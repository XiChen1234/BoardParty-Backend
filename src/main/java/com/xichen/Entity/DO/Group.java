package com.xichen.Entity.DO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 小圈实体类
 */
@Data
@TableName("circle")
public class Group {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private String avatarUrl;
    private Long creatorId;
    private LocalDateTime creatTime;
}
