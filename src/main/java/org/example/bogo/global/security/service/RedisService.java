package org.example.bogo.global.security.service;


import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class RedisService {

    private final RedisTemplate<String, String> redisTemplate;

    public RedisService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    // --- 이메일 인증 코드 ---
    public void saveEmailCode(String email, String code, long minutes) {
        String key = "email:" + email;
        redisTemplate.opsForValue().set(key, code, Duration.ofMinutes(minutes));
    }

    public String getEmailCode(String email) {
        return redisTemplate.opsForValue().get("email:" + email);
    }

    public void deleteEmailCode(String email) {
        redisTemplate.delete("email:" + email);
    }

    // --- 액세스 토큰 ---
    public void saveAccessToken(String userId, String token, long minutes) {
        String key = "access:" + userId;
        redisTemplate.opsForValue().set(key, token, Duration.ofMinutes(minutes));
    }

    public String getAccessToken(String userId) {
        return redisTemplate.opsForValue().get("access:" + userId);
    }

    public void deleteAccessToken(String userId) {
        redisTemplate.delete("access:" + userId);
    }

    // --- 리프레시 토큰 ---
    public void saveRefreshToken(String userId, String token, long days) {
        String key = "refresh:" + userId;
        redisTemplate.opsForValue().set(key, token, Duration.ofDays(days));
    }

    public String getRefreshToken(String userId) {
        return redisTemplate.opsForValue().get("refresh:" + userId);
    }

    public void deleteRefreshToken(String userId) {
        redisTemplate.delete("refresh:" + userId);
    }
}