package com.opensef.dynamicdatasource.util;

import com.alibaba.druid.pool.DruidDataSource;
import com.opensef.dynamicdatasource.config.CustomDruidConfig;

import java.sql.SQLException;

public class DruidUtil {

    public static DruidDataSource convertFromCustomDruidConfig(CustomDruidConfig customDruidConfig) {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setDefaultAutoCommit(customDruidConfig.isDefaultAutoCommit());
        druidDataSource.setDefaultReadOnly(customDruidConfig.getDefaultReadOnly());
        druidDataSource.setDefaultTransactionIsolation(customDruidConfig.getDefaultTransactionIsolation());
        druidDataSource.setDefaultCatalog(customDruidConfig.getDefaultCatalog());
        druidDataSource.setName(customDruidConfig.getName());
        druidDataSource.setUsername(customDruidConfig.getUsername());
        druidDataSource.setPassword(customDruidConfig.getPassword());
        druidDataSource.setUrl(customDruidConfig.getUrl());
        druidDataSource.setDriverClassName(customDruidConfig.getDriverClassName());
        druidDataSource.setConnectProperties(customDruidConfig.getConnectProperties());
        druidDataSource.setPasswordCallback(customDruidConfig.getPasswordCallback());
        druidDataSource.setUserCallback(customDruidConfig.getUserCallback());
        druidDataSource.setInitialSize(customDruidConfig.getInitialSize());
        druidDataSource.setMaxActive(customDruidConfig.getMaxActive());
        druidDataSource.setMinIdle(customDruidConfig.getMinIdle());
        druidDataSource.setMaxWait(customDruidConfig.getMaxWait());
        druidDataSource.setNotFullTimeoutRetryCount(customDruidConfig.getNotFullTimeoutRetryCount());
        druidDataSource.setValidationQuery(customDruidConfig.getValidationQuery());
        druidDataSource.setValidationQueryTimeout(customDruidConfig.getValidationQueryTimeout());
        druidDataSource.setTestOnBorrow(customDruidConfig.isTestOnBorrow());
        druidDataSource.setTestOnReturn(customDruidConfig.isTestOnReturn());
        druidDataSource.setTestWhileIdle(customDruidConfig.isTestWhileIdle());
        druidDataSource.setPoolPreparedStatements(customDruidConfig.isPoolPreparedStatements());
        druidDataSource.setSharePreparedStatements(customDruidConfig.isSharePreparedStatements());
        druidDataSource.setMaxPoolPreparedStatementPerConnectionSize(customDruidConfig.getMaxPoolPreparedStatementPerConnectionSize());
        druidDataSource.setInitExceptionThrow(customDruidConfig.isInitExceptionThrow());
        try {
            druidDataSource.setFilters(customDruidConfig.getFilters());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        druidDataSource.setClearFiltersEnable(customDruidConfig.isClearFiltersEnable());
        druidDataSource.setDriver(customDruidConfig.getDriver());
        druidDataSource.setQueryTimeout(customDruidConfig.getQueryTimeout());
        druidDataSource.setTransactionQueryTimeout(customDruidConfig.getTransactionQueryTimeout());
        druidDataSource.setMaxWaitThreadCount(customDruidConfig.getMaxWaitThreadCount());
        druidDataSource.setAccessToUnderlyingConnectionAllowed(customDruidConfig.isAccessToUnderlyingConnectionAllowed());
        druidDataSource.setTimeBetweenEvictionRunsMillis(customDruidConfig.getTimeBetweenEvictionRunsMillis());
        druidDataSource.setMinEvictableIdleTimeMillis(customDruidConfig.getMinEvictableIdleTimeMillis());
        druidDataSource.setMaxEvictableIdleTimeMillis(customDruidConfig.getMaxEvictableIdleTimeMillis());
        druidDataSource.setKeepAliveBetweenTimeMillis(customDruidConfig.getKeepAliveBetweenTimeMillis());
        druidDataSource.setPhyTimeoutMillis(customDruidConfig.getPhyTimeoutMillis());
        druidDataSource.setPhyMaxUseCount(customDruidConfig.getPhyMaxUseCount());
        druidDataSource.setRemoveAbandoned(customDruidConfig.isRemoveAbandoned());
        druidDataSource.setRemoveAbandonedTimeoutMillis(customDruidConfig.getRemoveAbandonedTimeoutMillis());
        druidDataSource.setLogAbandoned(customDruidConfig.isLogAbandoned());
        druidDataSource.setConnectionInitSqls(customDruidConfig.getConnectionInitSqls());
        druidDataSource.setDbType(customDruidConfig.getDbType());
        druidDataSource.setTimeBetweenConnectErrorMillis(customDruidConfig.getTimeBetweenConnectErrorMillis());
        druidDataSource.setConnectionErrorRetryAttempts(customDruidConfig.getConnectionErrorRetryAttempts());
        druidDataSource.setBreakAfterAcquireFailure(customDruidConfig.isBreakAfterAcquireFailure());
        druidDataSource.setTransactionThresholdMillis(customDruidConfig.getTransactionThresholdMillis());
        druidDataSource.setDupCloseLogEnable(customDruidConfig.isDupCloseLogEnable());
        druidDataSource.setObjectName(customDruidConfig.getObjectName());
        druidDataSource.setOracle(customDruidConfig.isOracle());
        druidDataSource.setUseOracleImplicitCache(customDruidConfig.isUseOracleImplicitCache());
        druidDataSource.setUseUnfairLock(customDruidConfig.isUseUnfairLock());
        druidDataSource.setUseLocalSessionState(customDruidConfig.isUseLocalSessionState());
        druidDataSource.setTimeBetweenLogStatsMillis(customDruidConfig.getTimeBetweenLogStatsMillis());
        druidDataSource.setAsyncCloseConnectionEnable(customDruidConfig.isAsyncCloseConnectionEnable());
        druidDataSource.setMaxCreateTaskCount(customDruidConfig.getMaxCreateTaskCount());
        druidDataSource.setFailFast(customDruidConfig.isFailFast());
        druidDataSource.setDestroyScheduler(customDruidConfig.getDestroyScheduler());
        druidDataSource.setCreateScheduler(customDruidConfig.getCreateScheduler());
        druidDataSource.setInitVariants(customDruidConfig.isInitVariants());
        druidDataSource.setInitGlobalVariants(customDruidConfig.isInitGlobalVariants());
        druidDataSource.setOnFatalErrorMaxActive(customDruidConfig.getOnFatalErrorMaxActive());
        druidDataSource.setKillWhenSocketReadTimeout(customDruidConfig.isKillWhenSocketReadTimeout());
        druidDataSource.setUseGlobalDataSourceStat(customDruidConfig.isUseGlobalDataSourceStat());
        druidDataSource.setKeepAlive(customDruidConfig.isKeepAlive());
        druidDataSource.setResetStatEnable(customDruidConfig.isResetStatEnable());
        druidDataSource.setEnable(customDruidConfig.isEnable());
        druidDataSource.setLogDifferentThread(customDruidConfig.isLogDifferentThread());
        druidDataSource.setCheckExecuteTime(customDruidConfig.isCheckExecuteTime());

        return druidDataSource;
    }

    public static DruidDataSource copy(DruidDataSource source) {
        DruidDataSource druidDataSource;
        try {
            druidDataSource = (DruidDataSource) source.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
        return druidDataSource;
    }

}
