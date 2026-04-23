package com.xichen.Exception;

import com.xichen.Common.ResponseCode;
import lombok.Getter;

/**
 * 自定义异常
 */
@Getter
public class CommonException extends RuntimeException {
    private final ResponseCode code;

    public CommonException(ResponseCode responseCode) {
        super(responseCode.getMessage());
        this.code = responseCode;
    }

    public CommonException(ResponseCode responseCode, String message) {
        super(message);
        this.code = responseCode;
    }
}
