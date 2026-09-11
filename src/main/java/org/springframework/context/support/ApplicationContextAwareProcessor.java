package org.springframework.context.support;

import org.springframework.beans.BeanException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
/*
 * @description: 应用程序上下文感知处理器，用于在Bean初始化前后处理ApplicationContextAware接口的实现类
 * @date: 2026/9/11
 * */
public class ApplicationContextAwareProcessor implements BeanPostProcessor {
    private ApplicationContext applicationContext;
    ApplicationContextAwareProcessor(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeanException {
        if (bean instanceof ApplicationContextAware) {
            ((ApplicationContextAware) bean).setApplicationContext(applicationContext);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeanException {
        return bean;
    }
}
