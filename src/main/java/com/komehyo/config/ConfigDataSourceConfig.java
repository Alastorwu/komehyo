package com.komehyo.config;

//表示这个类为一个配置类

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
// 扫描 Mapper 接口并容器管理
@MapperScan(basePackages = ConfigDataSourceConfig.PACKAGE, sqlSessionFactoryRef = "configSqlSessionFactory")
public class ConfigDataSourceConfig {

    // 精确到 app 目录，以便跟其他数据源隔离
    static final String PACKAGE = "com.komehyo.dao.mapper.config";
    static final String MAPPER_LOCATION = "classpath:config/*.xml";

    @Value("${spring.datasource.config.url}")
    private String url;

    @Value("${spring.datasource.config.username}")
    private String user;

    @Value("${spring.datasource.config.password}")
    private String password;

    @Value("${spring.datasource.config.driver-class-name}")
    private String driverClass;

    @Bean(name = "configDataSource")
    public DataSource configDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(driverClass);
        dataSource.setUrl(url);
        dataSource.setUsername(user);
        dataSource.setPassword(password);
        return dataSource;
    }

    @Bean(name = "configTransactionManager")
    public DataSourceTransactionManager configTransactionManager() {
        return new DataSourceTransactionManager(configDataSource());
    }

    @Bean(name = "configSqlSessionFactory")
    public SqlSessionFactory configSqlSessionFactory(@Qualifier("configDataSource") DataSource configDataSource)
            throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(configDataSource);
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources(ConfigDataSourceConfig.MAPPER_LOCATION));
        return sessionFactory.getObject();
    }
}
