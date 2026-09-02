package org.springframework.beans.factory.support;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanWrapper;
import org.springframework.beans.factory.BeanWrapperImpl;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractBeanFactory  implements BeanFactory{
    private final List<BeanPostProcessor> beanPostProcessors = new ArrayList<>();
    private final Map<String, Object> singletonMap = new HashMap<>();
    private final InstantiationStrategy instantiationStrategy = new SimpleInstantiationStrategy();//TODO 临时实现，后续需要修改策略模式
    @Override
    public Object getBean(String name) throws Exception {
        if(singletonMap.containsKey(name))
            return singletonMap.get(name);
        BeanDefinition beanDefinition = getBeanDefinition(name);
        if(beanDefinition == null)
            throw new Exception("BeanDefinition not found");
        //实例化bean
        Object bean = instantiationStrategy.instantiate(beanDefinition);
        //设置属性
        BeanWrapper beanWrapper = new BeanWrapperImpl(bean);
        beanWrapper.setPropertyValues(beanDefinition.getPropertyValues());

        //执行bean的初始化方法
        createBean(name, bean, beanDefinition);

        //注册singleton bean
        if (beanDefinition.isSingleton()){
            singletonMap.put(name, bean);
        }

        return bean;
    }

    private void createBean(String name, Object bean, BeanDefinition beanDefinition) {
        beanPostProcessors.forEach(beanPostProcessor -> {
            try {
                beanPostProcessor.postProcessBeforeInitialization(bean, name);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        beanPostProcessors.forEach(beanPostProcessor -> {
            try {
                beanPostProcessor.postProcessAfterInitialization(bean, name);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    protected abstract BeanDefinition getBeanDefinition(String name) throws Exception;
    protected abstract boolean containsBeanDefinition(String name);
    @Override
    public boolean containsBean(String name) {
        if (singletonMap.containsKey(name))
            return true;
        return containsBeanDefinition(name);
    }



    @Override
    public <T> T getBean(String name, Class<T> requiredType) throws Exception {
        return (T) getBean(name);
    }


}
