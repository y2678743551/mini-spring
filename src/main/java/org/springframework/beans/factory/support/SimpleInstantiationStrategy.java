package org.springframework.beans.factory.support;

import org.springframework.beans.BeanException;
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
        } catch (InstantiationException | InvocationTargetException | IllegalAccessException e) {
            throw new BeanException("Bean实例化失败",e);
        }catch (NoSuchMethodException e) {
            throw new BeanException("Bean无无参构造函数",e);
        }
    }
}
