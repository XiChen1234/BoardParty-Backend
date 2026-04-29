package com.xichen.Entity.Request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 编辑小圈的请求
 */
@Data
public class GroupEditRequest {
    @NotNull(message = "小圈id不能为空")
    private Long id;
    @Size(max = 20, message = "小圈名称长度不能超过20")
    private String name;
    private String avatarUrl;
    private String description;
}
