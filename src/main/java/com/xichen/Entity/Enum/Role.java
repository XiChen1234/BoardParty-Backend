package com.xichen.Entity.Enum;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 角色枚举
 */
@Getter
@AllArgsConstructor
public enum Role {
    CREATOR(0, "创建者"),
    MANAGER(1, "管理员"),
    PLAYER(2, "普通玩家");

    @EnumValue
    private final int code;
    private final String name;

    @JsonValue
    public int getCode() {
        return code;
    }
}
