package com.opensef.dynamicdatasource;

import com.alibaba.druid.pool.DruidDataSource;
import com.opensef.dynamicdatasource.aop.DataSourceInterceptor;
import com.opensef.dynamicdatasource.aop.DynamicDataSourceAnnotationAdvisor;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.aop.Advisor;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Role;

/**
 * 动态数据源配置，开启此配置，无论是否配置了多数据源，都会走自定义数据源配置方法，在DynamicDataSource里会自动切换
 */
@Configuration
@AutoConfigureOrder(-1)
@AutoConfigureBefore({DataSourceAutoConfiguration.class})
@EnableConfigurationProperties(DynamicDataSourceProperties.class)
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
public class DynamicDataSourceAutoConfiguration {

    @Primary
    @Bean
    public DynamicDataSourceProperties dynamicDataSourceProperties() {
        return new DynamicDataSourceProperties();
    }

    @Primary
    @Bean
    public DynamicDataSource dynamicDataSource(DynamicDataSourceProperties dynamicDataSourceProperties) {
        return new DynamicDataSource(dynamicDataSourceProperties);
    }

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public Advisor dynamicDataSourceAnnotationAdvisor() {
        DataSourceInterceptor interceptor = new DataSourceInterceptor();
        DynamicDataSourceAnnotationAdvisor advisor = new DynamicDataSourceAnnotationAdvisor(interceptor, DataSource.class);
        advisor.setOrder(-1);
        return advisor;
    }

    /**
     * 存在Hikari数据源时,创建Hikari数据源
     */
    @ConditionalOnClass(HikariDataSource.class)
    @Configuration
    static class HikariDataSourceCreatorConfiguration {

        @Bean("hikariConfig")
        @ConfigurationProperties(prefix = "spring.datasource.hikari")
        public HikariConfig hikariConfig() {
            return new HikariConfig();
        }

    }

    /**
     * 存在druid数据源时，创建Druid连接池配置
     */
    @ConditionalOnClass(DruidDataSource.class)
    @Configuration
    static class DruidDataSourceCreatorConfiguration {

        @Bean("druidConfig")
        @ConfigurationProperties("spring.datasource.druid")
        public DruidDataSource druidConfig() {
            return new DruidDataSource();
        }

    }

}
