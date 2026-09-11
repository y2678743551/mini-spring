package org.springframework.beans;

import org.springframework.beans.factory.BeanFactory;
/*
 * @description: BeanFactory感知接口
 * @date: 2026/9/11
 * */
public interface BeanFactoryAware  {
    void setBeanFactory(BeanFactory beanFactory) throws BeanException;

}
