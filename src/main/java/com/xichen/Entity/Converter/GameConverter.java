package com.xichen.Entity.Converter;

import com.xichen.Entity.DO.Game;
import com.xichen.Entity.DTO.GameQueryDTO;
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
