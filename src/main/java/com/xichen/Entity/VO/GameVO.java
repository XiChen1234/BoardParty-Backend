package com.xichen.Entity.VO;

import lombok.Data;

import java.util.List;

@Data
public class GameVO {
    private String name;
    private String icon;
    private List<String> tags;
    private String description;
    private Integer minPlayer;
    private Integer maxPlayer;
    private String image;
    private int star;
}
