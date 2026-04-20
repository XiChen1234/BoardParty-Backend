package com.xichen.Entity.Response;

import lombok.Data;

/**
 * 小圈列表，单项视图对象
 */
@Data
public class GroupListItemResponse {
    private Long id;
    private String name;
    private String avatarUrl;
    private String description;
}
