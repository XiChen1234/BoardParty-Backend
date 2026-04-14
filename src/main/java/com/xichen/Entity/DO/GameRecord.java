package com.xichen.Entity.DO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 游戏记录
 */
@Data
@TableName("game_record")
public class GameRecord {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long groupId;
    private Long gameId;
    private Integer playerCount;
    private String remark;
    private LocalDateTime updateTime;
    private LocalDateTime createdTime;
    private Boolean isDeleted;
}
