package com.xichen.Entity.Response;

import com.xichen.Entity.Enum.Gender;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户信息视图对象
 * TODO: 后续需要聚合补上游戏记录相关字段，如胜率、总场数
 */
@Data
public class UserResponse {
    private Long id;
    private String username;
    private String nickname;
    private String avatarUrl;
    private Gender gender;
    private LocalDateTime registerTime;
}
