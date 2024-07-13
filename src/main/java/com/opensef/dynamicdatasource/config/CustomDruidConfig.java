package com.opensef.dynamicdatasource.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.management.ObjectName;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import java.sql.Driver;
import java.sql.DriverManager;
import java.util.*;
import java.util.concurrent.ScheduledExecutorService;

public class CustomDruidConfig {

    private static final Logger LOG = LoggerFactory.getLogger(CustomDruidConfig.class);

    public final static int DEFAULT_INITIAL_SIZE = 0;
    public final static int DEFAULT_MAX_ACTIVE_SIZE = 8;
    public final static int DEFAULT_MAX_IDLE = 8;
    public final static int DEFAULT_MIN_IDLE = 0;
    public final static int DEFAULT_MAX_WAIT = -1;
    public final static String DEFAULT_VALIDATION_QUERY = null;                                                //
    public final static boolean DEFAULT_TEST_ON_BORROW = false;
    public final static boolean DEFAULT_TEST_ON_RETURN = false;
    public final static boolean DEFAULT_WHILE_IDLE = true;
    public static final long DEFAULT_TIME_BETWEEN_EVICTION_RUNS_MILLIS = 60 * 1000L;
    public static final long DEFAULT_TIME_BETWEEN_CONNECT_ERROR_MILLIS = 500;
    public static final int DEFAULT_NUM_TESTS_PER_EVICTION_RUN = 3;

    public static final long DEFAULT_MIN_EVICTABLE_IDLE_TIME_MILLIS = 1000L * 60L * 30L;
    public static final long DEFAULT_MAX_EVICTABLE_IDLE_TIME_MILLIS = 1000L * 60L * 60L * 7;
    public static final long DEFAULT_PHY_TIMEOUT_MILLIS = -1;

    protected boolean defaultAutoCommit = true;
    protected Boolean defaultReadOnly;
    protected Integer defaultTransactionIsolation;
    protected String defaultCatalog = null;

    protected String name;
    protected String username;
    protected String password;
    protected String jdbcUrl;
    protected String driverClass;
    protected Properties connectProperties = new Properties();

    protected PasswordCallback passwordCallback;
    protected NameCallback userCallback;
    protected int initialSize = DEFAULT_INITIAL_SIZE;
    protected int maxActive = DEFAULT_MAX_ACTIVE_SIZE;
    protected int minIdle = DEFAULT_MIN_IDLE;
    protected long maxWait = DEFAULT_MAX_WAIT;
    protected int notFullTimeoutRetryCount = 0;

    protected String validationQuery = DEFAULT_VALIDATION_QUERY;
    protected int validationQueryTimeout = -1;
    protected boolean testOnBorrow = DEFAULT_TEST_ON_BORROW;
    protected boolean testOnReturn = DEFAULT_TEST_ON_RETURN;
    protected boolean testWhileIdle = DEFAULT_WHILE_IDLE;
    protected boolean poolPreparedStatements = false;
    protected boolean sharePreparedStatements = false;
    protected int maxPoolPreparedStatementPerConnectionSize = 10;

    protected boolean inited = false;
    protected boolean initExceptionThrow = true;

    protected String filters;
    private boolean clearFiltersEnable = true;

    protected Driver driver;

    protected int queryTimeout;
    protected int transactionQueryTimeout;
    protected int maxWaitThreadCount = -1;
    protected boolean accessToUnderlyingConnectionAllowed = true;

    protected long timeBetweenEvictionRunsMillis = DEFAULT_TIME_BETWEEN_EVICTION_RUNS_MILLIS;
    protected long minEvictableIdleTimeMillis = DEFAULT_MIN_EVICTABLE_IDLE_TIME_MILLIS;
    protected long maxEvictableIdleTimeMillis = DEFAULT_MAX_EVICTABLE_IDLE_TIME_MILLIS;
    protected long keepAliveBetweenTimeMillis = DEFAULT_TIME_BETWEEN_EVICTION_RUNS_MILLIS * 2;
    protected long phyTimeoutMillis = DEFAULT_PHY_TIMEOUT_MILLIS;
    protected long phyMaxUseCount = -1;

