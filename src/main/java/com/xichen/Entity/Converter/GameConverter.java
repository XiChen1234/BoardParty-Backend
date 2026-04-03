package com.xichen.Entity.Converter;

import com.xichen.Entity.DO.Game;
import com.xichen.Entity.DTO.GameDTO;
import com.xichen.Entity.VO.GameVO;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
        gameDTO.setImages(game.getImage());
        gameDTO.setStar(game.getStar());

        gameDTO.setTags(parseTags(game.getTags()));

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

        gameVO.setName(gameDTO.getName());
        gameVO.setIcon(gameDTO.getIcon());
        gameVO.setTags(gameDTO.getTags());
        gameVO.setDescription(gameDTO.getDescription());
        gameVO.setMinPlayer(gameDTO.getMinPlayer());
        gameVO.setMaxPlayer(gameDTO.getMaxPlayer());
        gameVO.setImage(gameDTO.getImages());
        gameVO.setStar(gameDTO.getStar());
        return gameVO;
    }

    // 以下为辅助方法

    /**
     * 解析标签
     * @param tags 标签字符串
     * @return 标签列表
     */
    private static List<String> parseTags(String tags) {
        if(tags == null || tags.isEmpty()) {
            return Collections.emptyList();
        }
        return Arrays.stream(tags.split("#"))
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());
    }
}
