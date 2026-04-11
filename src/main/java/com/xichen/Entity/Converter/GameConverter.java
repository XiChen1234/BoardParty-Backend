package com.xichen.Entity.Converter;

import com.xichen.Entity.DO.Game;
import com.xichen.Entity.DTO.GameQueryDTO;
import com.xichen.Entity.Request.GameCreateRequest;
import com.xichen.Entity.VO.GameVO;

import com.alibaba.fastjson2.JSON;
import java.util.Collections;
import java.util.List;

public class GameConverter {
    /**
     * DO转换为DTO
     * @param game 数据对象
     * @return DTO对象
     */
    public static GameQueryDTO convertToDTO(Game game) {
        if(game == null) {
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
     * DTO转换为VO
     * @param gameQueryDTO 数据传输对象
     * @return 视图对象
     */
    public static GameVO convertToVO(GameQueryDTO gameQueryDTO) {
        if(gameQueryDTO == null) {
            return null;
        }

        GameVO gameVO = new GameVO();
        gameVO.setId(gameQueryDTO.getId());
        gameVO.setName(gameQueryDTO.getName());
        gameVO.setIcon(gameQueryDTO.getIcon());
        gameVO.setTags(gameQueryDTO.getTags());
        gameVO.setDescription(gameQueryDTO.getDescription());
        gameVO.setMinPlayer(gameQueryDTO.getMinPlayer());
        gameVO.setMaxPlayer(gameQueryDTO.getMaxPlayer());
        gameVO.setDuration(gameQueryDTO.getDuration());
        gameVO.setImages(gameQueryDTO.getImages());
        gameVO.setStar(gameQueryDTO.getStar());
        return gameVO;
    }

    /**
     * 请求转换为DO
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
     * @param json JSON字符串
     * @return String列表
     */
    public static List<String> parseJsonString(String json) {
        if (json == null || json.isBlank()) {
            return Collections.emptyList();
        }
        List<String> list = JSON.parseArray(json, String.class);
        if(list == null) {
            return Collections.emptyList();
        }
        return list;
    }

    /**
     * 将ListString转换为JSON字符串
     * List为空时返回"[]"
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
