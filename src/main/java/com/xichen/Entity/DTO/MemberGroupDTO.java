package com.xichen.Entity.DTO;

import com.xichen.Entity.Enum.Gender;
import com.xichen.Entity.Enum.Role;
import lombok.Data;

/**
 * 用户在group中的信息
 */
@Data
public class MemberGroupDTO {
    private Long id;
    private String nickname;
    private String avatarUrl;
    private Gender gender;
    private Role role; // 角色代码，0-创建者，1-管理员，2-普通玩家
}
