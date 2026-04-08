package com.xichen.Controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xichen.Entity.DO.Game;
import com.xichen.Mapper.GameMapper;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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

    @PostMapping("/test/")
    public String post() {
        Game game = new Game();
        game.setGroupId(1L);
        game.setName("test");
        game.setIcon("https://www.baidu.com/img/PCtm_d9c8750bed0b3c7d089fa7d55720d6cf.png");
        game.setDescription("test");
        game.setMinPlayer(1);
        game.setMaxPlayer(1);
        game.setDuration(30);
        game.setImages("[\"https://www.baidu.com/img/PCtm_d9c8750bed0b3c7d089fa7d55720d6cf.png\"]");
        game.setStar(1);
        game.setEnabled(true);

        gameMapper.insert(game);
        return "POST";
    }
}