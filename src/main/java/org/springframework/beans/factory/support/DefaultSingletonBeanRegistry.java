package org.springframework.beans.factory.support;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.SingletonBeanRegistry;

import java.util.HashMap;
import java.util.Map;
/*
* @date:2026/9/8
* */
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {
    private final Map<String, Object> singletonObjects = new HashMap<>();
    private final Map<String, Object> earlySingletonObjects = new HashMap<>();
    private final Map<String, ObjectFactory<?>> singletonFactories = new HashMap<>();
    @Override
    public Object getSingleton(String beanName) {
        Object bean = singletonObjects.get(beanName);
        if (bean==null)
        {
            bean = earlySingletonObjects.get(beanName);
            if (bean==null)
            {
                ObjectFactory<?> objectFactory = singletonFactories.get(beanName);
                if(objectFactory!=null){
                    bean = objectFactory.getObject();
                    earlySingletonObjects.put(beanName, bean);
                }
            }
        }
        return bean;
    }

    @Override
    public void registerSingleton(String beanName, Object singletonObject) {
        if(!singletonObjects.containsKey(beanName))
            singletonObjects.put(beanName, singletonObject);
        earlySingletonObjects.remove(beanName);
        singletonFactories.remove(beanName);
    }

    public void registerSingletonFactory(String beanName, ObjectFactory<?> singletonFactory) {
        if(!singletonFactories.containsKey(beanName)&&!singletonObjects.containsKey(beanName)&&!earlySingletonObjects.containsKey(beanName))
            singletonFactories.put(beanName, singletonFactory);

    }

    @Override
    public boolean containsSingleton(String beanName) {
        return getSingleton(beanName) != null;
    }


}
