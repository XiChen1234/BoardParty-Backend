package com.xichen.Entity.Converter;

import com.xichen.Entity.DO.Game;
import com.xichen.Entity.DTO.GameQueryDTO;
import com.xichen.Entity.Request.GameCreateRequest;
import com.xichen.Entity.VO.GameDetailVO;
import com.xichen.Entity.VO.GameListItemVO;

import com.alibaba.fastjson2.JSON;

import java.util.Collections;
import java.util.List;

public class GameConverter {
    /**
     * DO转换为DTO
     *
     * @param game 数据对象
     * @return DTO对象
     */
    public static GameQueryDTO convertToDTO(Game game) {
        if (game == null) {
            return null;
        }

        GameQueryDTO gameQueryDTO = new GameQueryDTO();

        gameQueryDTO.setId(game.getId());
        gameQueryDTO.setGroupId(game.getGroupId());
        gameQueryDTO.setName(game.getName());
        gameQueryDTO.setIcon(game.getIcon());
        gameQueryDTO.setDescription(game.getDescription());
        gameQueryDTO.setMinPlayer(game.getMinPlayer());
        gameQueryDTO.setMaxPlayer(game.getMaxPlayer());
        gameQueryDTO.setDuration(game.getDuration());
        gameQueryDTO.setStar(game.getStar());

        gameQueryDTO.setImages(parseJsonString(game.getImages()));

        return gameQueryDTO;
    }

    /**
     * DTO转换为ListItemVO
     *
     * @param gameQueryDTO 数据传输对象
     * @return 视图对象
     */
    public static GameListItemVO convertToListItemVO(GameQueryDTO gameQueryDTO) {
        if (gameQueryDTO == null) {
            return null;
        }

        GameListItemVO gameListItemVO = new GameListItemVO();
        gameListItemVO.setId(gameQueryDTO.getId());
        gameListItemVO.setName(gameQueryDTO.getName());
        gameListItemVO.setIcon(gameQueryDTO.getIcon());
        gameListItemVO.setTags(gameQueryDTO.getTags());
        gameListItemVO.setDescription(gameQueryDTO.getDescription());
        gameListItemVO.setMinPlayer(gameQueryDTO.getMinPlayer());
        gameListItemVO.setMaxPlayer(gameQueryDTO.getMaxPlayer());
        gameListItemVO.setDuration(gameQueryDTO.getDuration());
        gameListItemVO.setStar(gameQueryDTO.getStar());
        return gameListItemVO;
    }

    public static GameDetailVO convertToDetailVO(GameQueryDTO gameQueryDTO) {
        if(gameQueryDTO == null) {
            return null;
        }

        GameDetailVO gameDetailVO = new GameDetailVO();
        gameDetailVO.setId(gameQueryDTO.getId());
        gameDetailVO.setName(gameQueryDTO.getName());
        gameDetailVO.setIcon(gameQueryDTO.getIcon());
        gameDetailVO.setTags(gameQueryDTO.getTags());
        gameDetailVO.setDescription(gameQueryDTO.getDescription());
        gameDetailVO.setMinPlayer(gameQueryDTO.getMinPlayer());
        gameDetailVO.setMaxPlayer(gameQueryDTO.getMaxPlayer());
        gameDetailVO.setDuration(gameQueryDTO.getDuration());
        gameDetailVO.setStar(gameQueryDTO.getStar());
        gameDetailVO.setImages(gameQueryDTO.getImages());
        return gameDetailVO;
    }

    /**
     * 请求转换为DO
     *
     * @param request 桌游创建请求
     * @return 数据对象
     */
    public static Game convertToDO(GameCreateRequest request) {
        if (request == null) {
            return null;
        }

        Game game = new Game();
        game.setName(request.getName());
        game.setIcon(request.getIcon());
        game.setDescription(request.getDescription());
        game.setImages(parseListString(request.getImages()));
        game.setMinPlayer(request.getMinPlayer());
        game.setMaxPlayer(request.getMaxPlayer());
        game.setDuration(request.getDuration());
        game.setStar(request.getStar());
        game.setEnabled(true);
        return game;
    }

    // 以下为辅助方法

    /**
     * 将JSON字符串解析为List，若为空则返回空列表
     *
     * @param json JSON字符串
     * @return String列表
     */
    public static List<String> parseJsonString(String json) {
        if (json == null || json.isBlank()) {
            return Collections.emptyList();
        }
        List<String> list = JSON.parseArray(json, String.class);
        if (list == null) {
            return Collections.emptyList();
        }
        return list;
    }

    /**
     * 将ListString转换为JSON字符串
     * List为空时返回"[]"
     *
     * @param list String列表
     * @return JSON字符串
     */
    public static String parseListString(List<String> list) {
        if (list == null || list.isEmpty()) {
            return "[]";
        }
        return JSON.toJSONString(list);
    }
}
