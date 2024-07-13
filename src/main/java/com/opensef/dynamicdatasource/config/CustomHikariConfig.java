package com.opensef.dynamicdatasource.config;

import com.zaxxer.hikari.SQLExceptionOverride;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.security.AccessControlException;
import java.sql.Connection;
import java.util.Properties;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadLocalRandom;

import static com.zaxxer.hikari.util.UtilityElf.getNullIfEmpty;
import static java.util.concurrent.TimeUnit.MINUTES;
import static java.util.concurrent.TimeUnit.SECONDS;

public class CustomHikariConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomHikariConfig.class);
    private static final char[] ID_CHARACTERS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    private static final long CONNECTION_TIMEOUT = SECONDS.toMillis(30);
    private static final long VALIDATION_TIMEOUT = SECONDS.toMillis(5);
    private static final long SOFT_TIMEOUT_FLOOR = Long.getLong("com.zaxxer.hikari.timeoutMs.floor", 250L);
    private static final long IDLE_TIMEOUT = MINUTES.toMillis(10);
    private static final long MAX_LIFETIME = MINUTES.toMillis(30);
    private static final long DEFAULT_KEEPALIVE_TIME = 0L;
    private static final int DEFAULT_POOL_SIZE = 10;

    // Properties changeable at runtime through the HikariConfigMXBean
    //
    private String catalog;
    private long connectionTimeout;
    private long validationTimeout;
    private long idleTimeout;
    private long leakDetectionThreshold;
    private long maxLifetime;
    private int maxPoolSize;
    private int minIdle;
    private String username;
    private String password;

    // Properties NOT changeable at runtime
    //
    private long initializationFailTimeout;
    private String connectionInitSql;
    private String connectionTestQuery;
    private String dataSourceClassName;
    private String dataSourceJndiName;
    private String driverClassName;
    private String exceptionOverrideClassName;
    private String jdbcUrl;
    private String poolName;
    private String schema;
    private String transactionIsolationName;
    private boolean isAutoCommit;
    private boolean isReadOnly;
    private boolean isIsolateInternalQueries;
    private boolean isRegisterMbeans;
    private boolean isAllowPoolSuspension;
    private Properties dataSourceProperties;
    private ScheduledExecutorService scheduledExecutor;
    private Properties healthCheckProperties;
    private long keepaliveTime;

    public CustomHikariConfig() {
        dataSourceProperties = new Properties();
        healthCheckProperties = new Properties();

        minIdle = DEFAULT_POOL_SIZE;
        maxPoolSize = DEFAULT_POOL_SIZE;
        maxLifetime = MAX_LIFETIME;
        connectionTimeout = CONNECTION_TIMEOUT;
        validationTimeout = VALIDATION_TIMEOUT;
        idleTimeout = IDLE_TIMEOUT;
        initializationFailTimeout = 1L;
        isAutoCommit = true;
        keepaliveTime = DEFAULT_KEEPALIVE_TIME;

    }

    // ***********************************************************************
    //                       HikariConfigMXBean methods
    // ***********************************************************************

    /**
     * Get the default catalog name to be set on connections.
     */
    public String getCatalog() {
        return catalog;
    }

    /**
     * Set the default catalog name to be set on connections.
     * WARNING: THIS VALUE SHOULD ONLY BE CHANGED WHILE THE POOL IS SUSPENDED, AFTER CONNECTIONS HAVE BEEN EVICTED.
     */
    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }


    /**
     * Get the maximum number of milliseconds that a client will wait for a connection from the pool. If this
     * time is exceeded without a connection becoming available, a SQLException will be thrown from
     * {@link javax.sql.DataSource#getConnection()}.
     *
     * @return the connection timeout in milliseconds
     */
    public long getConnectionTimeout() {
        return connectionTimeout;
    }

    /**
     * Set the maximum number of milliseconds that a client will wait for a connection from the pool. If this
     * time is exceeded without a connection becoming available, a SQLException will be thrown from
     * {@link javax.sql.DataSource#getConnection()}.
     *
     * @param connectionTimeoutMs the connection timeout in milliseconds
     */
    public void setConnectionTimeout(long connectionTimeoutMs) {
        this.connectionTimeout = connectionTimeoutMs;
    }

    /**
     * This property controls the maximum amount of time (in milliseconds) that a connection is allowed to sit
     * idle in the pool. Whether a connection is retired as idle or not is subject to a maximum variation of +30
     * seconds, and average variation of +15 seconds. A connection will never be retired as idle before this timeout.
     * A value of 0 means that idle connections are never removed from the pool.
     *
     * @return the idle timeout in milliseconds
     */
    public long getIdleTimeout() {
        return idleTimeout;
    }

    /**
     * This property controls the maximum amount of time (in milliseconds) that a connection is allowed to sit
     * idle in the pool. Whether a connection is retired as idle or not is subject to a maximum variation of +30
     * seconds, and average variation of +15 seconds. A connection will never be retired as idle before this timeout.
     * A value of 0 means that idle connections are never removed from the pool.
     *
     * @param idleTimeoutMs the idle timeout in milliseconds
     */
    public void setIdleTimeout(long idleTimeoutMs) {
        this.idleTimeout = idleTimeoutMs;
    }

    /**
     * This property controls the amount of time that a connection can be out of the pool before a message is
     * logged indicating a possible connection leak. A value of 0 means leak detection is disabled.
     *
     * @return the connection leak detection threshold in milliseconds
     */
    public long getLeakDetectionThreshold() {
        return leakDetectionThreshold;
    }

    /**
     * This property controls the amount of time that a connection can be out of the pool before a message is
     * logged indicating a possible connection leak. A value of 0 means leak detection is disabled.
     *
     * @param leakDetectionThresholdMs the connection leak detection threshold in milliseconds
     */
    public void setLeakDetectionThreshold(long leakDetectionThresholdMs) {
        this.leakDetectionThreshold = leakDetectionThresholdMs;
    }

    /**
     * This property controls the maximum lifetime of a connection in the pool. When a connection reaches this
     * timeout, even if recently used, it will be retired from the pool. An in-use connection will never be
     * retired, only when it is idle will it be removed.
     *
     * @return the maximum connection lifetime in milliseconds
     */
    public long getMaxLifetime() {
        return maxLifetime;
    }

    /**
     * This property controls the maximum lifetime of a connection in the pool. When a connection reaches this
     * timeout, even if recently used, it will be retired from the pool. An in-use connection will never be
     * retired, only when it is idle will it be removed.
     *
     * @param maxLifetimeMs the maximum connection lifetime in milliseconds
     */
    public void setMaxLifetime(long maxLifetimeMs) {
        this.maxLifetime = maxLifetimeMs;
    }

    /**
     * The property controls the maximum number of connections that HikariCP will keep in the pool,
     * including both idle and in-use connections.
     *
     * @return the maximum number of connections in the pool
     */
    public int getMaximumPoolSize() {
        return maxPoolSize;
    }

    /**
     * The property controls the maximum size that the pool is allowed to reach, including both idle and in-use
     * connections. Basically this value will determine the maximum number of actual connections to the database
     * backend.
     * <p>
     * When the pool reaches this size, and no idle connections are available, calls to getConnection() will
     * block for up to connectionTimeout milliseconds before timing out.
     *
     * @param maxPoolSize the maximum number of connections in the pool
     */
    public void setMaximumPoolSize(int maxPoolSize) {
        this.maxPoolSize = maxPoolSize;
    }

    /**
     * The property controls the minimum number of idle connections that HikariCP tries to maintain in the pool,
     * including both idle and in-use connections. If the idle connections dip below this value, HikariCP will
     * make a best effort to restore them quickly and efficiently.
     *
     * @return the minimum number of connections in the pool
     */
    public int getMinimumIdle() {
        return minIdle;
    }

    /**
     * The property controls the minimum number of idle connections that HikariCP tries to maintain in the pool,
     * including both idle and in-use connections. If the idle connections dip below this value, HikariCP will
     * make a best effort to restore them quickly and efficiently.
     *
     * @param minIdle the minimum number of idle connections in the pool to maintain
     */
    public void setMinimumIdle(int minIdle) {
        this.minIdle = minIdle;
    }

    /**
     * Get the default password to use for DataSource.getConnection(username, password) calls.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set the default password to use for DataSource.getConnection(username, password) calls.
     *
     * @param password the password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Get the default username used for DataSource.getConnection(username, password) calls.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Set the default username used for DataSource.getConnection(username, password) calls.
     *
     * @param username the username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Get the maximum number of milliseconds that the pool will wait for a connection to be validated as
     * alive.
     *
     * @return the validation timeout in milliseconds
     */
    public long getValidationTimeout() {
        return validationTimeout;
    }

    /**
     * Sets the maximum number of milliseconds that the pool will wait for a connection to be validated as
     * alive.
     *
     * @param validationTimeoutMs the validation timeout in milliseconds
     */
    public void setValidationTimeout(long validationTimeoutMs) {
        this.validationTimeout = validationTimeoutMs;
    }

    // ***********************************************************************
    //                     All other configuration methods
    // ***********************************************************************

    /**
     * Get the SQL query to be executed to test the validity of connections.
     *
     * @return the SQL query string, or null
     */
    public String getConnectionTestQuery() {
        return connectionTestQuery;
    }

    /**
     * Set the SQL query to be executed to test the validity of connections. Using
     * the JDBC4 <code>Connection.isValid()</code> method to test connection validity can
     * be more efficient on some databases and is recommended.
     *
     * @param connectionTestQuery a SQL query string
     */
    public void setConnectionTestQuery(String connectionTestQuery) {
        this.connectionTestQuery = connectionTestQuery;
    }

    /**
     * Get the SQL string that will be executed on all new connections when they are
     * created, before they are added to the pool.
     *
     * @return the SQL to execute on new connections, or null
     */
    public String getConnectionInitSql() {
        return connectionInitSql;
    }

    /**
     * Set the SQL string that will be executed on all new connections when they are
     * created, before they are added to the pool.  If this query fails, it will be
     * treated as a failed connection attempt.
     *
     * @param connectionInitSql the SQL to execute on new connections
     */
    public void setConnectionInitSql(String connectionInitSql) {
        this.connectionInitSql = connectionInitSql;
    }

    /**
     * Get the name of the JDBC {@link DataSource} class used to create Connections.
     *
     * @return the fully qualified name of the JDBC {@link DataSource} class
     */
    public String getDataSourceClassName() {
        return dataSourceClassName;
    }

    /**
     * Set the fully qualified class name of the JDBC {@link DataSource} that will be used create Connections.
     *
     * @param className the fully qualified name of the JDBC {@link DataSource} class
     */
    public void setDataSourceClassName(String className) {
        this.dataSourceClassName = className;
    }

    /**
     * Add a property (name/value pair) that will be used to configure the {@link DataSource}/{@link java.sql.Driver}.
     * <p>
     * In the case of a {@link DataSource}, the property names will be translated to Java setters following the Java Bean
     * naming convention.  For example, the property {@code cachePrepStmts} will translate into {@code setCachePrepStmts()}
     * with the {@code value} passed as a parameter.
     * <p>
     * In the case of a {@link java.sql.Driver}, the property will be added to a {@link Properties} instance that will
     * be passed to the driver during {@link java.sql.Driver#connect(String, Properties)} calls.
     *
     * @param propertyName the name of the property
     * @param value        the value to be used by the DataSource/Driver
     */
    public void addDataSourceProperty(String propertyName, Object value) {
        dataSourceProperties.put(propertyName, value);
    }

    public String getDataSourceJNDI() {
        return this.dataSourceJndiName;
    }

    public void setDataSourceJNDI(String jndiDataSource) {
        this.dataSourceJndiName = jndiDataSource;
    }

    public Properties getDataSourceProperties() {
        return dataSourceProperties;
    }

    public void setDataSourceProperties(Properties dsProperties) {
        dataSourceProperties.putAll(dsProperties);
    }

    public String getDriverClassName() {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    /**
     * Get the user supplied SQLExceptionOverride class name.
     *
     * @return the user supplied SQLExceptionOverride class name
     * @see SQLExceptionOverride
     */
    public String getExceptionOverrideClassName() {
        return this.exceptionOverrideClassName;
    }

    /**
     * Set the user supplied SQLExceptionOverride class name.
     *
     * @param exceptionOverrideClassName the user supplied SQLExceptionOverride class name
     * @see SQLExceptionOverride
     */
    public void setExceptionOverrideClassName(String exceptionOverrideClassName) {
        this.exceptionOverrideClassName = exceptionOverrideClassName;
    }

    public String getJdbcUrl() {
        return jdbcUrl;
    }

    public void setJdbcUrl(String jdbcUrl) {
        this.jdbcUrl = jdbcUrl;
    }

    /**
     * Get the default auto-commit behavior of connections in the pool.
     *
     * @return the default auto-commit behavior of connections
     */
    public boolean isAutoCommit() {
        return isAutoCommit;
    }

    /**
     * Set the default auto-commit behavior of connections in the pool.
     *
     * @param isAutoCommit the desired auto-commit default for connections
     */
    public void setAutoCommit(boolean isAutoCommit) {
        this.isAutoCommit = isAutoCommit;
    }

    /**
     * Get the pool suspension behavior (allowed or disallowed).
     *
     * @return the pool suspension behavior
     */
    public boolean isAllowPoolSuspension() {
        return isAllowPoolSuspension;
    }

    /**
     * Set whether or not pool suspension is allowed.  There is a performance
     * impact when pool suspension is enabled.  Unless you need it (for a
     * redundancy system for example) do not enable it.
     *
     * @param isAllowPoolSuspension the desired pool suspension allowance
     */
    public void setAllowPoolSuspension(boolean isAllowPoolSuspension) {
        this.isAllowPoolSuspension = isAllowPoolSuspension;
    }

    /**
     * Get the pool initialization failure timeout.  See {@code #setInitializationFailTimeout(long)}
     * for details.
     *
     * @return the number of milliseconds before the pool initialization fails
     * @see com.zaxxer.hikari.HikariConfig#setInitializationFailTimeout(long)
     */
    public long getInitializationFailTimeout() {
        return initializationFailTimeout;
    }

    /**
     * Set the pool initialization failure timeout.  This setting applies to pool
     * initialization when {@link com.zaxxer.hikari.HikariDataSource} is constructed with a {@link com.zaxxer.hikari.HikariConfig},
     * or when {@link com.zaxxer.hikari.HikariDataSource} is constructed using the no-arg constructor
     * and {@link com.zaxxer.hikari.HikariDataSource#getConnection()} is called.
     * <ul>
     *   <li>Any value greater than zero will be treated as a timeout for pool initialization.
     *       The calling thread will be blocked from continuing until a successful connection
     *       to the database, or until the timeout is reached.  If the timeout is reached, then
     *       a {@code PoolInitializationException} will be thrown. </li>
     *   <li>A value of zero will <i>not</i>  prevent the pool from starting in the
     *       case that a connection cannot be obtained. However, upon start the pool will
     *       attempt to obtain a connection and validate that the {@code connectionTestQuery}
     *       and {@code connectionInitSql} are valid.  If those validations fail, an exception
     *       will be thrown.  If a connection cannot be obtained, the validation is skipped
     *       and the the pool will start and continue to try to obtain connections in the
     *       background.  This can mean that callers to {@code DataSource#getConnection()} may
     *       encounter exceptions. </li>
     *   <li>A value less than zero will bypass any connection attempt and validation during
     *       startup, and therefore the pool will start immediately.  The pool will continue to
     *       try to obtain connections in the background. This can mean that callers to
     *       {@code DataSource#getConnection()} may encounter exceptions. </li>
     * </ul>
     * Note that if this timeout value is greater than or equal to zero (0), and therefore an
     * initial connection validation is performed, this timeout does not override the
     * {@code connectionTimeout} or {@code validationTimeout}; they will be honored before this
     * timeout is applied.  The default value is one millisecond.
     *
     * @param initializationFailTimeout the number of milliseconds before the
     *                                  pool initialization fails, or 0 to validate connection setup but continue with
     *                                  pool start, or less than zero to skip all initialization checks and start the
     *                                  pool without delay.
     */
    public void setInitializationFailTimeout(long initializationFailTimeout) {
        this.initializationFailTimeout = initializationFailTimeout;
    }

    /**
     * Determine whether internal pool queries, principally aliveness checks, will be isolated in their own transaction
     * via {@link Connection#rollback()}.  Defaults to {@code false}.
     *
     * @return {@code true} if internal pool queries are isolated, {@code false} if not
     */
    public boolean isIsolateInternalQueries() {
        return isIsolateInternalQueries;
    }

    /**
     * Configure whether internal pool queries, principally aliveness checks, will be isolated in their own transaction
     * via {@link Connection#rollback()}.  Defaults to {@code false}.
     *
     * @param isolate {@code true} if internal pool queries should be isolated, {@code false} if not
     */
    public void setIsolateInternalQueries(boolean isolate) {
        this.isIsolateInternalQueries = isolate;
    }

    public Properties getHealthCheckProperties() {
        return healthCheckProperties;
    }

    public void setHealthCheckProperties(Properties healthCheckProperties) {
        this.healthCheckProperties.putAll(healthCheckProperties);
    }

    public void addHealthCheckProperty(String key, String value) {
        healthCheckProperties.setProperty(key, value);
    }

    /**
     * This property controls the keepalive interval for a connection in the pool. An in-use connection will never be
     * tested by the keepalive thread, only when it is idle will it be tested.
     *
     * @return the interval in which connections will be tested for aliveness, thus keeping them alive by the act of checking. Value is in milliseconds, default is 0 (disabled).
     */
    public long getKeepaliveTime() {
        return keepaliveTime;
    }

    /**
     * This property controls the keepalive interval for a connection in the pool. An in-use connection will never be
     * tested by the keepalive thread, only when it is idle will it be tested.
     *
     * @param keepaliveTimeMs the interval in which connections will be tested for aliveness, thus keeping them alive by the act of checking. Value is in milliseconds, default is 0 (disabled).
     */
    public void setKeepaliveTime(long keepaliveTimeMs) {
        this.keepaliveTime = keepaliveTimeMs;
    }

    /**
     * Determine whether the Connections in the pool are in read-only mode.
     *
     * @return {@code true} if the Connections in the pool are read-only, {@code false} if not
     */
    public boolean isReadOnly() {
        return isReadOnly;
    }

    /**
     * Configures the Connections to be added to the pool as read-only Connections.
     *
     * @param readOnly {@code true} if the Connections in the pool are read-only, {@code false} if not
     */
    public void setReadOnly(boolean readOnly) {
        this.isReadOnly = readOnly;
    }

    /**
     * Determine whether HikariCP will self-register {@link com.zaxxer.hikari.HikariConfigMXBean} and {@link com.zaxxer.hikari.HikariPoolMXBean} instances
     * in JMX.
     *
     * @return {@code true} if HikariCP will register MXBeans, {@code false} if it will not
     */
    public boolean isRegisterMbeans() {
        return isRegisterMbeans;
    }

    /**
     * Configures whether HikariCP self-registers the {@link com.zaxxer.hikari.HikariConfigMXBean} and {@link com.zaxxer.hikari.HikariPoolMXBean} in JMX.
     *
     * @param register {@code true} if HikariCP should register MXBeans, {@code false} if it should not
     */
    public void setRegisterMbeans(boolean register) {
        this.isRegisterMbeans = register;
    }

    /**
     * The name of the connection pool.
     *
     * @return the name of the connection pool
     */
    public String getPoolName() {
        return poolName;
    }

    /**
     * Set the name of the connection pool.  This is primarily used for the MBean
     * to uniquely identify the pool configuration.
     *
     * @param poolName the name of the connection pool to use
     */
    public void setPoolName(String poolName) {
        this.poolName = poolName;
    }

    /**
     * Get the ScheduledExecutorService used for housekeeping.
     *
     * @return the executor
     */
    public ScheduledExecutorService getScheduledExecutor() {
        return scheduledExecutor;
    }

    /**
     * Set the ScheduledExecutorService used for housekeeping.
     *
     * @param executor the ScheduledExecutorService
     */
    public void setScheduledExecutor(ScheduledExecutorService executor) {
        this.scheduledExecutor = executor;
    }


    public String getTransactionIsolation() {
        return transactionIsolationName;
    }

    /**
     * Get the default schema name to be set on connections.
     *
     * @return the default schema name
     */
    public String getSchema() {
        return schema;
    }

    /**
     * Set the default schema name to be set on connections.
     *
     * @param schema the name of the default schema
     */
    public void setSchema(String schema) {
        this.schema = schema;
    }

    /**
     * Set the default transaction isolation level.  The specified value is the
     * constant name from the <code>Connection</code> class, eg.
     * <code>TRANSACTION_REPEATABLE_READ</code>.
     *
     * @param isolationLevel the name of the isolation level
     */
    public void setTransactionIsolation(String isolationLevel) {
        this.transactionIsolationName = isolationLevel;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    public void validate() {
        if (poolName == null) {
            poolName = generatePoolName();
        } else if (isRegisterMbeans && poolName.contains(":")) {
            throw new IllegalArgumentException("poolName cannot contain ':' when used with JMX");
        }

        // treat empty property as null
        // noinspection NonAtomicOperationOnVolatileField
        catalog = getNullIfEmpty(catalog);
        connectionInitSql = getNullIfEmpty(connectionInitSql);
        connectionTestQuery = getNullIfEmpty(connectionTestQuery);
        transactionIsolationName = getNullIfEmpty(transactionIsolationName);
        dataSourceClassName = getNullIfEmpty(dataSourceClassName);
        dataSourceJndiName = getNullIfEmpty(dataSourceJndiName);
        driverClassName = getNullIfEmpty(driverClassName);
        jdbcUrl = getNullIfEmpty(jdbcUrl);

        validateNumerics();

    }

    private void validateNumerics() {
        if (maxLifetime != 0 && maxLifetime < SECONDS.toMillis(30)) {
            LOGGER.warn("{} - maxLifetime is less than 30000ms, setting to default {}ms.", poolName, MAX_LIFETIME);
            maxLifetime = MAX_LIFETIME;
        }

        // keepalive time must larger then 30 seconds
        if (keepaliveTime != 0 && keepaliveTime < SECONDS.toMillis(30)) {
            LOGGER.warn("{} - keepaliveTime is less than 30000ms, disabling it.", poolName);
            keepaliveTime = DEFAULT_KEEPALIVE_TIME;
        }

        // keepalive time must be less than maxLifetime (if maxLifetime is enabled)
        if (keepaliveTime != 0 && maxLifetime != 0 && keepaliveTime >= maxLifetime) {
            LOGGER.warn("{} - keepaliveTime is greater than or equal to maxLifetime, disabling it.", poolName);
            keepaliveTime = DEFAULT_KEEPALIVE_TIME;
        }

        if (leakDetectionThreshold > 0) {
            if (leakDetectionThreshold < SECONDS.toMillis(2) || (leakDetectionThreshold > maxLifetime && maxLifetime > 0)) {
                LOGGER.warn("{} - leakDetectionThreshold is less than 2000ms or more than maxLifetime, disabling it.", poolName);
                leakDetectionThreshold = 0;
            }
        }

        if (connectionTimeout < SOFT_TIMEOUT_FLOOR) {
            LOGGER.warn("{} - connectionTimeout is less than {}ms, setting to {}ms.", poolName, SOFT_TIMEOUT_FLOOR, CONNECTION_TIMEOUT);
            connectionTimeout = CONNECTION_TIMEOUT;
        }

        if (validationTimeout < SOFT_TIMEOUT_FLOOR) {
            LOGGER.warn("{} - validationTimeout is less than {}ms, setting to {}ms.", poolName, SOFT_TIMEOUT_FLOOR, VALIDATION_TIMEOUT);
            validationTimeout = VALIDATION_TIMEOUT;
        }

        if (maxPoolSize < 1) {
            maxPoolSize = DEFAULT_POOL_SIZE;
        }

        if (minIdle < 0 || minIdle > maxPoolSize) {
            minIdle = maxPoolSize;
        }

        if (idleTimeout + SECONDS.toMillis(1) > maxLifetime && maxLifetime > 0 && minIdle < maxPoolSize) {
            LOGGER.warn("{} - idleTimeout is close to or more than maxLifetime, disabling it.", poolName);
            idleTimeout = 0L;
        } else if (idleTimeout != 0 && idleTimeout < SECONDS.toMillis(10) && minIdle < maxPoolSize) {
            LOGGER.warn("{} - idleTimeout is less than 10000ms, setting to default {}ms.", poolName, IDLE_TIMEOUT);
            idleTimeout = IDLE_TIMEOUT;
        } else if (idleTimeout != IDLE_TIMEOUT && idleTimeout != 0 && minIdle == maxPoolSize) {
            LOGGER.warn("{} - idleTimeout has been set but has no effect because the pool is operating as a fixed size pool.", poolName);
        }
    }

    private String generatePoolName() {
        final String prefix = "HikariPool-";
        try {
            // Pool number is global to the VM to avoid overlapping pool numbers in classloader scoped environments
            synchronized (System.getProperties()) {
                final String next = String.valueOf(Integer.getInteger("com.zaxxer.hikari.pool_number", 0) + 1);
                System.setProperty("com.zaxxer.hikari.pool_number", next);
                return prefix + next;
            }
        } catch (AccessControlException e) {
            // The SecurityManager didn't allow us to read/write system properties
            // so just generate a random pool number instead
            final ThreadLocalRandom random = ThreadLocalRandom.current();
            final StringBuilder buf = new StringBuilder(prefix);

            for (int i = 0; i < 4; i++) {
                buf.append(ID_CHARACTERS[random.nextInt(62)]);
            }

            LOGGER.info("assigned random pool name '{}' (security manager prevented access to system properties)", buf);

            return buf.toString();
        }
    }

}
