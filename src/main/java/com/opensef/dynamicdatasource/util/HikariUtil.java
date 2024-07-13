package com.opensef.dynamicdatasource.util;

import com.opensef.dynamicdatasource.config.CustomHikariConfig;
import com.zaxxer.hikari.HikariConfig;
import org.springframework.util.StringUtils;

public class HikariUtil {

    private static final int DEFAULT_POOL_SIZE = 10;

    public static HikariConfig convertFromCustomHikariConfig(CustomHikariConfig customHikariConfig) {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setCatalog(customHikariConfig.getCatalog());
        hikariConfig.setConnectionTimeout(customHikariConfig.getConnectionTimeout());
        hikariConfig.setValidationTimeout(customHikariConfig.getValidationTimeout());
        hikariConfig.setIdleTimeout(customHikariConfig.getIdleTimeout());
        hikariConfig.setLeakDetectionThreshold(customHikariConfig.getLeakDetectionThreshold());
        hikariConfig.setMaxLifetime(customHikariConfig.getMaxLifetime());
        hikariConfig.setMaximumPoolSize(customHikariConfig.getMaximumPoolSize());
        hikariConfig.setMinimumIdle(customHikariConfig.getMinimumIdle());
        hikariConfig.setInitializationFailTimeout(customHikariConfig.getInitializationFailTimeout());
        hikariConfig.setConnectionInitSql(customHikariConfig.getConnectionInitSql());
        hikariConfig.setConnectionTestQuery(customHikariConfig.getConnectionTestQuery());
        hikariConfig.setDataSourceClassName(customHikariConfig.getDataSourceClassName());
        hikariConfig.setDataSourceJNDI(customHikariConfig.getDataSourceJNDI());
        if (StringUtils.hasText(customHikariConfig.getDriverClassName())) {
            hikariConfig.setDriverClassName(customHikariConfig.getDriverClassName());
        }
        if (StringUtils.hasText(customHikariConfig.getExceptionOverrideClassName())) {
            hikariConfig.setExceptionOverrideClassName(customHikariConfig.getExceptionOverrideClassName());
        }
        hikariConfig.setJdbcUrl(customHikariConfig.getJdbcUrl());
        hikariConfig.setPoolName(customHikariConfig.getPoolName());
        hikariConfig.setSchema(customHikariConfig.getSchema());
        hikariConfig.setTransactionIsolation(customHikariConfig.getTransactionIsolation());
        hikariConfig.setAutoCommit(customHikariConfig.isAutoCommit());
        hikariConfig.setReadOnly(customHikariConfig.isReadOnly());
        hikariConfig.setIsolateInternalQueries(customHikariConfig.isIsolateInternalQueries());
        hikariConfig.setRegisterMbeans(customHikariConfig.isRegisterMbeans());
        hikariConfig.setAllowPoolSuspension(customHikariConfig.isAllowPoolSuspension());
        hikariConfig.setDataSourceProperties(customHikariConfig.getDataSourceProperties());
        hikariConfig.setScheduledExecutor(customHikariConfig.getScheduledExecutor());
        hikariConfig.setHealthCheckProperties(customHikariConfig.getHealthCheckProperties());
        return hikariConfig;
    }

    public static HikariConfig copy(HikariConfig source) {
        if (source.getMaximumPoolSize() < 1) {
            source.setMaximumPoolSize(DEFAULT_POOL_SIZE);
        }

        if (source.getMinimumIdle() < 0 || source.getMinimumIdle() > source.getMaximumPoolSize()) {
            source.setMinimumIdle(source.getMaximumPoolSize());
        }

        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setPoolName(null);
        hikariConfig.setCatalog(source.getCatalog());
        hikariConfig.setConnectionTimeout(source.getConnectionTimeout());
        hikariConfig.setValidationTimeout(source.getValidationTimeout());
        hikariConfig.setIdleTimeout(source.getIdleTimeout());
        hikariConfig.setLeakDetectionThreshold(source.getLeakDetectionThreshold());
        hikariConfig.setMaxLifetime(source.getMaxLifetime());
        hikariConfig.setMaximumPoolSize(source.getMaximumPoolSize());
        hikariConfig.setMinimumIdle(source.getMinimumIdle());
        hikariConfig.setUsername(source.getUsername());
        hikariConfig.setPassword(source.getPassword());
        hikariConfig.setInitializationFailTimeout(source.getInitializationFailTimeout());
        hikariConfig.setConnectionInitSql(source.getConnectionInitSql());
        hikariConfig.setConnectionTestQuery(source.getConnectionTestQuery());
        hikariConfig.setDataSourceClassName(source.getDataSourceClassName());
        hikariConfig.setDataSourceJNDI(source.getDataSourceJNDI());
        if (StringUtils.hasText(source.getDriverClassName())) {
            hikariConfig.setDriverClassName(source.getDriverClassName());
        }
        if (StringUtils.hasText(source.getExceptionOverrideClassName())) {
            hikariConfig.setExceptionOverrideClassName(source.getExceptionOverrideClassName());
        }
        hikariConfig.setJdbcUrl(source.getJdbcUrl());
        hikariConfig.setPoolName(source.getPoolName());
        hikariConfig.setSchema(source.getSchema());
        hikariConfig.setTransactionIsolation(source.getTransactionIsolation());
        hikariConfig.setAutoCommit(source.isAutoCommit());
        hikariConfig.setReadOnly(source.isReadOnly());
        hikariConfig.setIsolateInternalQueries(source.isIsolateInternalQueries());
        hikariConfig.setRegisterMbeans(source.isRegisterMbeans());
        hikariConfig.setAllowPoolSuspension(source.isAllowPoolSuspension());
        hikariConfig.setDataSourceProperties(source.getDataSourceProperties());
        hikariConfig.setScheduledExecutor(source.getScheduledExecutor());
        hikariConfig.setHealthCheckProperties(source.getHealthCheckProperties());
        return hikariConfig;
    }

}
