package com.xichen.Service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xichen.Common.ResponseCode;
import com.xichen.Entity.Converter.GameConverter;
import com.xichen.Entity.DO.Game;
import com.xichen.Entity.DO.GameTag;
import com.xichen.Entity.DO.Tag;
import com.xichen.Entity.DTO.GameQueryDTO;
import com.xichen.Entity.Request.GameCreateRequest;
import com.xichen.Exception.CommonException;
import com.xichen.Mapper.GameMapper;
import com.xichen.Mapper.GameTagMapper;
import com.xichen.Mapper.TagMapper;
import com.xichen.Service.GameService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
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
     *
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

    /**
     * 创建新桌游
     *
     * @return 创建的桌游Id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createGame(GameCreateRequest request) {
        // 1. 校验名称是否重复
        LambdaQueryWrapper<Game> gameWrapper = new LambdaQueryWrapper<>();
        gameWrapper.eq(Game::getName, request.getName());
        if (gameMapper.selectOne(gameWrapper) != null) {
            throw new CommonException(ResponseCode.INFO_EXIST, "桌游名称已存在");
        }

        // 2. 创建桌游
        Game game = GameConverter.convertToDO(request);
        gameMapper.insert(game);
        Long gameId = game.getId(); // 获取创建的桌游Id

        // 3. 处理标签
        List<String> tagNames = request.getTagNames();
        if (tagNames != null && !tagNames.isEmpty()) {
            // 去重
            tagNames = new ArrayList<>(new LinkedHashSet<>(tagNames));
            // 批量获取已存在的标签
            LambdaQueryWrapper<Tag> tagWrapper = new LambdaQueryWrapper<>();
            tagWrapper.in(Tag::getName, tagNames);
            List<Tag> existTagList = tagMapper.selectList(tagWrapper);
            // 构建已存在的标签名称和Id的映射
            Map<String, Long> tagNameAndIdMap = existTagList.stream()
                    .collect(Collectors.toMap(Tag::getName, Tag::getId));
            List<Tag> newTagList = new ArrayList<>();
            for (String tagName : tagNames) {
                if (!tagNameAndIdMap.containsKey(tagName)) {
                    Tag tag = new Tag();
                    tag.setName(tagName);
                    newTagList.add(tag);
                }
            }

            // 批量插入标签
            if (!newTagList.isEmpty()) {
                tagMapper.insert(newTagList);
                tagNameAndIdMap.putAll(newTagList.stream()
                        .collect(Collectors.toMap(Tag::getName, Tag::getId)));
            }

            // 批量插入游戏-标签关系
            List<GameTag> gameTagList = new ArrayList<>();
            for (String tagName : tagNames) {
                GameTag gameTag = new GameTag();
                gameTag.setGameId(gameId);
                gameTag.setTagId(tagNameAndIdMap.get(tagName));
                gameTagList.add(gameTag);
            }
            gameTagMapper.insert(gameTagList);
        }

        return gameId;
    }
}
