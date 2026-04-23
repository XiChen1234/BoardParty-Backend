package com.xichen.Common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommonResponse<T> {
    private Integer code;
    private String message;
    private T data;

    /**
     * 携带数据的创建成功响应
     *
     * @param data 携带的数据
     * @param <T>  响应的数据类型
     * @return 响应对象
     */
    public static <T> CommonResponse<T> success(T data) {
        return new CommonResponse<>(
                ResponseCode.SUCCESS.getCode(),
                ResponseCode.SUCCESS.getMessage(),
                data
        );
    }

    /**
     * 无信息的失败响应
     *
     * @param code 错误码
     * @param <T>  响应的数据类型
     * @return 响应对象
     */
    public static <T> CommonResponse<T> fail(ResponseCode code) {
        return new CommonResponse<>(code.getCode(), code.getMessage(), null);
    }

    /**
     * 携带信息的失败响应
     *
     * @param code    错误码
     * @param message 错误信息
     * @param <T>     响应的数据类型
     * @return 响应对象
     */
    public static <T> CommonResponse<T> fail(ResponseCode code, String message) {
        return new CommonResponse<>(code.getCode(), message, null);
    }
}
