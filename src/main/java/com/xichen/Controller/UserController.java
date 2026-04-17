package com.xichen.Controller;

import com.xichen.Common.CommonResponse;
import com.xichen.Entity.Response.UserResponse;
import com.xichen.Security.JwtUtil;
import com.xichen.Service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户模块
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;
    @Resource
    private JwtUtil jwtUtil;

    @GetMapping("/{id}")
    public CommonResponse<UserResponse> getUser(@PathVariable Long id) {
        UserResponse userResponse = userService.getUserInfo(id);
        return CommonResponse.success(userResponse);
    }

    @GetMapping("/me")
    public CommonResponse<UserResponse> getUserSelf(HttpServletRequest request) {
        // 获取token，从token中获取用户id
        Long uid = (Long) request.getAttribute("uid");
        UserResponse userResponse = userService.getUserInfo(uid);
        return CommonResponse.success(userResponse);
    }
}
