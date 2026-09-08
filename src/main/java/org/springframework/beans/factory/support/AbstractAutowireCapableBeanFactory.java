package org.springframework.beans.factory.support;

import cn.hutool.core.util.TypeUtil;
import org.springframework.beans.BeanException;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.BeanReference;
import org.springframework.beans.factory.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.core.convert.ConverterException;
import org.springframework.core.convert.support.DefaultConversionService;

import java.lang.reflect.InvocationTargetException;

public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory implements AutowireCapableBeanFactory {
    private final InstantiationStrategy instantiationStrategy = new SimpleInstantiationStrategy();//TODO 临时实现，后续需要修改策略模式

    private final DefaultConversionService conversionService = new DefaultConversionService();
    @Override
    public Object createBean(String beanName, BeanDefinition beanDefinition){
        return doCreateBean(beanName, beanDefinition);
    }
    protected Object doCreateBean(String name,  BeanDefinition beanDefinition) {
        //实例化bean
        Object bean = instantiationStrategy.instantiate(beanDefinition);
        registerSingletonFactory(name, () -> getEarlyBeanReference(bean, name));
        //设置属性
        applyPropertyValues(name, bean, beanDefinition);

        initializeBean(bean, name, beanDefinition);

        //注册singleton bean
        if (beanDefinition.isSingleton()){
            registerSingleton(name, bean);
        }
        return bean;
    }

    private Object initializeBean(Object bean, String name, BeanDefinition beanDefinition) {

        Object wrappedBean =applyBeanPostProcessorsBeforeInitialization(bean, name);

        String initMethodName = beanDefinition.getInitMethodName();
        Class<?> beanClass = beanDefinition.getBeanClass();
        try {
            beanClass.getMethod(initMethodName).invoke(wrappedBean);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new BeanException("调用初始化方法失败",e);
        } catch (NoSuchMethodException e) {
            throw new BeanException("初始化方法不存在",e);
        }

        wrappedBean =applyBeanPostProcessorsAfterInitialization(wrappedBean, name);
        return wrappedBean;
    }
    private Object applyBeanPostProcessorsBeforeInitialization(Object bean, String beanName) throws BeanException {
        Object result = bean;
        for (BeanPostProcessor beanPostProcessor : getBeanPostProcessors())
        {
            Object object = beanPostProcessor.postProcessBeforeInitialization(bean, beanName);
            if (object != null) {
                result = object;
            }
        }
        return result;
    }
    private Object applyBeanPostProcessorsAfterInitialization(Object bean, String beanName) throws BeanException {
        Object result = bean;
        for (BeanPostProcessor beanPostProcessor : getBeanPostProcessors())
        {
            Object object = beanPostProcessor.postProcessAfterInitialization(bean, beanName);
            if (object != null) {
                result = object;
            }
        }
        return result;
    }
    public Object getEarlyBeanReference(Object bean, String beanName) throws BeanException {
        Object result = bean;
        for (BeanPostProcessor beanPostProcessor : getBeanPostProcessors())
        {
            if (beanPostProcessor instanceof InstantiationAwareBeanPostProcessor) {
                Object object = ((InstantiationAwareBeanPostProcessor) beanPostProcessor).getEarlyBeanReference(bean, beanName);
                if (object != null) {
                    result = object;
                }
            }
        }
        return result;
    }
    protected void applyPropertyValues(String beanName,Object bean,BeanDefinition beanDefinition){

        PropertyValues propertyValues = beanDefinition.getPropertyValues();
        if(propertyValues.isEmpty())
            return;
        for (PropertyValue propertyValue:propertyValues.getPropertyValueList()){
            String name = propertyValue.getName();
            Object value = propertyValue.getValue();
            if(value instanceof BeanReference beanReference){
                getBean(beanReference.getBeanName());
            }
            Class<?> targetType = (Class<?>) TypeUtil.getFieldType(beanDefinition.getBeanClass(), name);
            try {
                value = conversionService.convert(value, targetType);
            } catch (ConverterException e) {
                throw new BeanException(e.getMessage(), e);
            }

            try {
                String setterName = "set" + name.substring(0, 1).toUpperCase() + name.substring(1);

                bean.getClass().getMethod(setterName, value.getClass()).invoke(bean, value);

            } catch ( NoSuchMethodException e) {
                throw new BeanException("不存在" +name + "对应的set方法",e);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new BeanException("set方法调用失败",e);
            }
        }
    }

}
