package com.xichen.Entity.VO;

import com.xichen.Entity.Enum.Gender;
import lombok.Data;

/**
 * 登录返回用户信息
 */
@Data
public class LoginVO {
    private Long userId;
    private String username;
    private String nickname;
    private String avatarUrl;
    private Gender gender;
    private Boolean isAdmin;
    private String token;
}
