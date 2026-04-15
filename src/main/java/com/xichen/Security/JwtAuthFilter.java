package com.xichen.Security;

import com.alibaba.fastjson2.JSON;
import com.xichen.Annotation.IgnoreAuth;
import com.xichen.Common.CommonResponse;
import com.xichen.Common.ResponseCode;
import io.jsonwebtoken.Claims;
import jakarta.annotation.Resource;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.io.IOException;

/**
 * 用户JWT认证前置过滤器
 */
@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final RequestMappingHandlerMapping handlerMapping;

    public JwtAuthFilter(JwtUtil jwtUtil, RequestMappingHandlerMapping handlerMapping) {
        this.jwtUtil = jwtUtil;
        this.handlerMapping = handlerMapping;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        // 判断忽略认证注解@IgnoreAuth，存在直接放行
        if (isIgnoreAuth(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = extractToken(request);
        // 需要token，但没有token，返回401
        if (token == null) {
            writeResponse(response, "未登录");
            return;
        }

        try {
            Claims claims = jwtUtil.parseToken(token);
            Long uid = claims.get("uid", Long.class);
            String username = claims.getSubject();
            request.setAttribute("uid", uid);
            request.setAttribute("username", username);
        } catch (Exception e) {
            writeResponse(response, "token验证失败");
            return;
        }

        filterChain.doFilter(request, response);
    }

    /**
     * 从请求中提取token
     * @param request 请求
     * @return token
     */
    private String extractToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {
            return null;
        }

        return header.substring(7);
    }

    private void writeResponse(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=UTF-8");
        CommonResponse<?> commonResponse = CommonResponse.fail(ResponseCode.UNAUTHORIZED, message);
        String json = JSON.toJSONString(commonResponse);
        response.getWriter().write(json);
    }

    /**
     * 判断接口是否需要被放行
     * 实现方式：注解
     *
     * @param request 请求
     * @return 结果
     */
    private boolean isIgnoreAuth(HttpServletRequest request) {
        try {
            HandlerExecutionChain chain = handlerMapping.getHandler(request);
            // 如果获取不到处理器链，则说明该接口不存在，返回false
            if (chain == null) {
                return false;
            }

            Object handler = chain.getHandler();
            // 静态资源不允许访问
            if (!(handler instanceof HandlerMethod method)) {
                return false;
            }

            // 先检查方法，再检查类。
            if (method.hasMethodAnnotation(IgnoreAuth.class)) {
                return true;
            }
            if (method.getBeanType().isAnnotationPresent(IgnoreAuth.class)) {
                return true;
            }
        } catch (Exception e) {
            logger.error("JWT验证过程中出现异常", e);
            return false; // 出现异常默认不放行
        }

        return false;
    }
}
