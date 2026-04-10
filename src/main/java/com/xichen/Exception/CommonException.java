package com.xichen.Exception;

import com.xichen.Common.ResponseCode;
import lombok.Getter;

/**
 * 自定义异常
 */
@Getter
public class CommonException extends RuntimeException{
    private final ResponseCode code;
    private final String message;

    public CommonException(ResponseCode code) {
        this.code = code;
        this.message = code.getMessage();
    }

    public CommonException(ResponseCode code, String message) {
        this.code = code;
        this.message = message;
    }
}
