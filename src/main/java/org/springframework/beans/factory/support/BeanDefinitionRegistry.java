package org.springframework.beans.factory.support;

import org.springframework.beans.factory.config.BeanDefinition;

/*
* @Date 2026/8/31
* */
public interface BeanDefinitionRegistry {
    void registerBeanDefinition(String beanName, BeanDefinition beanDefinition);

    BeanDefinition getBeanDefinition(String beanName) throws Exception;


    boolean containsBeanDefinition(String beanName);


    String[] getBeanDefinitionNames();


}
