package com.xichen.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class PasswordUtil {
    private static final int BCRYPT_STRENGTH = 10;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(BCRYPT_STRENGTH);
    }

    public static String hashPassword(String rawPassword, PasswordEncoder encoder) {
        return encoder.encode(rawPassword);
    }

    public static boolean checkPassword(String rawPassword, String encodedPassword, PasswordEncoder encoder) {
        return encoder.matches(rawPassword, encodedPassword);
    }
}