package org.springframework.beans.factory.config;

import org.springframework.beans.factory.BeanFactory;

public interface BeanFactoryPostProcessor {
    void postProcessBeanFactory(BeanFactory beanFactory);


}
