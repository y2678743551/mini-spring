package org.springframework.beans.factory.support;

import org.springframework.beans.factory.config.BeanDefinition;

import java.lang.reflect.InvocationTargetException;
/*
* @description: 简单的实例化策略 用于占位
* @date: 2026/8/31
* */
public class SimpleInstantiationStrategy implements InstantiationStrategy {
    @Override
    public Object instantiate(BeanDefinition beanDefinition) {
        try {
            return beanDefinition.getBeanClass().getDeclaredConstructor().newInstance();
        } catch (InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
