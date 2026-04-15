package com.xichen.Annotation;

import java.lang.annotation.*;

/**
 * 忽略认证注解，忽略JWT验证，开放API
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface IgnoreAuth {

}
