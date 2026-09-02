package org.springframework.beans.factory.config;

public interface BeanPostProcessor {

    public Object postProcessBeforeInitialization(Object bean, String beanName)throws Exception;

    public Object postProcessAfterInitialization(Object bean, String beanName)throws Exception;
}
