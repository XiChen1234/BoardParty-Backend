package com.xichen.Entity.DO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("punishment")
public class Punishment {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long groupId; // 0表示所有小圈公用
    private String name;
    private String content;
    private String icon;
    private Boolean enabled;
    private LocalDateTime updateTime;
    private Boolean deleted;
}
