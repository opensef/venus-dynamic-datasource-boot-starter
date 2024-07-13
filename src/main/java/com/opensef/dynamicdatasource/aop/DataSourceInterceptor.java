package com.opensef.dynamicdatasource.aop;

import com.opensef.dynamicdatasource.DataSource;
import com.opensef.dynamicdatasource.DynamicDataSourceContextHolder;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class DataSourceInterceptor implements MethodInterceptor {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Method method = invocation.getMethod();

        // 先从方法获取注解
        DataSource dataSource = method.getAnnotation(DataSource.class);
        // 方法不存在注解则从当前类上获取
        if (dataSource == null) {
            dataSource = method.getDeclaringClass().getAnnotation(DataSource.class);
        }
        // 当前类不存在注解，则解析Mybatis Mapper代理对象
        if (dataSource == null) {
            Object current = invocation.getThis();
            if (current != null) {
                Class<?> proxyClass = null;
                try {
                    proxyClass = Class.forName("org.apache.ibatis.binding.MapperProxy");
                } catch (ClassNotFoundException e) {
                    // do something
                }
                if (proxyClass != null) {
                    Field mapperInterfaceField = proxyClass.getDeclaredField("mapperInterface");
                    mapperInterfaceField.setAccessible(true);
                    Class<?> mapperClass = (Class<?>) mapperInterfaceField.get(Proxy.getInvocationHandler(current));
                    dataSource = mapperClass.getAnnotation(DataSource.class);
                }

            }
        }

        if (dataSource == null) {
            throw new RuntimeException("Current dataSource is null");
        }

        logger.info("方法信息：{}, 动态数据源名称：{}", method, dataSource.value());
        // 设置到动态数据源上下文中
        DynamicDataSourceContextHolder.putDataSource(dataSource.value());

        Object object;
        try {
            object = invocation.proceed();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        } finally {
            // 方法执行完毕之后，销毁当前数据源信息
            DynamicDataSourceContextHolder.clearDataSource();
        }
        return object;
    }

}
