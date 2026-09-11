package org.springframework.beans.factory.support;

import cn.hutool.core.util.TypeUtil;
import org.springframework.beans.BeanException;
import org.springframework.beans.BeanFactoryAware;
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

    /**
     * 创建bean实例
     * @param beanName
     * @param beanDefinition
     * @return
     */
    @Override
    public Object createBean(String beanName, BeanDefinition beanDefinition){
        return doCreateBean(beanName, beanDefinition);
    }

    /**
     * 执行创建bean实例
     * @param name
     * @param beanDefinition
     * @return
     */
    protected Object doCreateBean(String name,  BeanDefinition beanDefinition) {
        //实例化bean
        Object bean = instantiationStrategy.instantiate(beanDefinition);
        //注册不完整的singleton bean
        if (beanDefinition.isSingleton()){
            Object finalBean = bean;

            registerSingletonFactory(name, () -> getEarlyBeanReference(finalBean, name));

        }

        //设置属性
        applyPropertyValues(name, bean, beanDefinition);

        bean=initializeBean(bean, name, beanDefinition);

        //注册完整的singleton bean
        if (beanDefinition.isSingleton()){
            registerSingleton(name, bean);
        }
        return bean;
    }

    /**
     * 初始化bean
     * @param bean
     * @param name
     * @param beanDefinition
     * @return
     */
    private Object initializeBean(Object bean, String name, BeanDefinition beanDefinition) {
        if(bean instanceof BeanFactoryAware)
            ((BeanFactoryAware) bean).setBeanFactory(this);
        Object wrappedBean =bean;
        wrappedBean=applyBeanPostProcessorsBeforeInitialization(wrappedBean, name);

        String initMethodName = beanDefinition.getInitMethodName();
        Class<?> beanClass = beanDefinition.getBeanClass();

        if (initMethodName != null){
            try {
                beanClass.getMethod(initMethodName).invoke(wrappedBean);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new BeanException("调用初始化方法失败", e);
            } catch (NoSuchMethodException e) {
                throw new BeanException("初始化方法不存在", e);
            }
        }

        wrappedBean =applyBeanPostProcessorsAfterInitialization(wrappedBean, name);
        return wrappedBean;
    }
    @Override
    /**
     * 调用bean前置处理器
     * @param bean
     * @param beanName
     * @return
     */
    public Object applyBeanPostProcessorsBeforeInitialization(Object bean, String beanName) throws BeanException {
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
    @Override
    /**
     * 调用bean后置处理器
     * @param bean
     * @param beanName
     * @return
     */
    public Object applyBeanPostProcessorsAfterInitialization(Object bean, String beanName) throws BeanException {
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
    /**
     * 获取早期bean引用
     * @param bean
     * @param beanName
     * @return
     */
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

    /**
     * 设置bean属性值
     * @param beanName
     * @param bean
     * @param beanDefinition
     */
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

            if (targetType != null && !value.getClass().isAssignableFrom(targetType)){
                try {
                    value = conversionService.convert(value, targetType);
                } catch (ConverterException e) {
                    throw new BeanException(e.getMessage(), e);
                }
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
