package com.opensef.dynamicdatasource;

import com.alibaba.druid.pool.DruidDataSource;
import com.opensef.dynamicdatasource.util.DruidUtil;
import com.opensef.dynamicdatasource.util.HikariUtil;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 动态数据源设置
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final DynamicDataSourceProperties dynamicDataSourceProperties;

    // 默认数据源名称，当引入了动态数据源包但没有指定动态数据源中的主数据源时，使用默认值
    public final String DEFAULT_DATA_SOURCE_NAME = "default";

    /**
     * 当前数据源key名称
     */
    private String currentDatabaseKey = null;

    public DynamicDataSource(DynamicDataSourceProperties dynamicDataSourceProperties) {
        this.dynamicDataSourceProperties = dynamicDataSourceProperties;
    }

    @Autowired
    private ApplicationContext applicationContext;

    /**
     * druid数据源是在项目中存在
     */
    private static boolean DRUID_EXISTS;

    /**
     * hikari数据源是否在项目中存在
     */
    private static boolean HIKARI_EXISTS;

    static {
        try {
            Class.forName("com.alibaba.druid.pool.DruidDataSource");
            DRUID_EXISTS = true;
        } catch (ClassNotFoundException var2) {
            DRUID_EXISTS = false;
        }

        try {
            Class.forName("com.zaxxer.hikari.HikariDataSource");
            HIKARI_EXISTS = true;
        } catch (ClassNotFoundException var1) {
            HIKARI_EXISTS = false;
        }

    }

    @Override
    public void afterPropertiesSet() {
        AtomicReference<DataSource> defaultTargetDataSource = new AtomicReference<>();
        Map<Object, Object> dataSourceMap = new HashMap<>();

        if (dynamicDataSourceProperties.getDynamic() == null || dynamicDataSourceProperties.getDynamic().size() == 0) {
            // 如果没有配置动态数据源，则使用默认数据源
            setDataSource(dynamicDataSourceProperties, dataSourceMap, defaultTargetDataSource, DEFAULT_DATA_SOURCE_NAME);
        } else {
            dynamicDataSourceProperties.getDynamic().forEach((dataSourceName, dataSourceProperties) -> {
                setDataSource(dataSourceProperties, dataSourceMap, defaultTargetDataSource, dataSourceName);
            });
        }

        setDefaultTargetDataSource(defaultTargetDataSource.get());

        setTargetDataSources(dataSourceMap);

        super.afterPropertiesSet();
    }

    private void setDataSource(DataSourceProperties dataSourceProperties,
                               Map<Object, Object> dataSourceMap,
                               AtomicReference<DataSource> defaultTargetDataSource,
                               String dataSourceName) {
        DataSource dataSource = selectDataSource(dataSourceProperties);
        // 验证连接数据库是否成功，如果失败，则退出启动程序
        try {
            dataSource.getConnection().close();
        } catch (Throwable throwable) {
            logger.error("初始化数据库连接失败：url：{}，username：{}，password：{}",
                    dataSourceProperties.getUrl(), dataSourceProperties.getUsername(), dataSourceProperties.getPassword());
            throwable.printStackTrace();
            SpringApplication.exit(applicationContext);
        }

        dataSourceMap.put(dataSourceName, dataSource);
        // 设置默认数据源
        if (null != dynamicDataSourceProperties.getDynamic() && dynamicDataSourceProperties.getDynamic().size() > 1) {
            if (StringUtils.isBlank(dynamicDataSourceProperties.getPrimary())) {
                throw new DynamicDataSourceException("请在多个数据源中指定一个主数据源");
            }

            if (dynamicDataSourceProperties.getPrimary().equals(dataSourceName)) {
                defaultTargetDataSource.set(dataSource);
            }
        } else {
            defaultTargetDataSource.set(dataSource);
        }
    }

    /**
     * 选择使用哪种数据源
     *
     * @param dataSourceProperties DataSourceProperties
     * @return DataSource
     */
    public DataSource selectDataSource(DataSourceProperties dataSourceProperties) {
        // 初始化druid数据源（代码必须在方法里写，单独提出去会报错）
        boolean isHasDruidConfig = applicationContext.containsBeanDefinition("druidConfig");
        DruidDataSource druidDataSource = null;
        if (isHasDruidConfig) {
            if (dataSourceProperties instanceof CustomDataSourceProperties) {
                CustomDataSourceProperties customDataSourceProperties = (CustomDataSourceProperties) dataSourceProperties;
                if (null != customDataSourceProperties.getDruid()) {
                    druidDataSource = DruidUtil.convertFromCustomDruidConfig(customDataSourceProperties.getDruid());
                } else {
                    druidDataSource = DruidUtil.copy(applicationContext.getBean(DruidDataSource.class));
                }
            } else {
                druidDataSource = DruidUtil.copy(applicationContext.getBean(DruidDataSource.class));
            }
        }

        // 初始化Hikari数据源（代码必须在方法里写，单独提出去会报错）
        boolean isHikariConfig = applicationContext.containsBeanDefinition("hikariConfig");
        HikariConfig hikariConfig = null;
        if (isHikariConfig) {
            if (dataSourceProperties instanceof CustomDataSourceProperties) {
                CustomDataSourceProperties customDataSourceProperties = (CustomDataSourceProperties) dataSourceProperties;
                if (customDataSourceProperties.getHikari() != null) {
                    hikariConfig = HikariUtil.convertFromCustomHikariConfig(customDataSourceProperties.getHikari());
                } else {
                    hikariConfig = HikariUtil.copy(applicationContext.getBean(HikariConfig.class));
                }
            } else {
                hikariConfig = HikariUtil.copy(applicationContext.getBean(HikariConfig.class));
            }
        }


        if (dataSourceProperties.getType() == null) {
            // 如果没有指定类型，则优先使用Hikari数据源
            if (HIKARI_EXISTS && isHikariConfig) {
                return DataSourceCreator.createHikariDataSource(hikariConfig, dataSourceProperties);
            } else if (DRUID_EXISTS && isHasDruidConfig) {
                return DataSourceCreator.createDruidDataSource(druidDataSource.cloneDruidDataSource(), dataSourceProperties);
            }
        } else if (dataSourceProperties.getType().equals(HikariDataSource.class)) {
            return DataSourceCreator.createHikariDataSource(hikariConfig, dataSourceProperties);
        } else if (dataSourceProperties.getType().equals(DruidDataSource.class)) {
            if (Objects.isNull(druidDataSource)) {
                return null;
            }
            return DataSourceCreator.createDruidDataSource(druidDataSource.cloneDruidDataSource(), dataSourceProperties);
        }

        return null;
    }

    /**
     * 决策当前查询使用哪个数据源
     *
     * @return 数据源的key
     */
    @Override
    protected Object determineCurrentLookupKey() {
        String key;
        if (dynamicDataSourceProperties.getDynamic() == null || dynamicDataSourceProperties.getDynamic().size() == 0) {
            key = DEFAULT_DATA_SOURCE_NAME;
        } else {
            key = DynamicDataSourceContextHolder.getDataSource();
            if (key == null) {
                // 设置默认数据源
                key = dynamicDataSourceProperties.getPrimary();
            }
        }
        this.currentDatabaseKey = key;
        logger.debug("当前连接数据源为：{}", this.currentDatabaseKey);
        return key;
    }

    /**
     * 获取当前数据源key名称
     *
     * @return 当前数据源key名称
     */
    public String getCurrentDatabaseKey() {
        if (StringUtils.isNotBlank(currentDatabaseKey)) {
            return currentDatabaseKey;
        } else {
            return (String) determineCurrentLookupKey();
        }
    }

    /**
     * 获取当前数据源
     *
     * @return 当前数据源
     */
    public DataSource getCurrentDataSource() {
        return determineTargetDataSource();
    }

}
