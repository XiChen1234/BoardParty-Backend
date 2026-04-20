package com.xichen.Entity.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 创建小圈的请求
 */
@Data
public class GroupCreateRequest {
    @NotBlank(message = "小圈名称不能为空")
    @Size(max = 20, message = "小圈名称长度不能超过20")
    private String name;
    private String avatarUrl; // 小圈头像，为空则采用默认头像
    private String description; // 小圈描述，为空则采用默认描述
    private Long creatorId; // controller层自动填充jwt中的当前用户id
}
