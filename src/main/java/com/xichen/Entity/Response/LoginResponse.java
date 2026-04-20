package com.xichen.Entity.Response;

import lombok.Data;

/**
 * 登录响应：返回jwt
 */
@Data
public class LoginResponse {
    private String token;
}
