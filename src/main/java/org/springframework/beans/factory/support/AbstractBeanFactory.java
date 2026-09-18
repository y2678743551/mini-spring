package org.springframework.beans.factory.support;


import com.sun.source.tree.UsesTree;
import org.springframework.beans.*;


import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.config.SingletonBeanRegistry;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements ConfigurableBeanFactory {
    private final List<BeanPostProcessor> beanPostProcessors = new ArrayList<>();
    private final Map<String, Object> factoryBeanCache = new HashMap<String, Object>();
    private ConversionService conversionService;
    /**
     * 根据beanName和requiredType获取bean
     * @param beanName bean名称
     * @param requiredType 获取的bean类型
     * @param <T> bean类型
     * @return bean实例
     * @throws BeanException
     */
    @SuppressWarnings("unchecked")
    @Override
    public <T> T getBean(String beanName, Class<T> requiredType) throws BeanException {
        return (T) getBean(beanName);
    }

    /**
     * 根据beanName获取bean
     * @param beanName bean名称
     * @return bean实例
     * @throws BeanException
     */
    @Override
    public Object getBean(String beanName) throws BeanException {
        Object singleton = getSingleton(beanName);
        if(singleton!=null){
            return getBeanForBeanInstance(singleton, beanName);
        }

        BeanDefinition beanDefinition = getBeanDefinition(beanName);
        if(beanDefinition == null)
            throw new BeanException("未找到BeanDefinition "+beanName);

        Object bean = createBean(beanName   , beanDefinition);
        if(beanDefinition.isSingleton())
            registerSingleton(beanName, bean);
        return getBeanForBeanInstance(bean, beanName);
    }

    /**
     * 如果是FactoryBean，从FactoryBean获取bean
     * @param beanInstance bean实例
     * @param beanName bean名称
     * @return 获取的bean实例
     */
    protected Object getBeanForBeanInstance(Object beanInstance, String beanName) {
        Object result = beanInstance;
        if (beanInstance instanceof FactoryBean){
            FactoryBean factoryBean = (FactoryBean) beanInstance;
            try {
                if (factoryBean.isSingleton()){
                    result = factoryBeanCache.get(beanName);
                    if (result == null) {
                        result = factoryBean.getObject();
                        factoryBeanCache.put(beanName, result);
                    }
                }else {
                    result= factoryBean.getObject();
                }
            } catch (Exception e) {
                throw new BeanException("从FactoryBean获取bean失败: " + beanName, e);
            }
        }

        return result;
    }
    /**
     * 添加beanPostProcessor
     * @param beanPostProcessor beanPostProcessor实例
     */
    @Override
    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor){
        beanPostProcessors.remove(beanPostProcessor);
        beanPostProcessors.add(beanPostProcessor);
    }
    /**
     * 获取beanPostProcessors
     * @return beanPostProcessors
     */
    public List<BeanPostProcessor> getBeanPostProcessors() {
        return beanPostProcessors;
    }
    /**
     * 判断是否包含bean
     * @param beanName bean名称
     * @return 是否包含bean
     */
    @Override
    public boolean containsBean(String beanName) {
        if (containsSingleton(beanName))
            return true;
        return containsBeanDefinition(beanName);
    }
    @Override
    public ConversionService getConversionService() {
        return conversionService;
    }
    @Override
    public void setConversionService(ConversionService conversionService) {
        this.conversionService =  conversionService;
    }
    protected abstract BeanDefinition getBeanDefinition(String beanName) throws BeanException;
    protected abstract boolean containsBeanDefinition(String beanName);
    public abstract Object createBean(String beanName, BeanDefinition beanDefinition);


}
