package com.leo.checkin.annotaion;

import java.lang.annotation.*;

/**
 * @Description: 不需要登录注解
 * @Author: leo
 * @Date: 2021/9/11 23:37
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface NoNeedLogin {
    String value() default "";
}
