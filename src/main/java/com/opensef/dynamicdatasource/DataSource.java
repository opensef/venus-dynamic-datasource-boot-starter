package com.opensef.dynamicdatasource;

import java.lang.annotation.*;

/**
 * 动态数据源注解
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataSource {

    String value();

}
