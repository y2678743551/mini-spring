package org.springframework.beans.factory;
/**
 *
 * @date: 2026/8/31
 */
public interface BeanFactory {
    Object getBean(String beanName) throws Exception;
    <T> T getBean(String beanName, Class<T> requiredType) throws Exception;
    boolean containsBean(String beanName);
}
