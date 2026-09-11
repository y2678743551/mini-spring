package org.springframework.beans.factory;

import org.springframework.beans.BeanException;

/**
 *
 * @date: 2026/8/31
 */
public interface BeanFactory {
    Object getBean(String beanName) throws BeanException;
    <T> T getBean(String beanName, Class<T> requiredType) throws BeanException;
    boolean containsBean(String beanName);
}