    protected boolean removeAbandoned;
    protected long removeAbandonedTimeoutMillis = 300 * 1000;
    protected boolean logAbandoned;

    protected List<String> connectionInitSqls;

    protected String dbTypeName;

    protected long timeBetweenConnectErrorMillis = DEFAULT_TIME_BETWEEN_CONNECT_ERROR_MILLIS;

    protected int connectionErrorRetryAttempts = 1;
    protected boolean breakAfterAcquireFailure = false;
    protected long transactionThresholdMillis = 0L;

    private boolean dupCloseLogEnable = false;

    private ObjectName objectName;

    protected boolean isOracle = false;
    protected boolean useOracleImplicitCache = true;

    private boolean useUnfairLock;
    private boolean useLocalSessionState = true;

    protected long timeBetweenLogStatsMillis;

    private boolean asyncCloseConnectionEnable = false;
    protected int maxCreateTaskCount = 3;
    protected boolean failFast = false;
    protected ScheduledExecutorService destroyScheduler;
    protected ScheduledExecutorService createScheduler;

    protected boolean initVariants = false;
    protected boolean initGlobalVariants = false;
    protected int onFatalErrorMaxActive = 0;

    protected boolean killWhenSocketReadTimeout = false;
    private boolean useGlobalDataSourceStat = false;
    private boolean keepAlive = false;
    private boolean resetStatEnable = true;
    private boolean enable = true;
    private boolean logDifferentThread = true;
    protected boolean checkExecuteTime = false;


    public boolean isUseLocalSessionState() {
        return useLocalSessionState;
    }

    public void setUseLocalSessionState(boolean useLocalSessionState) {
        this.useLocalSessionState = useLocalSessionState;
    }

    public long getTimeBetweenLogStatsMillis() {
        return timeBetweenLogStatsMillis;
    }

    public void setTimeBetweenLogStatsMillis(long timeBetweenLogStatsMillis) {
        this.timeBetweenLogStatsMillis = timeBetweenLogStatsMillis;
    }

    public boolean isOracle() {
        return isOracle;
    }

    public void setOracle(boolean isOracle) {
        if (inited) {
            throw new IllegalStateException();
        }
        this.isOracle = isOracle;
    }

    public boolean isUseUnfairLock() {
        return this.useUnfairLock;
    }

    public void setUseUnfairLock(boolean useUnfairLock) {
        this.useUnfairLock = useUnfairLock;
    }

    public boolean isUseOracleImplicitCache() {
        return useOracleImplicitCache;
    }

    public void setUseOracleImplicitCache(boolean useOracleImplicitCache) {
        this.useOracleImplicitCache = useOracleImplicitCache;
    }

    public int getTransactionQueryTimeout() {
        if (transactionQueryTimeout <= 0) {
            return queryTimeout;
        }

        return transactionQueryTimeout;
    }

    public void setTransactionQueryTimeout(int transactionQueryTimeout) {
        this.transactionQueryTimeout = transactionQueryTimeout;
    }

    public boolean isDupCloseLogEnable() {
        return dupCloseLogEnable;
    }

    public void setDupCloseLogEnable(boolean dupCloseLogEnable) {
        this.dupCloseLogEnable = dupCloseLogEnable;
    }

    public ObjectName getObjectName() {
        return objectName;
    }

    public void setObjectName(ObjectName objectName) {
        this.objectName = objectName;
    }

    public long getTransactionThresholdMillis() {
        return transactionThresholdMillis;
    }

    public void setTransactionThresholdMillis(long transactionThresholdMillis) {
        this.transactionThresholdMillis = transactionThresholdMillis;
    }

    public boolean isBreakAfterAcquireFailure() {
        return breakAfterAcquireFailure;
    }

    public void setBreakAfterAcquireFailure(boolean breakAfterAcquireFailure) {
        this.breakAfterAcquireFailure = breakAfterAcquireFailure;
    }

    public int getConnectionErrorRetryAttempts() {
        return connectionErrorRetryAttempts;
    }

    public void setConnectionErrorRetryAttempts(int connectionErrorRetryAttempts) {
        this.connectionErrorRetryAttempts = connectionErrorRetryAttempts;
    }

    public int getMaxPoolPreparedStatementPerConnectionSize() {
        return maxPoolPreparedStatementPerConnectionSize;
    }

