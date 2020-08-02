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
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
// 扫描 Mapper 接口并容器管理
@MapperScan(basePackages = AppDataSourceConfig.PACKAGE, sqlSessionFactoryRef = "appSqlSessionFactory")
public class AppDataSourceConfig {

    // 精确到 app 目录，以便跟其他数据源隔离
    static final String PACKAGE = "com.komehyo.dao.mapper.app";
    static final String MAPPER_LOCATION = "classpath:app/*.xml";

    @Value("${spring.datasource.app.url}")
    private String url;

    @Value("${spring.datasource.app.username}")
    private String user;

    @Value("${spring.datasource.app.password}")
    private String password;

    @Value("${spring.datasource.app.driver-class-name}")
    private String driverClass;

    @Bean(name = "appDataSource")
    @Primary
    public DataSource appDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(driverClass);
        dataSource.setUrl(url);
        dataSource.setUsername(user);
        dataSource.setPassword(password);
        return dataSource;
    }

    @Bean(name = "appTransactionManager")
    @Primary
    public DataSourceTransactionManager appTransactionManager() {
        return new DataSourceTransactionManager(appDataSource());
    }

    @Bean(name = "appSqlSessionFactory")
    @Primary
    public SqlSessionFactory appSqlSessionFactory(@Qualifier("appDataSource") DataSource appDataSource)
            throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(appDataSource);
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources(AppDataSourceConfig.MAPPER_LOCATION));
        return sessionFactory.getObject();
    }
}
