package com.xichen.Entity.DO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xichen.Entity.Enum.GroupStatus;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 小圈实体类，数据库无法存group，故存circle
 */
@Data
@TableName("circle")
public class Group {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private String avatarUrl;
    private String description;
    private Long creatorId;
    private Integer memberCount;
    private LocalDateTime updateTime;
    private GroupStatus status;
    private LocalDateTime createTime;
    private Boolean deleted;
}