    public void setMaxPoolPreparedStatementPerConnectionSize(int maxPoolPreparedStatementPerConnectionSize) {
        if (maxPoolPreparedStatementPerConnectionSize > 0) {
            this.poolPreparedStatements = true;
        } else {
            this.poolPreparedStatements = false;
        }

        this.maxPoolPreparedStatementPerConnectionSize = maxPoolPreparedStatementPerConnectionSize;
    }

    public boolean isSharePreparedStatements() {
        return sharePreparedStatements;
    }

    public void setSharePreparedStatements(boolean sharePreparedStatements) {
        this.sharePreparedStatements = sharePreparedStatements;
    }

    public String getDbType() {
        return dbTypeName;
    }

    public void setDbType(String dbType) {
        this.dbTypeName = dbType;
    }


    public Collection<String> getConnectionInitSqls() {
        Collection<String> result = connectionInitSqls;
        if (result == null) {
            return Collections.emptyList();
        }
        return result;
    }

    public void setConnectionInitSqls(Collection<? extends Object> connectionInitSqls) {
        if ((connectionInitSqls != null) && (connectionInitSqls.size() > 0)) {
            ArrayList<String> newVal = null;
            for (Object o : connectionInitSqls) {
                if (o == null) {
                    continue;
                }

                String s = o.toString();
                s = s.trim();
                if (s.length() == 0) {
                    continue;
                }

                if (newVal == null) {
                    newVal = new ArrayList<String>();
                }
                newVal.add(s);
            }
            this.connectionInitSqls = newVal;
        } else {
            this.connectionInitSqls = null;
        }
    }

    public long getTimeBetweenConnectErrorMillis() {
        return timeBetweenConnectErrorMillis;
    }

    public void setTimeBetweenConnectErrorMillis(long timeBetweenConnectErrorMillis) {
        this.timeBetweenConnectErrorMillis = timeBetweenConnectErrorMillis;
    }

    public int getMaxOpenPreparedStatements() {
        return maxPoolPreparedStatementPerConnectionSize;
    }

    public void setMaxOpenPreparedStatements(int maxOpenPreparedStatements) {
        this.setMaxPoolPreparedStatementPerConnectionSize(maxOpenPreparedStatements);
    }

    public boolean isLogAbandoned() {
        return logAbandoned;
    }

    public void setLogAbandoned(boolean logAbandoned) {
        this.logAbandoned = logAbandoned;
    }

    public int getRemoveAbandonedTimeout() {
        return (int) (removeAbandonedTimeoutMillis / 1000);
    }

    public void setRemoveAbandonedTimeout(int removeAbandonedTimeout) {
        this.removeAbandonedTimeoutMillis = (long) removeAbandonedTimeout * 1000;
    }

    public void setRemoveAbandonedTimeoutMillis(long removeAbandonedTimeoutMillis) {
        this.removeAbandonedTimeoutMillis = removeAbandonedTimeoutMillis;
    }

    public long getRemoveAbandonedTimeoutMillis() {
        return removeAbandonedTimeoutMillis;
    }

    public boolean isRemoveAbandoned() {
        return removeAbandoned;
    }

    public void setRemoveAbandoned(boolean removeAbandoned) {
        this.removeAbandoned = removeAbandoned;
    }

    public long getMinEvictableIdleTimeMillis() {
        return minEvictableIdleTimeMillis;
    }

    public void setMinEvictableIdleTimeMillis(long minEvictableIdleTimeMillis) {
        if (minEvictableIdleTimeMillis < 1000 * 30) {
            LOG.error("minEvictableIdleTimeMillis should be greater than 30000");
        }

        this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
    }

    public long getKeepAliveBetweenTimeMillis() {
        return keepAliveBetweenTimeMillis;
    }

    public void setKeepAliveBetweenTimeMillis(long keepAliveBetweenTimeMillis) {
        if (keepAliveBetweenTimeMillis < 1000 * 30) {
            LOG.error("keepAliveBetweenTimeMillis should be greater than 30000");
        }

        if (keepAliveBetweenTimeMillis <= timeBetweenEvictionRunsMillis) {
            LOG.warn("keepAliveBetweenTimeMillis should be greater than timeBetweenEvictionRunsMillis");
        }

        this.keepAliveBetweenTimeMillis = keepAliveBetweenTimeMillis;
    }

