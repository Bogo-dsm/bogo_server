package org.example.bogo.domain.member.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.bogo.domain.member.entity.Member;
import org.example.bogo.domain.member.presentation.dto.request.LoginRequest;
import org.example.bogo.domain.member.presentation.dto.request.SignupRequest;
import org.example.bogo.domain.member.presentation.dto.request.VerifyCodeRequest;
import org.example.bogo.domain.member.presentation.dto.request.VerifyCodeSendRequest;
import org.example.bogo.domain.member.presentation.dto.response.TokenResponse;
import org.example.bogo.domain.member.repository.MemberRepository;
import org.example.bogo.global.error.exception.BogoException;
import org.example.bogo.global.error.exception.GlobalErrorCode;
import org.example.bogo.global.mail.service.MailService;
import org.example.bogo.global.security.jwt.JwtTokenProvider;
import org.example.bogo.global.security.service.RedisService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;
    private final RedisService redisService;
    private final JwtTokenProvider jwtTokenProvider;


    @Transactional
    public String signup(SignupRequest data) {

        if(memberRepository.existsByEmail(data.email())) {
            throw new BogoException(GlobalErrorCode.EMAIL_DUPLICATE);
        }

        Member member = Member.builder()
                .nickname(data.nickname())
                .email(data.email())
                .password(passwordEncoder.encode(data.password()))
                .build();

        memberRepository.save(member);

        return member.getNickname();
    }

    public void send(VerifyCodeSendRequest data) {

        Member member = memberRepository.findByEmail(data.email())
                .orElseThrow(() -> new BogoException(GlobalErrorCode.MEMBER_NOT_FOUND));

        String code = mailService.generateCode();

        redisService.saveEmailCode(member.getEmail(), code, 10);

        mailService.sendVerificationEmail(member.getEmail(), code);
    }

    @Transactional
    public void verify(VerifyCodeRequest data) {
        String savedCode = redisService.getEmailCode(data.email());
        if(savedCode == null || !savedCode.equals(data.code())) {
            throw new BogoException(GlobalErrorCode.INVALID_VERIFICATION_CODE);
        }
        Member member = memberRepository.findMemberByEmail(data.email())
                .orElseThrow(() -> new BogoException(GlobalErrorCode.MEMBER_NOT_FOUND));
        member.makeEmailVerified();
        redisService.deleteEmailCode(data.email()); // 검증 후 삭제
    }


    public TokenResponse login(LoginRequest request) {

        Member member = memberRepository.findByEmail(request.email())
                .orElseThrow(() -> new BogoException(GlobalErrorCode.INVALID_CREDENTIALS));

        if (!passwordEncoder.matches(request.password(), member.getPassword())) {
            throw new BogoException(GlobalErrorCode.INVALID_CREDENTIALS);
        }

        if (!member.isEmailVerified()) {
            throw new BogoException(GlobalErrorCode.EMAIL_NOT_VERIFIED);
        }

        String accessToken = jwtTokenProvider.generateAccessToken(
                member.getId(),
                member.getEmail(),
                String.valueOf(member.getRole()));

        String refreshToken = jwtTokenProvider.generateRefreshToken(
                member.getId(),
                member.getEmail());

        redisService.saveRefreshToken(String.valueOf(member.getId()), refreshToken, 14); // 14일 예시

        return new TokenResponse(accessToken, refreshToken);
    }

}