package com.xichen.Controller;

import com.xichen.Mapper.GameMapper;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试模块
 */
@RestController
public class HelloController {
    @Resource
    private GameMapper gameMapper;

    // 映射根路径请求
    @GetMapping("/")
    public String hello(HttpServletRequest request) {
        Object userId = request.getAttribute("uid");
        return "Hello World!" + userId;
    }
}