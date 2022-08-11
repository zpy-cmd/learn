package com.sunny.common.response;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ClassName NotControllerResponseAdvice
 * @Author 张普裕
 * @Version 1.0.0
 * @Description 新增不进行封装注解
 * @CreateTime 2022年08月11日 11:24:00
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface NotControllerResponseAdvice {
}
