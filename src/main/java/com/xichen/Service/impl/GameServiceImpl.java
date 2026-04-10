package com.xichen.Service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xichen.Entity.Converter.GameConverter;
import com.xichen.Entity.DO.Game;
import com.xichen.Entity.DO.GameTag;
import com.xichen.Entity.DO.Tag;
import com.xichen.Entity.DTO.GameQueryDTO;
import com.xichen.Mapper.GameMapper;
import com.xichen.Mapper.GameTagMapper;
import com.xichen.Mapper.TagMapper;
import com.xichen.Service.GameService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class GameServiceImpl implements GameService {
    @Resource
    private GameMapper gameMapper;
    @Resource
    private GameTagMapper gameTagMapper;
    @Resource
    private TagMapper tagMapper;

    /**
     * 获取所有游戏信息（已启用）
     * @return 游戏列表
     */
    @Override
    public List<GameQueryDTO> getAllGames() {
        // 查询所有启用的游戏信息
        LambdaQueryWrapper<Game> gameWrapper = new LambdaQueryWrapper<>();
        gameWrapper.eq(Game::getEnabled, true);
        List<Game> gameList = gameMapper.selectList(gameWrapper);
        // 若为空直接返回空列表
        if (gameList == null || gameList.isEmpty()) {
            return Collections.emptyList();
        }

        // 查询所有游戏-标签关系
        LambdaQueryWrapper<GameTag> gameTagWrapper = new LambdaQueryWrapper<>();
        gameTagWrapper.in(GameTag::getGameId, gameList.stream().map(Game::getId).toList());
        List<GameTag> gameTagList = gameTagMapper.selectList(gameTagWrapper);
        // 如果游戏-标签关系为空，说明所有游戏都没有标签，直接返回不带标签的游戏列表
        if (gameTagList == null || gameTagList.isEmpty()) {
            return gameList.stream().map(GameConverter::convertToDTO).toList();
        }

        // 查询所有标签信息
        LambdaQueryWrapper<Tag> tagWrapper = new LambdaQueryWrapper<>();
        tagWrapper.in(Tag::getId, gameTagList.stream().map(GameTag::getTagId).toList());
        List<Tag> tagList = tagMapper.selectList(tagWrapper);

        // 预存储标签信息
        Map<Long, List<Long>> gameIdAndTagIdListMap = gameTagList.stream()
                .collect(Collectors.groupingBy(
                        GameTag::getGameId,
                        Collectors.mapping(GameTag::getTagId, Collectors.toList())
                ));
        Map<Long, Tag> tagMap = tagList.stream().collect(Collectors.toMap(Tag::getId, t -> t));

        // 构建DTO列表
        List<GameQueryDTO> gameQueryDTOList = new ArrayList<>();
        for (Game game : gameList) {
            GameQueryDTO dto = GameConverter.convertToDTO(game);
            List<Long> tagIdList = gameIdAndTagIdListMap.get(game.getId());
            if (tagIdList != null && !tagIdList.isEmpty()) {
                List<Tag> tags = tagIdList.stream()
                        .map(tagMap::get)
                        .toList();
                dto.setTags(tags);
            }
            gameQueryDTOList.add(dto);
        }
        return gameQueryDTOList;
    }
}
