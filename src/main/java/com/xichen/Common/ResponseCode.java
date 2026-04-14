package com.xichen.Common;

import lombok.Getter;

@Getter
public enum ResponseCode {
    SUCCESS(200, "成功"),
    FAIL(400, "操作失败"),
    ERROR(500, "服务器错误"),
    // 其他错误码定义

    VALIDATION_FAILED(1401, "参数验证失败"),
    INFO_EXIST(1402, "信息已存在"),
    INFO_NOT_FOUND(1404, "信息未找到"),
    ;

    private final Integer code;
    private final String message;

    ResponseCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
