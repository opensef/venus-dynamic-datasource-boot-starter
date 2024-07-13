package com.opensef.dynamicdatasource;

import com.opensef.dynamicdatasource.config.CustomDruidConfig;
import com.opensef.dynamicdatasource.config.CustomHikariConfig;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.jdbc.DatabaseDriver;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

public class CustomDataSourceProperties extends DataSourceProperties {

    private CustomHikariConfig hikari;

    private CustomDruidConfig druid;

    public CustomHikariConfig getHikari() {
        return hikari;
    }

    public void setHikari(CustomHikariConfig hikari) {
        this.hikari = hikari;
    }

    public CustomDruidConfig getDruid() {
        return druid;
    }

    public void setDruid(CustomDruidConfig druid) {
        this.druid = druid;
    }

    /**
     * 嵌入式数据库连接
     */
    private EmbeddedDatabaseConnection embeddedDatabaseConnection;

    /**
     * 初始化
     */
    public CustomDataSourceProperties() {
        this.embeddedDatabaseConnection = EmbeddedDatabaseConnection.NONE;
    }

    /**
     * 重写
     */
    public void afterPropertiesSet() {
        this.embeddedDatabaseConnection = EmbeddedDatabaseConnection.get(super.getClassLoader());
    }

    /**
     * 重写determineDriverClassName方法，不再抛出异常
     *
     * @return DriverClassName名称
     */
    public String determineDriverClassName() {
        if (StringUtils.hasText(this.getDriverClassName())) {
            Assert.state(this.driverClassIsLoadable(), () -> "Cannot load driver class: " + this.getDriverClassName());
            return this.getDriverClassName();
        } else {
            String driverClassName = null;
            if (StringUtils.hasText(this.getUrl())) {
                driverClassName = DatabaseDriver.fromJdbcUrl(this.getUrl()).getDriverClassName();
            }

            if (!StringUtils.hasText(driverClassName)) {
                driverClassName = this.embeddedDatabaseConnection.getDriverClassName();
            }

            if (!StringUtils.hasText(driverClassName)) {
                return "";
            } else {
                return driverClassName;
            }
        }
    }

    public String determineUrl() {
        if (StringUtils.hasText(this.getUrl())) {
            return this.getUrl();
        } else {
            String databaseName = this.determineDatabaseName();
            String url = databaseName != null ? this.embeddedDatabaseConnection.getUrl(databaseName) : null;
            if (!StringUtils.hasText(url)) {
                return "";
            } else {
                return url;
            }
        }
    }

    private boolean driverClassIsLoadable() {
        try {
            ClassUtils.forName(this.getDriverClassName(), null);
            return true;
        } catch (UnsupportedClassVersionError var2) {
            throw var2;
        } catch (Throwable var3) {
            return false;
        }
    }

}
