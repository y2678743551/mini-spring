package org.springframework.beans.factory.annotation;

import org.springframework.beans.BeanException;
import org.springframework.beans.BeanFactoryAware;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.core.convert.ConversionService;

import java.lang.reflect.Field;
/*
* 自动装配注解处理器
* @date 2026/9/18
* */
public class AutowiredAnnotationPostProcessor implements InstantiationAwareBeanPostProcessor, BeanFactoryAware {
    private ConfigurableListableBeanFactory beanFactory;
    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = (ConfigurableListableBeanFactory) beanFactory;
    }

    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeanException {
        return null;
    }

    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeanException {
        return true ;
    }

    @Override
    public PropertyValues postProcessAfterProperties(PropertyValues pvs, Object bean, String beanName) throws BeanException {

        Field[] fields = bean.getClass().getDeclaredFields();
        // autowired注解处理
        for (Field field : fields) {
            if (field.isAnnotationPresent(Autowired.class)) {
                field.setAccessible(true);
                Class<?> fieldType = field.getType();
                Object beanToInject = null;
                if(field.isAnnotationPresent(Qualifier.class)) {
                    Qualifier qualifier = field.getAnnotation(Qualifier.class);
                    String value = qualifier.value();
                    beanToInject = beanFactory.getBean(value, fieldType);
                }else
                    beanToInject = beanFactory.getBean(fieldType);
                try {

                    field.set(beanToInject, pvs.getPropertyValue(field.getName()).getValue());
                } catch (IllegalAccessException e) {
                    throw new BeanException("", e);
                }
            }
        }

        // value注解处理
        for (Field field : fields) {
            if (field.isAnnotationPresent(Value.class)) {
                field.setAccessible(true);
                Value valueAnnotation = field.getAnnotation(Value.class);
                String value = valueAnnotation.value();
                value = beanFactory.resolveEmbeddedValue(value);
                Class<?> fieldType = field.getType();
                Object convertedValue = null;
                ConversionService conversionService = beanFactory.getConversionService();
                if(conversionService != null && conversionService.canConvert(value.getClass(), fieldType))
                    convertedValue = conversionService.convert(value, fieldType);
                pvs.addPropertyValue(new PropertyValue(field.getName(), convertedValue));
            }
        }

        return pvs;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeanException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeanException {
        return bean;
    }
}
