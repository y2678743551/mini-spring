package org.springframework.beans.factory;
/**
 *
 * @date: 2026/8/31
 */
public interface BeanFactory {
    Object getBean(String name) throws Exception;
    <T> T getBean(String name, Class<T> requiredType) throws Exception;
    boolean containsBean(String name);
}
