package com.xichen.Entity.Enum;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 小圈状态枚举类
 */
@Getter
@AllArgsConstructor
public enum GroupStatus {
    NORMAL(0, "正常"),
    FROZEN(1, "删除");

    @EnumValue
    private final Integer code;
    private final String status;

    @JsonValue
    public Integer getCode() {
        return code;
    }
}
