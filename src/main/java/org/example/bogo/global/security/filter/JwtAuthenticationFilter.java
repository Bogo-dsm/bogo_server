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

        String token = resolveToken(request);
        if (StringUtils.hasText(token)) {
            if (tokenProvider.validateToken(token)) {
                SecurityContextHolder.getContext().setAuthentication(tokenProvider.getAuthentication(token));
            }
        }

        filterChain.doFilter(request, response);
    }

    private String resolveToken(HttpServletRequest request) {
        String[] candidateHeaders = {"Authorization", "accessToken", "Access-Token", "X-Access-Token"};
        for (String headerName : candidateHeaders) {
            String headerValue = request.getHeader(headerName);
            String extracted = extractBearerValue(headerValue);
            if (StringUtils.hasText(extracted)) {
                return extracted;
            }
        }
        return null;
    }

    private String extractBearerValue(String headerValue) {
        if (!StringUtils.hasText(headerValue)) {
            return null;
        }

        String trimmed = headerValue.trim();
        if ((trimmed.startsWith("\"") && trimmed.endsWith("\""))
                || (trimmed.startsWith("'") && trimmed.endsWith("'"))) {
            trimmed = trimmed.substring(1, trimmed.length() - 1).trim();
        }

        if (trimmed.regionMatches(true, 0, "Bearer:", 0, 7)) {
            return trimmed.substring(7).trim();
        }
        if (trimmed.regionMatches(true, 0, "Bearer ", 0, 7)) {
            return trimmed.substring(7).trim();
        }
        // Bearer prefix 없이 raw token만 보내는 경우도 허용한다.
        return trimmed;
    }
}