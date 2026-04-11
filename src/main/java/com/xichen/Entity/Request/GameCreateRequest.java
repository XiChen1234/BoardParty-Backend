package com.xichen.Entity.Request;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.List;

/**
 * 创建桌游的请求
 */
@Data
public class GameCreateRequest {
    @NotBlank(message = "桌游名称不能为空")
    @Size(max = 50, message = "桌游名称长度不能超过50个字符")
    private String name;

    @NotBlank(message = "桌游图标不能为空")
    private String icon;

    @NotBlank(message = "桌游描述不能为空")
    private String description;

    private List<String> tagNames;

    @NotNull(message = "桌游最小人数不能为空")
    @Min(value = 1, message = "桌游最小人数不能小于1")
    private Integer minPlayer;

    @NotNull(message = "桌游最大人数不能为空")
    @Min(value = 1, message = "桌游最大人数不能小于1")
    private Integer maxPlayer;

    @NotNull(message = "桌游时长不能为空")
    @Min(value = 1, message = "桌游时长不能小于1")
    private Integer duration;

    private List<String> images;

    @NotNull(message = "桌游星级不能为空")
    @Min(value = 0, message = "桌游星级不能小于0")
    @Max(value = 10, message = "桌游星级不能大于10")
    private Integer star;
}
