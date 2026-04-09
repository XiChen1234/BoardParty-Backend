package com.xichen.Entity.VO;

import com.xichen.Entity.DO.Tag;
import lombok.Data;

import java.util.List;

@Data
public class GameVO {
    private Long id;
    private String name;
    private String icon;
    private List<Tag> tags;
    private String description;
    private Integer minPlayer;
    private Integer maxPlayer;
    private Integer duration;
    private List<String> images;
    private int star;
}