    public long getMaxEvictableIdleTimeMillis() {
        return maxEvictableIdleTimeMillis;
    }


    public void setMaxEvictableIdleTimeMillis(long maxEvictableIdleTimeMillis) {
        if (maxEvictableIdleTimeMillis < 1000 * 30) {
            LOG.error("maxEvictableIdleTimeMillis should be greater than 30000");
        }

        if (inited && maxEvictableIdleTimeMillis < minEvictableIdleTimeMillis) {
            throw new IllegalArgumentException("maxEvictableIdleTimeMillis must be grater than minEvictableIdleTimeMillis");
        }

        this.maxEvictableIdleTimeMillis = maxEvictableIdleTimeMillis;
    }

    public long getPhyTimeoutMillis() {
        return phyTimeoutMillis;
    }

    public void setPhyTimeoutMillis(long phyTimeoutMillis) {
        this.phyTimeoutMillis = phyTimeoutMillis;
    }

    public long getPhyMaxUseCount() {
        return phyMaxUseCount;
    }

    public void setPhyMaxUseCount(long phyMaxUseCount) {
        this.phyMaxUseCount = phyMaxUseCount;
    }

    public long getTimeBetweenEvictionRunsMillis() {
        return timeBetweenEvictionRunsMillis;
    }

    public void setTimeBetweenEvictionRunsMillis(long timeBetweenEvictionRunsMillis) {
        this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
    }

    public int getMaxWaitThreadCount() {
        return maxWaitThreadCount;
    }

    public void setMaxWaitThreadCount(int maxWaithThreadCount) {
        this.maxWaitThreadCount = maxWaithThreadCount;
    }

    public String getValidationQuery() {
        return validationQuery;
    }

    public void setValidationQuery(String validationQuery) {
        this.validationQuery = validationQuery;
    }

    public int getValidationQueryTimeout() {
        return validationQueryTimeout;
    }

    public void setValidationQueryTimeout(int validationQueryTimeout) {
        if (validationQueryTimeout < 0 && dbTypeName.equalsIgnoreCase("sqlserver")) {
            LOG.error("validationQueryTimeout should be >= 0");
        }
        this.validationQueryTimeout = validationQueryTimeout;
    }

    public boolean isAccessToUnderlyingConnectionAllowed() {
        return accessToUnderlyingConnectionAllowed;
    }

    public void setAccessToUnderlyingConnectionAllowed(boolean accessToUnderlyingConnectionAllowed) {
        this.accessToUnderlyingConnectionAllowed = accessToUnderlyingConnectionAllowed;
    }

    public boolean isTestOnBorrow() {
        return testOnBorrow;
    }

    public void setTestOnBorrow(boolean testOnBorrow) {
        this.testOnBorrow = testOnBorrow;
    }

    public boolean isTestOnReturn() {
        return testOnReturn;
    }

    public void setTestOnReturn(boolean testOnReturn) {
        this.testOnReturn = testOnReturn;
    }

    public boolean isTestWhileIdle() {
        return testWhileIdle;
    }

    public void setTestWhileIdle(boolean testWhileIdle) {
        this.testWhileIdle = testWhileIdle;
    }

    public boolean isDefaultAutoCommit() {
        return defaultAutoCommit;
    }

    public void setDefaultAutoCommit(boolean defaultAutoCommit) {
        this.defaultAutoCommit = defaultAutoCommit;
    }

    public Boolean getDefaultReadOnly() {
        return defaultReadOnly;
    }

    public void setDefaultReadOnly(Boolean defaultReadOnly) {
        this.defaultReadOnly = defaultReadOnly;
    }

    public Integer getDefaultTransactionIsolation() {
        return defaultTransactionIsolation;
    }

    public void setDefaultTransactionIsolation(Integer defaultTransactionIsolation) {
        this.defaultTransactionIsolation = defaultTransactionIsolation;
    }

    public String getDefaultCatalog() {
        return defaultCatalog;
    }

