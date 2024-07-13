package com.opensef.dynamicdatasource;

/**
 * 动态数据源异常
 */
public class DynamicDataSourceException extends RuntimeException {

    public DynamicDataSourceException(String message) {
        super(message);
    }

    public DynamicDataSourceException(String message, Throwable cause) {
        super(message, cause);
    }

}
