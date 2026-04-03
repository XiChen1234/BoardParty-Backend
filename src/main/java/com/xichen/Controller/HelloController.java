package com.xichen.Controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xichen.Entity.Game;
import com.xichen.Mapper.GameMapper;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// REST 控制器注解
@RestController
public class HelloController {
    @Resource
    private GameMapper gameMapper;

    // 映射根路径请求
    @GetMapping("/")
    public String hello() {
        return "Hello World!";
    }

    @GetMapping("/test/")
    public List<Game> get() {
        return gameMapper.selectList(new QueryWrapper<>());
    }
}