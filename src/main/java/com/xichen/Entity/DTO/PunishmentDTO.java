package com.xichen.Entity.DTO;

import lombok.Data;

/**
 * 惩罚数据传输对象
 */
@Data
public class PunishmentDTO {
    private Long id;
    private String name;
    private String content;
    private String icon;
}
