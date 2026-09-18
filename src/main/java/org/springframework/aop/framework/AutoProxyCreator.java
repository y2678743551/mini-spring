package org.springframework.aop.framework;

import org.springframework.aop.Advisor;
import org.springframework.aop.TargetSource;
import org.springframework.aop.aspectJ.AspectJExpressionPointcutAdvisor;
import org.springframework.beans.BeanException;
import org.springframework.beans.BeanFactoryAware;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
/**
 * 代理创建类
 * @date 2026/9/17
 */
public class AutoProxyCreator implements InstantiationAwareBeanPostProcessor , BeanFactoryAware {
    private ConfigurableListableBeanFactory beanFactory;
    private final Set<String> proxyTargetClassNames=new HashSet<>();
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeanException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeanException {
        return wrapIfNecessary(bean, beanName);
    }



    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeanException {
        return null;
    }

    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeanException {
        return true;
    }
    private Object wrapIfNecessary(Object bean, String beanName) {
        if(proxyTargetClassNames.contains(beanName))
            return bean;
        Class<?> beanClass = bean.getClass();
        if (Advisor.class.isAssignableFrom(beanClass))
            return bean;
        Map<String,AspectJExpressionPointcutAdvisor> advisors = beanFactory.getBeansOfType(AspectJExpressionPointcutAdvisor.class);
        AdvisedSupport advisedSupport = new AdvisedSupport();
        advisedSupport.setTargetSource(new TargetSource(bean));
        advisedSupport.setTargetClass(beanClass);
        for (AspectJExpressionPointcutAdvisor advisor : advisors.values()) {
            if(!advisor.getPointcut().getClassFilter().matches(beanClass))
                continue;
            advisedSupport.addAdvisor(advisor);

        }
        if (!advisedSupport.getAdvisors().isEmpty())
            return new ProxyFactory(advisedSupport).getProxy();
        return bean;
    }

    @Override
    public Object getEarlyBeanReference(Object bean, String beanName) throws BeanException {
        proxyTargetClassNames.add(beanName);
        return wrapIfNecessary(bean, beanName);
    }

    @Override
    public PropertyValues postProcessAfterProperties(PropertyValues pvs, Object bean, String beanName) throws BeanException {
        return pvs;
    }



    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeanException {
        this.beanFactory = (ConfigurableListableBeanFactory) beanFactory;
    }
}
