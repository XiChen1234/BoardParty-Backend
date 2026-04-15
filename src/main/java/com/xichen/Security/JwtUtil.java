package com.xichen.Security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

/**
 * JWT工具类
 * - 生成JWT
 * - 解析JWT
 * - 验证JWT
 */
@Component
public class JwtUtil {
    @Value("${jwt.secret}")
    private String secret;
    private final Long expire = (long) (1000 * 60 * 60 * 24 * 7); // 一周

    private SecretKey key;

    @PostConstruct
    public void init() {
        key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    /**
     * 生成JWT
     *
     * @param uid      用户ID
     * @param username 用户名
     * @return JWT
     */
    public String generateToken(Long uid, String username) {
        Date now = new Date();
        Date expireTime = new Date(now.getTime() + expire);

        return Jwts.builder()
                .subject(username)
                .claim("uid", uid)
                .issuedAt(now)
                .expiration(expireTime)
                .signWith(key)
                .compact();
    }

    /**
     * 解析JWT，返回payload
     *
     * @param token JWT
     * @return Claims
     */
    public Claims parseToken(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

}
