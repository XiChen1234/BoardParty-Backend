package com.xichen.Controller;

import com.xichen.Common.CommonResponse;
import com.xichen.Entity.Response.UserResponse;
import com.xichen.Service.UserService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

/**
 * 用户模块
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;
    @GetMapping("/{id}")
    public CommonResponse<UserResponse> getUser(@PathVariable Long id) {
        UserResponse userResponse = userService.getUserInfo(id);
        return CommonResponse.success(userResponse);
    }
}
