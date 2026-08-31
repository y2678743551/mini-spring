package org.springframework.beans.factory.support;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractBeanFactory  implements BeanFactory{

    private final Map<String, Object> singletonObjects = new HashMap<>();
    private final InstantiationStrategy instantiationStrategy = new SimpleInstantiationStrategy();//TODO 临时实现，后续需要修改策略模式
    @Override
    public Object getBean(String name) throws Exception {
        if(singletonObjects.containsKey(name))
            return singletonObjects.get(name);
        BeanDefinition beanDefinition = getBeanDefinition(name);
        if(beanDefinition == null)
            throw new Exception("BeanDefinition not found");

        if (beanDefinition.isSingleton()){
            Object instantiate = instantiationStrategy.instantiate(beanDefinition);

            singletonObjects.put(name, instantiate);
            return instantiate;
        }else
        {
            return instantiationStrategy.instantiate(beanDefinition);
        }
    }

    protected abstract BeanDefinition getBeanDefinition(String name) throws Exception;

    @Override
    public <T> T getBean(String name, Class<T> requiredType) throws Exception {
        return (T) getBean(name);
    }


}
