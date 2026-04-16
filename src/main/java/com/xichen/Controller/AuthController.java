package com.xichen.Controller;

import com.xichen.Annotation.IgnoreAuth;
import com.xichen.Common.CommonResponse;
import com.xichen.Entity.Request.LoginRequest;
import com.xichen.Service.AuthService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户认证模块
 */
@RestController
@RequestMapping("/auth")
public class AuthController {
    @Resource
    private AuthService authService;

    @IgnoreAuth
    @PostMapping("/login")
    public CommonResponse<String> login(@RequestBody LoginRequest request) {
        String token = authService.login(
                request.getUsername(),
                request.getPassword()
        );
        return CommonResponse.success(token);
    }
}
