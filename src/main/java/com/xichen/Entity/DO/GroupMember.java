package com.xichen.Entity.DO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xichen.Entity.Enum.Role;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 小圈成员表
 */
@Data
@TableName("circle_member")
public class GroupMember {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long groupId;
    private Long userId;
    private Role role;
    private LocalDateTime joinTime;
}
