package org.springframework.context.support;

import org.dom4j.DocumentException;
import org.springframework.beans.BeanException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;

import java.io.IOException;

public abstract class AbstractRefreshableApplicationContext extends AbstractApplicationContext {
    private DefaultListableBeanFactory beanFactory;
    @Override
    protected ConfigurableListableBeanFactory obtainFreshBeanFactory() throws BeanException {
        refreshBeanFactory();
        return getBeanFactory();
    }

    protected void refreshBeanFactory() throws BeanException {
        if (this.beanFactory!=null){
            this.beanFactory.destroySingletons();
            this.beanFactory = null;
        }

        DefaultListableBeanFactory beanFactory  = createBeanFactory();
        this.beanFactory= beanFactory;
        loadBeanDefinitions(beanFactory);

    }

    protected abstract void loadBeanDefinitions(DefaultListableBeanFactory beanFactory) throws BeanException;

    private DefaultListableBeanFactory createBeanFactory() {
        return new DefaultListableBeanFactory();

    }

    ;

    @Override
    public ConfigurableListableBeanFactory getBeanFactory() throws IllegalStateException {
        if(beanFactory == null)
            throw new IllegalStateException("BeanFactory not initialized or already closed");
        return beanFactory;
    }
}
