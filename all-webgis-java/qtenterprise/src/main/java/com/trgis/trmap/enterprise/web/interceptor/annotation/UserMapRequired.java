package com.trgis.trmap.enterprise.web.interceptor.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 识别需要拦截的方法，给需拦截的方法注解
 * 
 * @author zhangqian
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface UserMapRequired {

}
