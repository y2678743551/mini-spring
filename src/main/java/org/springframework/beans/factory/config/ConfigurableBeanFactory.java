package org.springframework.beans.factory.config;

import org.springframework.beans.factory.HierarchicalBeanFactory;
import org.springframework.core.convert.ConversionService;
import org.springframework.util.StringValueResolver;


/*
 * @description: 可配置的bean工厂接口
 * @date: 2026/9/3
 */
public interface ConfigurableBeanFactory extends HierarchicalBeanFactory, SingletonBeanRegistry {

    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);

    void setConversionService(ConversionService conversionService);

    ConversionService getConversionService();

    String resolveEmbeddedValue(String value);
    void addEmbeddedValueResolver(StringValueResolver valueResolver);
}