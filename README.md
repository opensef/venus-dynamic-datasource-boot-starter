# 动态数据源starter
- 如果没有配置动态数据源，则使用默认数据源
- 通过spring.datasource.dynamic.ds1.type=com.alibaba.druid.pool.DruidDataSource指定数据源【ds1为自定义的数据源名称】
- 如果没有指定类型，则优先使用Hikari数据源