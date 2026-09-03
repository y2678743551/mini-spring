package org.springframework.beans.factory.config;

import org.springframework.beans.BeanException;

public interface BeanPostProcessor {

    public Object postProcessBeforeInitialization(Object bean, String beanName)throws BeanException;

    public Object postProcessAfterInitialization(Object bean, String beanName)throws BeanException;
}
