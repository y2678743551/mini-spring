package org.springframework.beans.factory.config;

import org.springframework.beans.BeanException;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.PropertyValues;

/*
* @description: 
* @date: 2026/9/8
* */
public interface InstantiationAwareBeanPostProcessor extends BeanPostProcessor {

    // 在Bean实例化之前执行
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeanException ;

    // 在Bean实例化之后，在属性赋值之前执行

    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeanException ;
    // 获取早期对象
    default public Object getEarlyBeanReference(Object bean, String beanName) throws BeanException {
        return bean;
    }
    // 在Bean属性赋值之后执行
    public PropertyValues postProcessAfterProperties(PropertyValues pvs, Object bean, String beanName) throws BeanException ;
}
