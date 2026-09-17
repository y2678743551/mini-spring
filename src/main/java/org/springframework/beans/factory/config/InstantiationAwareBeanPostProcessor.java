package org.springframework.beans.factory.config;

import org.springframework.beans.BeanException;
/*
* @description: 
* @date: 2026/9/8
* */
public interface InstantiationAwareBeanPostProcessor extends BeanPostProcessor {

    // 在Bean实例化之前执行
    public Object postProcessBeforeInstantiation(Object bean, String beanName) throws BeanException ;
    // 在Bean实例化之后执行

    public Object postProcessAfterInstantiation(Object bean, String beanName) throws BeanException ;
    // 获取早期对象
    default public Object getEarlyBeanReference(Object bean, String beanName) throws BeanException {
        return bean;
    }
}
