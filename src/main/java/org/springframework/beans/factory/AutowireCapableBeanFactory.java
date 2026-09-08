package org.springframework.beans.factory;

import org.springframework.beans.BeanException;
import org.springframework.beans.factory.config.BeanDefinition;

/*
 * @description: 自动装配的bean工厂接口
 * @date: 2026/9/3
 */
public interface AutowireCapableBeanFactory extends BeanFactory {
   Object createBean(String beanName, BeanDefinition beanDefinition) throws BeanException;
}
