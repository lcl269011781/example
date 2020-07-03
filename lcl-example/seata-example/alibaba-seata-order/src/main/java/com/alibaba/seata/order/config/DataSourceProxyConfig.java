package com.alibaba.seata.order.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.zaxxer.hikari.HikariDataSource;
import io.seata.rm.datasource.DataSourceProxy;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.transaction.SpringManagedTransactionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

/**
 * @ClassName: DatasourceConfig
 * @Description: 代理seata数据源
 * @Author: Chunliang.Li
 * @Date: 2020/4/6 16:06
 * 本人学识渊博、经验丰富，代码风骚、效率恐怖、无所不精、无所不通
 **/
@Configuration
public class DataSourceProxyConfig {

    /**
     * 数据源属性配置
     * {@link DataSourceProperties}
     */
    @Autowired
    private DataSourceProperties dataSourceProperties;
    /**
     * 配置数据源代理，用于事务回滚
     *
     * @return The default datasource    seata 1.0.0  貌似不需要了。只需要排除自动注入的数据源@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)//取消数据源的自动创建
     * @see DataSourceProxy
     */
//    @Primary
//    @Bean
//    public DataSource dataSource() {
//        HikariDataSource dataSource = new HikariDataSource();
//        dataSource.setJdbcUrl(dataSourceProperties.getUrl());
//        dataSource.setUsername(dataSourceProperties.getUsername());
//        dataSource.setPassword(dataSourceProperties.getPassword());
//        dataSource.setDriverClassName(dataSourceProperties.getDriverClassName());
//        return new DataSourceProxy(dataSource);
//    }
}
