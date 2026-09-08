package org.springframework.beans.factory.support;


import org.springframework.beans.*;


import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.config.SingletonBeanRegistry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements ConfigurableBeanFactory {
    private final List<BeanPostProcessor> beanPostProcessors = new ArrayList<>();

    @SuppressWarnings("unchecked")
    @Override
    public <T> T getBean(String beanName, Class<T> requiredType) throws BeanException {
        return (T) getBean(beanName);
    }
    @Override
    public Object getBean(String beanName) throws BeanException {
        Object singleton = getSingleton(beanName);
        if(singleton!=null){
            return singleton;
        }

        BeanDefinition beanDefinition = getBeanDefinition(beanName);
        if(beanDefinition == null)
            throw new BeanException("BeanDefinition "+beanName+" not found");

        Object bean = createBean(beanName   , beanDefinition);
        registerSingleton(beanName, bean);
        return bean;
    }



    protected abstract BeanDefinition getBeanDefinition(String beanName) throws BeanException;
    protected abstract boolean containsBeanDefinition(String beanName);
    @Override
    public boolean containsBean(String beanName) {
        if (containsSingleton(beanName))
            return true;
        return containsBeanDefinition(beanName);
    }

    public abstract Object createBean(String beanbeanName, BeanDefinition beanDefinition);

    public List<BeanPostProcessor> getBeanPostProcessors() {
        return beanPostProcessors;
    }
}
