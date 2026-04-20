package com.xichen.Entity.Response;

import com.xichen.Entity.DO.Tag;
import lombok.Data;

import java.util.List;

/**
 * 桌游列表，单项视图对象
 */
@Data
public class GameListItemResponse {
    private Long id;
    private String name;
    private String icon;
    private List<Tag> tags;
    private String description;
    private Integer minPlayer;
    private Integer maxPlayer;
    private Integer duration;
    private Integer star;
}
