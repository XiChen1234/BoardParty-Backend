package com.xichen.Common;

import lombok.Getter;

@Getter
public enum ResponseCode {
    SUCCESS(200, "成功"),
    FAIL(400, "操作失败"),
    ERROR(500, "服务器错误"),
    // 其他错误码定义

    ;

    private final int code;
    private final String message;

    ResponseCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
