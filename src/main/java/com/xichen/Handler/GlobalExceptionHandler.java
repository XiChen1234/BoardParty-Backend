package com.xichen.Handler;

import com.xichen.Common.CommonResponse;
import com.xichen.Common.ResponseCode;
import com.xichen.Exception.CommonException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

/**
 * 全局异常处理器
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 自定义异常处理器
     *
     * @param e 捕获的自定义异常
     * @return 响应对象
     */
    @ExceptionHandler(CommonException.class)
    public CommonResponse<?> handleCommonException(CommonException e) {
        if (e.getMessage() != null) {
            return CommonResponse.fail(e.getCode(), e.getMessage());
        }
        return CommonResponse.fail(e.getCode());
    }

    /**
     * 参数验证失败处理器
     *
     * @param e 捕获的参数验证失败异常
     * @return 响应对象
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public CommonResponse<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String error = e.getBindingResult().getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining("; "));
        log.warn("参数验证失败: {}", error);

        return CommonResponse.fail(ResponseCode.PARAM_ERROR, error);
    }

    /**
     * 默认异常处理器，处理所有未捕获的异常，兜底
     *
     * @param e       捕获的异常
     * @param request 请求对象
     * @return 响应对象
     */
    @ExceptionHandler(Exception.class)
    public CommonResponse<?> handleException(Exception e, HttpServletRequest request) {
        log.error("\n请求路径: {}\n异常类型: {}\n异常信息: {}\n",
                request.getRequestURI(),
                e.getClass().getName(),
                e.getMessage(),
                e);
        return CommonResponse.fail(ResponseCode.SYSTEM_ERROR);
    }
}
