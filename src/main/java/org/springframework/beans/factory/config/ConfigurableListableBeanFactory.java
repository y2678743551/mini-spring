package org.springframework.beans.factory.config;

import org.springframework.beans.BeanException;
import org.springframework.beans.factory.AutowireCapableBeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
/*
 * @description: 可配置的ListableBean工厂接口
 * @date: 2026/9/9
 */
public abstract interface ConfigurableListableBeanFactory extends ListableBeanFactory, ConfigurableBeanFactory, AutowireCapableBeanFactory {
    BeanDefinition getBeanDefinition(String beanName) throws BeanException;
    String[] getBeanDefinitionNames();
    void preInstantiateSingletons() throws BeanException;
}
