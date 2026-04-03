package com.xichen.Entity.DO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("game")
public class Game {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long groupId;
    private String name;
    private String icon;
    private String tags;
    private String description;
    private Integer minPlayer;
    private Integer maxPlayer;
    private String image;
    private Integer star;
    private Boolean enabled;
}
