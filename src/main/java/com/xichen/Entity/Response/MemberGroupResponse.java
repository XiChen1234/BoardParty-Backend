package com.xichen.Entity.Response;

import com.xichen.Entity.Enum.Gender;
import com.xichen.Entity.Enum.Role;
import lombok.Data;

/**
 * 小圈中，用户显示的信息
 */
@Data
public class MemberGroupResponse {
    private Long id;
    private String nickname;
    private String avatarUrl;
    private Gender gender;
    private Role role;
}