    public void setDefaultCatalog(String defaultCatalog) {
        this.defaultCatalog = defaultCatalog;
    }

    public PasswordCallback getPasswordCallback() {
        return passwordCallback;
    }

    public void setPasswordCallback(PasswordCallback passwordCallback) {
        this.passwordCallback = passwordCallback;
    }

    public NameCallback getUserCallback() {
        return userCallback;
    }

    public void setUserCallback(NameCallback userCallback) {
        this.userCallback = userCallback;
    }

    public boolean isInitVariants() {
        return initVariants;
    }

    public void setInitVariants(boolean initVariants) {
        this.initVariants = initVariants;
    }

    public boolean isInitGlobalVariants() {
        return initGlobalVariants;
    }

    public void setInitGlobalVariants(boolean initGlobalVariants) {
        this.initGlobalVariants = initGlobalVariants;
    }

    /**
     * Retrieves the number of seconds the driver will wait for a <code>Statement</code> object to execute. If the limit
     * is exceeded, a <code>SQLException</code> is thrown.
     *
     * @return the current query timeout limit in seconds; zero means there is no limit
     * <code>Statement</code>
     * @see #setQueryTimeout
     */
    public int getQueryTimeout() {
        return queryTimeout;
    }

    /**
     * Sets the number of seconds the driver will wait for a <code>Statement</code> object to execute to the given
     * number of seconds. If the limit is exceeded, an <code>SQLException</code> is thrown. A JDBC driver must apply
     * this limit to the <code>execute</code>, <code>executeQuery</code> and <code>executeUpdate</code> methods. JDBC
     * driver implementations may also apply this limit to <code>ResultSet</code> methods (consult your driver vendor
     * documentation for details).
     *
     * @param seconds the new query timeout limit in seconds; zero means there is no limit
     * @see #getQueryTimeout
     */
    public void setQueryTimeout(int seconds) {
        this.queryTimeout = seconds;
    }

