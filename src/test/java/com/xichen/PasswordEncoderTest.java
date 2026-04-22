package com.xichen;

import com.xichen.Security.PasswordUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class PasswordEncoderTest {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void generateBcryptHash() {
        String rawPassword = "password";
        String hashedPassword = PasswordUtil.hashPassword(rawPassword, passwordEncoder);
        System.out.println("密码: " + rawPassword);
        System.out.println("BCrypt哈希: " + hashedPassword);
    }
}