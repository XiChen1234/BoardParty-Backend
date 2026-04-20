package com.xichen.Common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseCode {
    SUCCESS(0, "成功"),

    /**
     * 系统通用 1000-1999
     */
    SYSTEM_ERROR(1000, "系统错误"),
    PARAM_ERROR(1001, "参数错误"),
    AUTH_NOT_LOGIN(1002, "未登录"),
    AUTH_TOKEN_EXPIRED(1003, "认证已过期"),
    AUTH_FAILED(1004, "权限验证失败"),
    RESOURCE_NOT_FOUND(1005, "资源不存在"),
    RESOURCE_ALREADY_EXIST(1006, "资源已存在"),

    /**
     * 文件上传模块 2000-2999
     */
    FILE_UPLOAD_SUCCESS(2000, "文件上传成功"),
    FILE_UPLOAD_FAILED(2001, "文件上传失败"),
    FILE_NOT_FOUND(2002, "文件不存在"),
    FILE_ALREADY_EXIST(2003, "文件已存在"),
    FILE_TYPE_NOT_SUPPORTED(2004, "文件类型不支持"),
    FILE_SIZE_EXCEEDED(2005, "文件大小超出限制"),

    /**
     * 用户模块 3000-3999
     */
    USER_NOT_FOUND(3001, "用户不存在"),
    USER_ALREADY_EXIST(3002, "用户已存在"),
    PASSWORD_ERROR(3003, "密码错误"),

    /**
     * 小圈模块 4000-4999
     */
    GROUP_NOT_FOUND(4001, "小圈不存在"),
    GROUP_ALREADY_EXIST(4002, "小圈名称已存在"),
    GROUP_PERMISSION_DENIED(4003, "没有小圈权限"),
    GROUP_MEMBER_ALREADY_EXIST(4004, "已加入该小圈"),
    GROUP_MEMBER_NOT_FOUND(4005, "不是该小圈成员"),

    /**
     * 桌游模块 5000-5999
     */
    GAME_NOT_FOUND(5001, "桌游不存在"),
    GAME_ALREADY_EXIST(5002, "桌游已存在"),
    GAME_DISABLED(5003, "桌游未启用"),

    /**
     * 对局记录模块 6000-6999
     */
    RECORD_NOT_FOUND(6001, "对局不存在"),
    RECORD_PLAYER_INVALID(6002, "玩家信息非法"),
    RECORD_RESULT_INVALID(6003, "对局结果非法"),
    STATS_CALCULATE_ERROR(6004, "统计计算失败"),

    /**
     * 惩罚模块 7000-7999
     */
    PUNISHMENT_NOT_FOUND(7001, "惩罚不存在"),
    ;

    private final Integer code;
    private final String message;
}
