package com.lcl.quartz.config;

import com.zaxxer.hikari.HikariDataSource;
import org.quartz.ee.servlet.QuartzInitializerListener;
import org.quartz.spi.JobFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.concurrent.ScheduledExecutorFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

/**
 * @ClassName: QuartzConfig
 * @Description:
 * @Author: Chunliang.Li
 * @Date: 2020/7/1 13:49
 * 本人学识渊博、经验丰富，代码风骚、效率恐怖、无所不精、无所不通
 **/
@Configuration
public class QuartzConfig {

    private static final String QUARTZ_PROPERTIES_PATH = "/quartz.yml";
    /**
     * 加载job工厂对象
     */
    @Bean
    public JobFactory jobFactory(ApplicationContext applicationContext) {
        QuartzJobFactory quartzJobFactory = new QuartzJobFactory();
        quartzJobFactory.setApplicationContext(applicationContext);
        return quartzJobFactory;
    }

    @Bean("scheduler")
    public SchedulerFactoryBean schedulerFactoryBean(JobFactory jobFactory,
                                                     @Qualifier("dataSource") DataSource dataSource,
                                                     PlatformTransactionManager platformTransactionManager) throws IOException {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        //可选,QuartzScheduler启动时更新己存在的Job,这样就不用每次修改targetObject后删除qrtz_job_details表对应记录
        factory.setOverwriteExistingJobs(true);
        factory.setAutoStartup(true);
        factory.setJobFactory(jobFactory);
        factory.setQuartzProperties(quartzProperties());
        factory.setDataSource(dataSource);
        factory.setTransactionManager(platformTransactionManager);
        return factory;

    }

    @Bean
    public Properties quartzProperties() throws IOException {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.setLocation(new ClassPathResource(QUARTZ_PROPERTIES_PATH));
        propertiesFactoryBean.afterPropertiesSet();
        return propertiesFactoryBean.getObject();
    }

    @Bean
    public QuartzInitializerListener quartzInitializerListener() {
        return new QuartzInitializerListener();
    }


}

