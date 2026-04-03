package com.xichen.Service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xichen.Entity.Converter.GameConverter;
import com.xichen.Entity.DO.Game;
import com.xichen.Entity.DTO.GameDTO;
import com.xichen.Mapper.GameMapper;
import com.xichen.Service.GameService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameServiceImpl implements GameService {
    @Resource
    private GameMapper gameMapper;

    @Override
    public List<GameDTO> getAllGames() {
        QueryWrapper<Game> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("enabled", true);
        List<Game> gameList = gameMapper.selectList(queryWrapper);
        return gameList.stream()
                .map(GameConverter::convertToDTO)
                .toList();
    }
}
