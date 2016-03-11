package net.coderland.server.api.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * User: zhangxin
 * Date: 2016-03-11 09:36:00
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TraceLogger {
    //是否需要记录日志
    boolean require() default true;
}
