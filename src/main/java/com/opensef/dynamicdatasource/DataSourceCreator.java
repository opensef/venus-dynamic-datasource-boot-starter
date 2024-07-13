package com.opensef.dynamicdatasource;

import com.alibaba.druid.pool.DruidDataSource;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;

/**
 * 数据源创建器
 */
public class DataSourceCreator {

    /**
     * 动态数据源-创建Hikari连接池
     *
     * @param hikariConfig         HikariConfig
     * @param dataSourceProperties DataSourceProperties
     * @return HikariDataSource
     */
    public static HikariDataSource createHikariDataSource(HikariConfig hikariConfig, DataSourceProperties dataSourceProperties) {
        String driverClassName = dataSourceProperties.getDriverClassName();
        if (StringUtils.isNotBlank(driverClassName)) {
            hikariConfig.setDriverClassName(driverClassName);
        }
        hikariConfig.setJdbcUrl(dataSourceProperties.getUrl());
        hikariConfig.setUsername(dataSourceProperties.getUsername());
        hikariConfig.setPassword(dataSourceProperties.getPassword());
        return new HikariDataSource(hikariConfig);
    }

    /**
     * 动态数据源-创建Druid连接池
     *
     * @param druidDataSource      DruidDataSource
     * @param dataSourceProperties DataSourceProperties
     * @return DruidDataSource
     */
    public static DruidDataSource createDruidDataSource(DruidDataSource druidDataSource, DataSourceProperties dataSourceProperties) {
        String driverClassName = dataSourceProperties.getDriverClassName();
        if (StringUtils.isNotBlank(driverClassName)) {
            druidDataSource.setDriverClassName(driverClassName);
        }
        druidDataSource.setUrl(dataSourceProperties.getUrl());
        druidDataSource.setUsername(dataSourceProperties.getUsername());
        druidDataSource.setPassword(dataSourceProperties.getPassword());

        return druidDataSource;
    }

}
