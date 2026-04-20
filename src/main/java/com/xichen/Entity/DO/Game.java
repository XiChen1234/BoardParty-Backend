package com.xichen.Entity.DO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 桌游
 */
@Data
@TableName("game")
public class Game {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long groupId;
    private String name;
    private String icon;
    private String description;
    private Integer minPlayer;
    private Integer maxPlayer;
    private Integer duration;
    private String images; // json
    private Integer star;
    private Boolean enabled;
    private LocalDateTime updateTime;
    private Boolean deleted;
}
