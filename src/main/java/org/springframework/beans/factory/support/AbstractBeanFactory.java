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

    /**
     * 根据beanName和requiredType获取bean
     * @param beanName
     * @param requiredType
     * @param <T>
     * @return
     * @throws BeanException
     */
    @SuppressWarnings("unchecked")
    @Override
    public <T> T getBean(String beanName, Class<T> requiredType) throws BeanException {
        return (T) getBean(beanName);
    }

    /**
     * 根据beanName获取bean
     * @param beanName
     * @return
     * @throws BeanException
     */
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

    /**
     * 添加beanPostProcessor
     * @param beanPostProcessor
     */
    @Override
    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor){
        beanPostProcessors.remove(beanPostProcessor);
        beanPostProcessors.add(beanPostProcessor);
    }
    /**
     * 获取beanPostProcessors
     * @return
     */
    public List<BeanPostProcessor> getBeanPostProcessors() {
        return beanPostProcessors;
    }
    /**
     * 判断是否包含bean
     * @param beanName
     * @return
     */
    @Override
    public boolean containsBean(String beanName) {
        if (containsSingleton(beanName))
            return true;
        return containsBeanDefinition(beanName);
    }
    protected abstract BeanDefinition getBeanDefinition(String beanName) throws BeanException;
    protected abstract boolean containsBeanDefinition(String beanName);
    public abstract Object createBean(String beanName, BeanDefinition beanDefinition);


}
