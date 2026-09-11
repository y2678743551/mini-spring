package org.springframework.context.event;

import org.springframework.beans.BeanFactoryAware;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

import java.util.HashSet;
import java.util.Set;
/*
 * @description: 应用事件多播器抽象类
 * @date: 2026/9/11
 * */
public abstract class AbstractApplicationEventMulticaster implements ApplicationEventMulticaster , BeanFactoryAware {
    private BeanFactory beanFactory;
    protected final Set<ApplicationListener<?>> applicationListeners=new HashSet<>();
    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @Override
    public void addApplicationListener(ApplicationListener<?> listener) {
        applicationListeners.add(listener);
    }

    @Override
    public void removeApplicationListener(ApplicationListener<?> listener) {
        applicationListeners.remove(listener);
    }
}
