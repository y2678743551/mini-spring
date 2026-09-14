package org.springframework.beans.factory;
/*
 *
 * @date: 2026/9/14
 */
public interface FactoryBean<T> {
    public T getObject() throws Exception ;
    public boolean isSingleton();
}
