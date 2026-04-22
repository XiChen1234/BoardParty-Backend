package com.xichen.Entity.DTO;

import com.xichen.Entity.Enum.Gender;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户查询参数
 */
@Data
public class UserQueryDTO {
    private Long id;
    private String username;
    private String nickname;
    private String avatarUrl;
    private Gender gender;
    private LocalDateTime registerTime;
    private LocalDateTime updateTime;
}
