package com.lcl.quartz.config;

import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.scheduling.quartz.AdaptableJobFactory;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;
import org.springframework.stereotype.Component;

/**
 * @ClassName: QuartzJobFactory
 * @Description:
 * @Author: Chunliang.Li
 * @Date: 2020/7/1 13:51
 * 本人学识渊博、经验丰富，代码风骚、效率恐怖、无所不精、无所不通
 **/
@Component
public class QuartzJobFactory extends SpringBeanJobFactory {
    /**
     * AutowireCapableBeanFactory 可以将一个对象添加到SpringIOC容器中，并且完成该对象注入
     */
    @Autowired
    private AutowireCapableBeanFactory autowireCapableBeanFactory;

    /**
     * 将实例化的任务对象手动添加到Spring容器中，完成对象的注入，否则程序会报空指针异常
     */
    @Override
    protected Object createJobInstance(TriggerFiredBundle bundle) throws Exception {
        Object jobInstance = super.createJobInstance(bundle);
        autowireCapableBeanFactory.autowireBean(jobInstance);
        return jobInstance;
    }
}
