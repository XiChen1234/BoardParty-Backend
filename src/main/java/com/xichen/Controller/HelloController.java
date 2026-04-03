package com.xichen.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// REST 控制器注解
@RestController
public class HelloController {
    // 映射根路径请求
    @GetMapping("/")
    public String hello() {
        return "Hello World!";
    }
}