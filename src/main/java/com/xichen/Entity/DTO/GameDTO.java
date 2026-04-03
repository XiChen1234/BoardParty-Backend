package com.xichen.Entity.DTO;

import lombok.Data;

import java.util.List;

@Data
public class GameDTO {
    private Long id;
    private Long groupId;
    private String name;
    private String icon;
    private List<String> tags;
    private String description;
    private Integer minPlayer;
    private Integer maxPlayer;
    private String images;
    private Integer star;
}
