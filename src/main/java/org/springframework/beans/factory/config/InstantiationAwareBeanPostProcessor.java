package org.springframework.beans.factory.config;

import org.springframework.beans.BeanException;
/*
* @description: 实例化感知的Bean后置处理器
* @date: 2026/9/8
* */
public interface InstantiationAwareBeanPostProcessor extends BeanPostProcessor {

    // 在Bean实例化之后，初始化方法调用之前执行
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeanException ;
    // 在Bean实例化之后，初始化方法调用之后执行
//
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeanException ;
    // 获取早期对象
    default public Object getEarlyBeanReference(Object bean, String beanName) throws BeanException {
        return bean;
    }
}
