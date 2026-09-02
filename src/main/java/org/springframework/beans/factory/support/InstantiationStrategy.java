package org.springframework.beans.factory.support;

import org.springframework.beans.factory.config.BeanDefinition;
/*
 * @Date:2026/8/31
 * */
public interface InstantiationStrategy {
    public Object instantiate(BeanDefinition beanDefinition);
}
