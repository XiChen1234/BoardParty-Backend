package com.xichen.Entity.Converter;

import com.xichen.Entity.DO.Game;
import com.xichen.Entity.DTO.GameDTO;
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
    public static GameDTO convertToDTO(Game game) {
        if(game == null) {
            return null;
        }

        GameDTO gameDTO = new GameDTO();

        gameDTO.setId(game.getId());
        gameDTO.setGroupId(game.getGroupId());
        gameDTO.setName(game.getName());
        gameDTO.setIcon(game.getIcon());
        gameDTO.setDescription(game.getDescription());
        gameDTO.setMinPlayer(game.getMinPlayer());
        gameDTO.setMaxPlayer(game.getMaxPlayer());
        gameDTO.setDuration(game.getDuration());
        gameDTO.setStar(game.getStar());

        gameDTO.setImages(parseJsonString(game.getImages()));

        return gameDTO;
    }

    /**
     * DTO转换为VO
     * @param gameDTO 数据传输对象
     * @return 视图对象
     */
    public static GameVO convertToVO(GameDTO gameDTO) {
        if(gameDTO == null) {
            return null;
        }

        GameVO gameVO = new GameVO();
        gameVO.setId(gameDTO.getId());
        gameVO.setName(gameDTO.getName());
        gameVO.setIcon(gameDTO.getIcon());
        gameVO.setTags(gameDTO.getTags());
        gameVO.setDescription(gameDTO.getDescription());
        gameVO.setMinPlayer(gameDTO.getMinPlayer());
        gameVO.setMaxPlayer(gameDTO.getMaxPlayer());
        gameVO.setDuration(gameDTO.getDuration());
        gameVO.setImages(gameDTO.getImages());
        gameVO.setStar(gameDTO.getStar());
        return gameVO;
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
}
