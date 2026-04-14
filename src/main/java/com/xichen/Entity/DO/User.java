package com.xichen.Entity.DO;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xichen.Entity.Enum.Gender;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户实体类
 */
@Data
@TableName("user")
public class User {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String username;
    private String password;
    private String nickname;
    private String avatarUrl;
    private Gender gender;
    private String remark;
    private LocalDateTime registerTime;
    private LocalDateTime updateTime;
    private Boolean isDeleted;
}