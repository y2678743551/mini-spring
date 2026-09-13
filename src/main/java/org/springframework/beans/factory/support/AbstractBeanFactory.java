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
     * @param beanName bean名称
     * @param requiredType 获取的bean类型
     * @param <T> bean类型
     * @return bean实例
     * @throws BeanException
     */
    @SuppressWarnings("unchecked")
    @Override
    public <T> T getBean(String beanName, Class<T> requiredType) throws BeanException {
        return (T) getBean(beanName);
    }

    /**
     * 根据beanName获取bean
     * @param beanName bean名称
     * @return bean实例
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
            throw new BeanException("未找到BeanDefinition "+beanName);

        Object bean = createBean(beanName   , beanDefinition);
        registerSingleton(beanName, bean);
        return bean;
    }

    /**
     * 添加beanPostProcessor
     * @param beanPostProcessor beanPostProcessor实例
     */
    @Override
    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor){
        beanPostProcessors.remove(beanPostProcessor);
        beanPostProcessors.add(beanPostProcessor);
    }
    /**
     * 获取beanPostProcessors
     * @return beanPostProcessors
     */
    public List<BeanPostProcessor> getBeanPostProcessors() {
        return beanPostProcessors;
    }
    /**
     * 判断是否包含bean
     * @param beanName bean名称
     * @return 是否包含bean
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
