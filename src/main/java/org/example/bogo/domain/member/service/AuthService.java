package org.example.bogo.domain.member.service;

import jakarta.transaction.Transactional;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.example.bogo.domain.member.entity.Member;
import org.example.bogo.domain.member.presentation.dto.request.SignupRequest;
import org.example.bogo.domain.member.repository.MemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;


    @Transactional
    public void join(SignupRequest data) {

        Member member = Member.builder()
                .nickname(data.nickname())
                .email(data.email())
                .password(passwordEncoder.encode(data.password()))
                .build();
    }

}
