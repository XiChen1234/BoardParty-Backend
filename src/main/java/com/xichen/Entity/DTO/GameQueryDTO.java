package com.xichen.Entity.DTO;

import com.xichen.Entity.DO.Tag;
import lombok.Data;

import java.util.List;

@Data
public class GameQueryDTO {
    private Long id;
    private Long groupId;
    private String name;
    private String icon;
    private List<Tag> tags;
    private String description;
    private Integer minPlayer;
    private Integer maxPlayer;
    private Integer duration;
    private List<String> images;
    private Integer star;
}
