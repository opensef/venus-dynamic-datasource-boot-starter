package com.opensef.dynamicdatasource;

/**
 * 设置当前线程数据源
 */
public class DynamicDataSourceContextHolder {

    private static final ThreadLocal<String> LOCAL = new ThreadLocal<>();

    public static void putDataSource(String dataSource) {
        LOCAL.set(dataSource);
    }

    public static String getDataSource() {
        return LOCAL.get();
    }

    public static void clearDataSource() {
        LOCAL.remove();
    }

}
