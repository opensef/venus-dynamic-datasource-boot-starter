package com.opensef.dynamicdatasource;

import com.zaxxer.hikari.HikariConfig;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DatabaseDriver;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * 动态数据源属性配置
 */
@ConfigurationProperties(prefix = "spring.datasource")
public class DynamicDataSourceProperties extends DataSourceProperties{

    private String primary;

    private Map<String, CustomDataSourceProperties> dynamic;


    public String getPrimary() {
        return primary;
    }

    public void setPrimary(String primary) {
        this.primary = primary;
    }

    public Map<String, CustomDataSourceProperties> getDynamic() {
        return dynamic;
    }

    public void setDynamic(Map<String, CustomDataSourceProperties> dynamic) {
        this.dynamic = dynamic;
    }

}
