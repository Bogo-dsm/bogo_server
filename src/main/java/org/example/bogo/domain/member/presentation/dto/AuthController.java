package org.example.bogo.domain.member.presentation.dto;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.bogo.domain.member.presentation.dto.request.LoginRequest;
import org.example.bogo.domain.member.presentation.dto.request.SignupRequest;
import org.example.bogo.domain.member.presentation.dto.request.VerifyCodeRequest;
import org.example.bogo.domain.member.presentation.dto.request.VerifyCodeSendRequest;
import org.example.bogo.domain.member.presentation.dto.response.JoinResponse;
import org.example.bogo.global.APIResponse;
import org.example.bogo.domain.member.presentation.dto.response.TokenResponse;
import org.example.bogo.domain.member.presentation.dto.response.VerifyResponse;
import org.example.bogo.domain.member.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<JoinResponse> signup(@Valid @RequestBody SignupRequest request) {
        String name = authService.signup(request);
        return ResponseEntity.ok().body(new JoinResponse("OK", "Signup successfully.", name));
    }

    // 코드 전송
    @PostMapping("/send")
    public ResponseEntity<VerifyResponse> send(@Valid @RequestBody VerifyCodeSendRequest request) {
        authService.send(request);
        return ResponseEntity.ok().body(new VerifyResponse("OK", "Email verify code send successful."));
    }

    // 받은 코드 인증
    @PostMapping("/verify")
    public ResponseEntity<VerifyResponse> verify(@Valid @RequestBody VerifyCodeRequest request) {
        authService.verify(request);
        return ResponseEntity.ok().body(new VerifyResponse("OK","Email verification successful."));
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<APIResponse<TokenResponse>> login(@Valid @RequestBody LoginRequest request) {
        TokenResponse tokenResponse = authService.login(request);
        APIResponse<TokenResponse> response =
                new APIResponse<>("OK", "로그인 성공", tokenResponse);
        return ResponseEntity.ok().body(response);
    }
}