    public String getName() {
        if (name != null) {
            return name;
        }
        return "DataSource-" + System.identityHashCode(this);
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isPoolPreparedStatements() {
        return poolPreparedStatements;
    }

    public void setPoolPreparedStatements(boolean value) {
        this.poolPreparedStatements = value;
    }

    public long getMaxWait() {
        return maxWait;
    }

    public void setMaxWait(long maxWaitMillis) {
        this.maxWait = maxWaitMillis;
    }

    public int getNotFullTimeoutRetryCount() {
        return notFullTimeoutRetryCount;
    }


    public void setNotFullTimeoutRetryCount(int notFullTimeoutRetryCount) {
        this.notFullTimeoutRetryCount = notFullTimeoutRetryCount;
    }

    public int getMinIdle() {
        return minIdle;
    }

    public void setMinIdle(int value) {
        this.minIdle = value;
    }

    public int getInitialSize() {
        return initialSize;
    }

    public void setInitialSize(int initialSize) {
        if (this.initialSize == initialSize) {
            return;
        }

        if (inited) {
            throw new UnsupportedOperationException();
        }

        this.initialSize = initialSize;
    }

    public int getMaxActive() {
        return maxActive;
    }

    public void setMaxActive(int maxActive) {
        this.maxActive = maxActive;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Properties getConnectProperties() {
        return connectProperties;
    }

    public void setConnectProperties(Properties properties) {
        this.connectProperties = properties;
    }

    public void setConnectionProperties(String connectionProperties) {
        if (connectionProperties == null || connectionProperties.trim().length() == 0) {
            setConnectProperties(null);
            return;
        }

        String[] entries = connectionProperties.split(";");
        Properties properties = new Properties();
        for (int i = 0; i < entries.length; i++) {
            String entry = entries[i];
            if (entry.length() > 0) {
                int index = entry.indexOf('=');
                if (index > 0) {
                    String name = entry.substring(0, index);
                    String value = entry.substring(index + 1);
                    properties.setProperty(name, value);
                } else {
                    // no value is empty string which is how java.util.Properties works
                    properties.setProperty(entry, "");
                }
            }
        }

        setConnectProperties(properties);
    }

    public String getUrl() {
        return jdbcUrl;
    }

    public String getRawJdbcUrl() {
        return jdbcUrl;
    }

    public void setUrl(String jdbcUrl) {
        this.jdbcUrl = jdbcUrl;
    }

    public String getDriverClassName() {
        return driverClass;
    }

    public void setDriverClassName(String driverClass) {
        this.driverClass = driverClass;
    }

    public String getFilters() {
        return filters;
    }

    public void setFilters(String filters) {
        this.filters = filters;
    }

    public void setLoginTimeout(int seconds) {
        DriverManager.setLoginTimeout(seconds);
    }

    public int getLoginTimeout() {
        return DriverManager.getLoginTimeout();
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public boolean isClearFiltersEnable() {
        return clearFiltersEnable;
    }

    public void setClearFiltersEnable(boolean clearFiltersEnable) {
        this.clearFiltersEnable = clearFiltersEnable;
    }

    public boolean isAsyncCloseConnectionEnable() {
        if (isRemoveAbandoned()) {
            return true;
        }
        return asyncCloseConnectionEnable;
    }

    public void setAsyncCloseConnectionEnable(boolean asyncCloseConnectionEnable) {
        this.asyncCloseConnectionEnable = asyncCloseConnectionEnable;
    }

    public ScheduledExecutorService getCreateScheduler() {
        return createScheduler;
    }

    public void setCreateScheduler(ScheduledExecutorService createScheduler) {
        if (isInited()) {
            throw new RuntimeException("dataSource inited.");
        }
        this.createScheduler = createScheduler;
    }

    public ScheduledExecutorService getDestroyScheduler() {
        return destroyScheduler;
    }


    public void setDestroyScheduler(ScheduledExecutorService destroyScheduler) {
        if (isInited()) {
            throw new RuntimeException("dataSource inited.");
        }
        this.destroyScheduler = destroyScheduler;
    }

    public boolean isInited() {
        return this.inited;
    }


    public int getMaxCreateTaskCount() {
        return maxCreateTaskCount;
    }


    public void setMaxCreateTaskCount(int maxCreateTaskCount) {
        if (maxCreateTaskCount < 1) {
            throw new IllegalArgumentException();
        }

        this.maxCreateTaskCount = maxCreateTaskCount;
    }

    public boolean isFailFast() {
        return failFast;
    }

    public void setFailFast(boolean failFast) {
        this.failFast = failFast;
    }

    public int getOnFatalErrorMaxActive() {
        return onFatalErrorMaxActive;
    }

    public void setOnFatalErrorMaxActive(int onFatalErrorMaxActive) {
        this.onFatalErrorMaxActive = onFatalErrorMaxActive;
    }

    /**
     * @since 1.1.11
     */
    public boolean isInitExceptionThrow() {
        return initExceptionThrow;
    }

    /**
     * @since 1.1.11
     */
    public void setInitExceptionThrow(boolean initExceptionThrow) {
        this.initExceptionThrow = initExceptionThrow;
    }

    public boolean isKillWhenSocketReadTimeout() {
        return killWhenSocketReadTimeout;
    }

    public void setKillWhenSocketReadTimeout(boolean killWhenSocketTimeOut) {
        this.killWhenSocketReadTimeout = killWhenSocketTimeOut;
    }

    public boolean isUseGlobalDataSourceStat() {
        return useGlobalDataSourceStat;
    }

    public void setUseGlobalDataSourceStat(boolean useGlobalDataSourceStat) {
        this.useGlobalDataSourceStat = useGlobalDataSourceStat;
    }

    public boolean isKeepAlive() {
        return keepAlive;
    }

    public void setKeepAlive(boolean keepAlive) {
        this.keepAlive = keepAlive;
    }

    public boolean isResetStatEnable() {
        return resetStatEnable;
    }

    public void setResetStatEnable(boolean resetStatEnable) {
        this.resetStatEnable = resetStatEnable;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public boolean isLogDifferentThread() {
        return logDifferentThread;
    }

    public void setLogDifferentThread(boolean logDifferentThread) {
        this.logDifferentThread = logDifferentThread;
    }

    public boolean isCheckExecuteTime() {
        return checkExecuteTime;
    }

    public void setCheckExecuteTime(boolean checkExecuteTime) {
        this.checkExecuteTime = checkExecuteTime;
    }

}
