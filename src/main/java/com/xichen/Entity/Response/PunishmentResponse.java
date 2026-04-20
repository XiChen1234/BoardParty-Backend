package com.xichen.Entity.Response;

import lombok.Data;

/**
 * 惩罚视图对象
 */
@Data
public class PunishmentResponse {
    private Long id;
    private String name;
    private String content;
    private String icon;
}
