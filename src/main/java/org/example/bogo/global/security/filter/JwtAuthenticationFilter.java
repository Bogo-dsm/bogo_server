package org.example.bogo.global.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.bogo.global.security.jwt.JwtTokenProvider;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider tokenProvider;

    public JwtAuthenticationFilter(JwtTokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        String token = resolveToken(authHeader);
        if (StringUtils.hasText(token)) {
            if (tokenProvider.validateToken(token)) {
                SecurityContextHolder.getContext().setAuthentication(tokenProvider.getAuthentication(token));
            }
        }

        filterChain.doFilter(request, response);
    }

    private String resolveToken(String authHeader) {
        if (!StringUtils.hasText(authHeader)) {
            return null;
        }

        String trimmed = authHeader.trim();
        if (trimmed.regionMatches(true, 0, "Bearer ", 0, 7)) {
            return trimmed.substring(7).trim();
        }
        // 일부 클라이언트가 Bearer prefix 없이 토큰만 보내는 경우도 허용한다.
        return trimmed;
    }
}