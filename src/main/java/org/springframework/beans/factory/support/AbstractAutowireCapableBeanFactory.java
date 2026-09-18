package org.springframework.beans.factory.support;

import cn.hutool.core.util.TypeUtil;
import org.springframework.beans.BeanException;
import org.springframework.beans.BeanFactoryAware;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.BeanReference;
import org.springframework.beans.factory.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.core.convert.ConverterException;
import org.springframework.core.convert.support.DefaultConversionService;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory implements AutowireCapableBeanFactory {
    private final InstantiationStrategy instantiationStrategy = new SimpleInstantiationStrategy();



    /**
     * 创建bean实例
     * @param beanName bean名称
     * @param beanDefinition bean定义
     * @return bean实例
     */
    @Override
    public Object createBean(String beanName, BeanDefinition beanDefinition){
        return doCreateBean(beanName, beanDefinition);
    }

    /**
     * 执行创建bean实例
     * @param name bean名称
     * @param beanDefinition bean定义
      * @return bean实例
     */
    protected Object doCreateBean(String name,  BeanDefinition beanDefinition) {

        Object bean = resolveBeforeInstantiation(beanDefinition.getBeanClass(), name);
        if (bean != null) {
            return bean;
        }
        //实例化bean
        bean = instantiationStrategy.instantiate(beanDefinition);
        //注册不完整的singleton bean
        if (beanDefinition.isSingleton()){
            Object finalBean = bean;

            registerSingletonFactory(name, () -> getEarlyBeanReference(finalBean, name));

        }

        //设置属性
        populateBean(name, bean, beanDefinition);


        bean=initializeBean(bean, name, beanDefinition);

        //注册完整的singleton bean
        if (beanDefinition.isSingleton()){
            registerSingleton(name, bean);
        }
        return bean;
    }
    /*
     * 在实例化bean之前调用
     * @param beanClass bean类
     * @param beanName bean名称
     * @return bean实例
     */
    protected Object resolveBeforeInstantiation(Class<?> beanClass, String beanName) {
        Object result = applyBeanPostProcessorsBeforeInstantiation(beanClass, beanName);

        if (result != null) {
            result=applyBeanPostProcessorsAfterInitialization(result, beanName);
        }

        return result;
    }

    /**
     * 填充bean属性
     * @param beanName bean名称
     * @param bean bean实例
     * @param beanDefinition bean定义
     */
    private void populateBean(String beanName, Object bean, BeanDefinition beanDefinition) {

        for (BeanPostProcessor beanPostProcessor : getBeanPostProcessors())
            if (beanPostProcessor instanceof InstantiationAwareBeanPostProcessor) {
                boolean bool = ((InstantiationAwareBeanPostProcessor) beanPostProcessor).postProcessAfterInstantiation(bean, beanName);
                if (!bool) {
                    return ;
                }
            }

        PropertyValues pvs = beanDefinition.getPropertyValues();
        for (PropertyValue propertyValue:pvs.getPropertyValueList()){
            String name = propertyValue.getName();
            Object value = propertyValue.getValue();
            if(value instanceof BeanReference beanReference){
                value=getBean(beanReference.getBeanName());
            }
            propertyValue.setValue(value);
        }

        for (BeanPostProcessor beanPostProcessor : getBeanPostProcessors())
            if (beanPostProcessor instanceof InstantiationAwareBeanPostProcessor) {
                PropertyValues newPvs=((InstantiationAwareBeanPostProcessor) beanPostProcessor).postProcessAfterProperties(pvs,bean, beanName);
                if (newPvs == null) {
                    return ;
                }
                pvs = newPvs;
            }

        applyPropertyValues(beanName, bean,pvs);
    }

    /**
     * 调用bean实例化前置处理器
     * @param beanClass bean类
     * @param beanName bean名称
     * @return bean实例
     */
    private Object applyBeanPostProcessorsBeforeInstantiation(Class<?> beanClass, String beanName) {
        for (BeanPostProcessor beanPostProcessor : getBeanPostProcessors())
            if (beanPostProcessor instanceof InstantiationAwareBeanPostProcessor) {
                Object object = ((InstantiationAwareBeanPostProcessor) beanPostProcessor).postProcessBeforeInstantiation(beanClass, beanName);
                if (object != null) {
                    return object;
                }
            }
        return null;
    }


    /**
     * 初始化bean
     * @param bean bean实例
     * @param name bean名称
     * @param beanDefinition bean定义
     * @return bean实例
     */
    private Object initializeBean(Object bean, String name, BeanDefinition beanDefinition) {

        if(bean instanceof BeanFactoryAware)
            ((BeanFactoryAware) bean).setBeanFactory(this);
        Object wrappedBean =bean;
        wrappedBean=applyBeanPostProcessorsBeforeInitialization(wrappedBean, name);
        try {
            invokeInitMethods(name, wrappedBean, beanDefinition);
        } catch (Exception e) {
            throw new BeanException("初始化bean失败:"+e.getMessage(), e);

        }

        wrappedBean =applyBeanPostProcessorsAfterInitialization(wrappedBean, name);
        return wrappedBean;
    }
    /**
     * 调用bean的初始化方法
     * @param beanName bean名称
     * @param bean bean实例
     * @param beanDefinition bean定义
     * @throws Exception
     */
    protected void invokeInitMethods(String beanName, Object bean, BeanDefinition beanDefinition) throws Exception {
        if(bean instanceof InitializingBean){
            ((InitializingBean) bean).afterPropertiesSet();
        }
        String initMethodName = beanDefinition.getInitMethodName();
        Class<?> beanClass = beanDefinition.getBeanClass();

        try {
            if (initMethodName != null) {
                beanClass.getMethod(initMethodName).invoke(bean);
            }
        }catch (IllegalAccessException | InvocationTargetException e) {
            throw new BeanException("调用初始化方法失败", e);
        } catch (NoSuchMethodException e) {
            throw new BeanException("初始化方法不存在", e);
        }

    }

    @Override
    /**
     * 调用bean前置处理器
     * @param bean bean实例
     * @param beanName bean名称
     * @return bean实例
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
     * @param bean bean实例
     * @param beanName bean名称
     * @return bean实例
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
     * @param bean bean实例
     * @param beanName bean名称
     * @return bean实例
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
     * @param beanName bean名称
     * @param bean bean实例
     * @param propertyValues 属性值
     */
    protected void applyPropertyValues(String beanName, Object bean, PropertyValues propertyValues){

        if(propertyValues.isEmpty())
            return;
        for (PropertyValue propertyValue:propertyValues.getPropertyValueList()){
            String name = propertyValue.getName();
            Object value = propertyValue.getValue();


            Class<?> targetType = (Class<?>) TypeUtil.getFieldType(bean.getClass(), name);

            if (targetType != null && !value.getClass().isAssignableFrom(targetType)) {
                try {
                    value = getConversionService().convert(value, targetType);
                } catch (ConverterException e) {
                    throw new BeanException(e.getMessage(), e);
                }
            }

            try {
                String setterName = "set" + name.substring(0, 1).toUpperCase() + name.substring(1);

                Method[] methods = bean.getClass().getMethods();
                boolean found = false;
                for (Method method : methods) {

                    if (method.getName().equals(setterName) && method.getParameterCount() == 1) {
                        method.invoke(bean, value);
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    throw new BeanException(beanName + "不存在" + name + "对应的set方法");
                }


            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new BeanException(beanName+"set方法调用失败",e);
            }
        }
    }


}
