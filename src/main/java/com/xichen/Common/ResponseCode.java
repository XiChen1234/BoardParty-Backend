package com.xichen.Common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseCode {
    SUCCESS(0, "成功"), // 1

    /**
     * 系统通用 1000-1999
     */
    SYSTEM_ERROR(1000, "系统错误"),
    PARAM_ERROR(1001, "参数错误"),
    VALIDATION_ERROR(1002, "数据验证错误"),
    AUTH_NOT_LOGIN(1003, "未登录"),
    AUTH_TOKEN_EXPIRED(1004, "认证已过期"),
    AUTH_FAILED(1005, "权限验证失败"),

    /**
     * 文件上传模块 2000-2999
     */
    FILE_UPLOAD_FAILED(2001, "文件上传失败"),

    /**
     * 用户模块 3000-3999
     */
    USER_NOT_FOUND(3001, "用户不存在"),
    PASSWORD_ERROR(3002, "用户名或密码错误"),

    /**
     * 小圈模块 4000-4999
     */
    GROUP_NOT_FOUND(4001, "小圈不存在"),
    GROUP_ALREADY_EXIST(4002, "小圈名称已存在"),
    GROUP_PERMISSION_DENIED(4003, "没有小圈权限"),
    GROUP_OPERATION_ERROR(4004, "小圈操作失败"),

    /**
     * 桌游模块 5000-5999
     */
    GAME_NOT_FOUND(5001, "桌游不存在"),
    GAME_ALREADY_EXIST(5002, "桌游已存在"),

    /**
     * 对局记录模块 6000-6999
     */

    /**
     * 惩罚模块 7000-7999
     */
    ;

    private final Integer code;
    private final String message;
}
