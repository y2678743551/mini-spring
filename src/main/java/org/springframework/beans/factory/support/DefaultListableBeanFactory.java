package org.springframework.beans.factory.support;

import org.springframework.beans.BeanException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

import java.util.HashMap;
import java.util.Map;
/*
 * @Date:2026/8/31
 * */
public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory implements BeanDefinitionRegistry, ConfigurableListableBeanFactory {
    private final Map<String, BeanDefinition> beanDefinitionMap = new HashMap<>();
    private BeanFactory parentBeanFactory;

    /**
     * 注册bean定义
     * @param beanName
     * @param beanDefinition
     */
    @Override
    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {

        beanDefinitionMap.put(beanName, beanDefinition);

    }

    /**
     * 获取bean定义
     * @param beanName
     * @return
     */

    @Override
    public BeanDefinition getBeanDefinition(String beanName) {
        return beanDefinitionMap.get(beanName);
    }

    /**
     * 注册销毁bean
     * @param beanName
     * @param bean
     * @param beanDefinition
     */
    public void registerDisposableBean(String beanName, Object bean, BeanDefinition beanDefinition) {
        if(beanDefinition.isSingleton() &&( beanDefinition.getDestroyMethodName() != null || bean instanceof DisposableBean))
            registerDisposableBean(beanName, new DisposableBeanAdapter(bean, beanName, beanDefinition.getDestroyMethodName()));
    }

    /**
     * 预实例化单例bean
     * @throws BeanException
     */
    @Override
    public void preInstantiateSingletons() throws BeanException {
        beanDefinitionMap.forEach((beanName, beanDefinition) -> {
            if (beanDefinition.isSingleton()&& !beanDefinition.isLazyInit()) {
                Object bean = createBean(beanName, beanDefinition);
            }
        });
    }

    /**
     * 判断是否包含bean定义
     * @param beanName
     * @return
     */
    @Override
    public boolean containsBeanDefinition(String beanName) {
        return beanDefinitionMap.containsKey(beanName);
    }
    /**
     * 获取bean定义名称
     * @return
     */

    @Override
    public String[] getBeanDefinitionNames() {
        return beanDefinitionMap.keySet().toArray(new String[0]);
    }
    /**
     * 获取父bean工厂
     * @return
     */

    @Override
    public BeanFactory getParentBeanFactory() {
        return parentBeanFactory;
    }
    /**
     * 设置父bean工厂
     * @param parentBeanFactory
     */

    public void setParentBeanFactory(BeanFactory parentBeanFactory) {
        this.parentBeanFactory = parentBeanFactory;
    }

    /**
     * 根据类型获取bean
     * @param type 类型
     * @param <T>
     * @return
     */
    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeanException {
        Map<String, T> result = new HashMap<>();
        beanDefinitionMap.forEach((beanName, beanDefinition) -> {
            Class<?> beanClass = beanDefinition.getBeanClass();
            if (type.isAssignableFrom(beanClass))
                result.put(beanName, (T) getBean(beanName));
        });
        return result;
    }
}
