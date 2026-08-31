package org.springframework.beans.factory.support;

import org.springframework.beans.factory.config.BeanDefinition;

public interface InstantiationStrategy {
    public Object instantiate(BeanDefinition beanDefinition);
}
