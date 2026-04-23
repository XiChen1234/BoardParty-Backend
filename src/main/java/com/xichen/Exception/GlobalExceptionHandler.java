package com.xichen.Exception;

import com.xichen.Common.CommonResponse;
import com.xichen.Common.ResponseCode;
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
        log.warn("业务异常: code={}, message={}",
                e.getCode(),
                e.getMessage(), e);
        return CommonResponse.fail(e.getCode(), e.getMessage());
    }

    /**
     * 参数验证失败处理器，Spring Validation<br/>
     * 仅在controller层进行参数校验时使用，禁止在service层进行参数校验
     *
     * @param e 捕获的参数验证失败异常
     * @return 响应对象
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public CommonResponse<?> handleValidationException(MethodArgumentNotValidException e) {
        log.error("参数验证失败：", e);
        String error = e.getBindingResult().getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining("; "));

        return CommonResponse.fail(ResponseCode.PARAM_ERROR, error);
    }

    /**
     * 简单参数错误处理器<br/>
     * 仅在service层进行参数校验时使用，禁止在controller层进行参数校验
     *
     * @param e 参数错误
     * @return 响应对象
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public CommonResponse<?> handleIllegalArgumentException(IllegalArgumentException e) {
        log.error("参数错误: {}", e.getMessage());
        return CommonResponse.fail(ResponseCode.PARAM_ERROR, e.getMessage());
    }

    /**
     * 默认异常处理器<br/>
     * 处理所有未捕获的异常，兜底，必须打详细日志
     *
     * @param e       捕获的异常
     * @param request 请求对象
     * @return 响应的异常对象
     */
    @ExceptionHandler(Exception.class)
    public CommonResponse<?> handleException(Exception e, HttpServletRequest request) {
        log.error("\n请求路径: {}\n异常类型: {}\n异常信息: {}\n", request.getRequestURI(), e.getClass().getName(), e.getMessage(), e);
        return CommonResponse.fail(ResponseCode.SYSTEM_ERROR);
    }
}
