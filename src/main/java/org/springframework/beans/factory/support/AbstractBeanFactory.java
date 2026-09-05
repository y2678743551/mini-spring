package org.springframework.beans.factory.support;

import cn.hutool.core.util.TypeUtil;
import org.springframework.beans.*;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.BeanReference;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.core.convert.support.DefaultConversionService;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractBeanFactory  implements ListableBeanFactory, AutowireCapableBeanFactory, ConfigurableBeanFactory {
    private final List<BeanPostProcessor> beanPostProcessors = new ArrayList<>();
    private final Map<String, Object> singletonMap = new HashMap<>();
    private final InstantiationStrategy instantiationStrategy = new SimpleInstantiationStrategy();//TODO 临时实现，后续需要修改策略模式
    private final DefaultConversionService conversionService = new DefaultConversionService();
    @Override
    public Object getBean(String name) throws BeanException {
        if(containsSingleton(name))
            return getSingleton(name);


        BeanDefinition beanDefinition = getBeanDefinition(name);
        if(beanDefinition == null)
            throw new BeanException("BeanDefinition not found");

        Object bean = createBean(name, beanDefinition);
        return bean;
    }


    @Override
    public Object createBean(String beanName, BeanDefinition beanDefinition){
        return doCreateBean(beanName, beanDefinition);
    }
    protected Object doCreateBean(String name,  BeanDefinition beanDefinition) {
        //实例化bean
        Object bean = instantiationStrategy.instantiate(beanDefinition);
        //设置属性


        applyPropertyValues(name, bean, beanDefinition);


        beanPostProcessors.forEach(beanPostProcessor -> {
            try {
                beanPostProcessor.postProcessBeforeInitialization(bean, name);
            } catch (BeanException e) {
                throw new BeanException(e.getMessage(),e);
            }
        });

        beanPostProcessors.forEach(beanPostProcessor -> {
            try {
                beanPostProcessor.postProcessAfterInitialization(bean, name);
            } catch (BeanException e) {
                throw new BeanException(e.getMessage(),e);
            }
        });
        //注册singleton bean
        if (beanDefinition.isSingleton()){
            registerSingleton(name, bean);
        }
        return bean;
    }
    protected void applyPropertyValues(String beanName,Object bean,BeanDefinition beanDefinition){

        PropertyValues propertyValues = beanDefinition.getPropertyValues();
        if(propertyValues.isEmpty())
            return;
        for (PropertyValue propertyValue:propertyValues.getPropertyValueList()){
            String name = propertyValue.getName();
            Object value = propertyValue.getValue();
            if(value instanceof BeanReference){
                BeanReference beanReference= (BeanReference) value;
                getBean(beanReference.getBeanName());
            }
            Class<?> targetType = (Class<?>) TypeUtil.getFieldType(beanDefinition.getBeanClass(), name);

            value = conversionService.convert(value, targetType);
            try {
                String setterName = "set" + name.substring(0, 1).toUpperCase() + name.substring(1);
                bean.getClass().getMethod(setterName, value.getClass()).invoke(bean, value);
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                throw new BeanException("不存在" +name + "对应的set方法");
            }
        }
    }
    protected abstract BeanDefinition getBeanDefinition(String name) throws BeanException;
    protected abstract boolean containsBeanDefinition(String name);
    @Override
    public boolean containsBean(String name) {
        if (singletonMap.containsKey(name))
            return true;
        return containsBeanDefinition(name);
    }



    @SuppressWarnings("unchecked")
    @Override
    public <T> T getBean(String name, Class<T> requiredType) throws BeanException {
        return (T) getBean(name);
    }


    @Override
    public Object getSingleton(String beanName) {
        if (singletonMap.containsKey(beanName))
            return singletonMap.get(beanName);
        throw new BeanException("No bean named " + beanName + " is defined");
    }

    @Override
    public void registerSingleton(String beanName, Object singletonObject) {
        singletonMap.put(beanName, singletonObject);
    }

    @Override
    public boolean containsSingleton(String beanName) {
        return singletonMap.containsKey(beanName);
    }

    @Override
    public String[] getSingletonNames() {
        return singletonMap.keySet().toArray(new String[0]);
    }
}
