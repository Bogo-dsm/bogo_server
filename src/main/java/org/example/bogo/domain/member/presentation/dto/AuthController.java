package org.example.bogo.domain.member.presentation.dto;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.example.bogo.domain.member.presentation.dto.request.JoinRequest;
import org.example.bogo.domain.member.presentation.dto.request.LoginRequest;
import org.example.bogo.domain.member.presentation.dto.request.SignupRequest;
import org.example.bogo.domain.member.presentation.dto.request.VerifyEmailRequest;
import org.example.bogo.domain.member.presentation.dto.response.JoinResponse;
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

    @PostMapping("/signup")
    public ResponseEntity<JoinResponse> join(@Valid @RequestBody SignupRequest request) {
        authService.join(request);
        return ResponseEntity.ok();
    }

    @PostMapping("/login")
    public ResponseEntity<LoginRequest> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/join/verify")
    public ResponseEntity<?> verifyEmail(@Valid @RequestBody VerifyEmailRequest request) {
        return ResponseEntity.ok(authService.verifyEmail(request));
    }
}