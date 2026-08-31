package org.springframework.beans.factory.support;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;

import java.util.HashMap;
import java.util.Map;

public class DefaultListableBeanFactory extends AbstractBeanFactory implements BeanDefinitionRegistry {
    private final Map<String, BeanDefinition> beanDefinitionMap = new HashMap<>();


    @Override
    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {

        beanDefinitionMap.put(beanName, beanDefinition);

    }
    @Override
    public boolean containsBean(String name) {
        //TODO 无实例化bean，暂时判断beanDefinition是否存在

        return beanDefinitionMap.containsKey(name);
    }

    @Override
    public BeanDefinition getBeanDefinition(String beanName) {
        return beanDefinitionMap.get(beanName);
    }

    @Override
    public boolean containsBeanDefinition(String beanName) {
        return beanDefinitionMap.containsKey(beanName);
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return beanDefinitionMap.keySet().toArray(new String[0]);
    }
}
