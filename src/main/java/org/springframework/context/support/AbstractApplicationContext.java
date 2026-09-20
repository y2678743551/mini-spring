package org.springframework.context.support;

import org.springframework.beans.BeanException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigableApplicationContext;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.io.DefaultResourceLoader;

import java.util.Map;

/*
* @description: 抽象应用上下文
* @date 2026/9/10
* */
public abstract class AbstractApplicationContext extends DefaultResourceLoader implements ConfigableApplicationContext {

    private static final String APPLICATION_EVENT_MULTICASTER_BEAN_NAME = "applicationEventMulticaster";

    private ConfigurableEnvironment environment;

    private ApplicationEventMulticaster applicationEventMulticaster;
    @Override
    public void refresh()  throws BeanException {
        //准备环境变量
        prepareRefresh();
        //获取beanFactory
        ConfigurableListableBeanFactory beanFactory = obtainFreshBeanFactory();
        //准备beanFactory
        prepareBeanFactory(beanFactory);
        //子类扩展
        postBeanFactoryProcessor();
        //执行beanFactory处理器
        invokeBeanFactoryPostProcessors(beanFactory);
        //注册Bean处理器
        registerBeanPostProcessors(beanFactory);
        //初始化事件多播器
        initApplicationEventMulticaster();
        //子类扩展
        onFresh();
        //注册监听器
        registerListeners();
        //完成bean实例化
        finishBeanFactoryInitialization(beanFactory);
        //完成刷新并发布事件
        finishRefresh();

    }

    private void finishBeanFactoryInitialization(ConfigurableListableBeanFactory beanFactory) {
        beanFactory.preInstantiateSingletons();
    }

    private void registerListeners() {
        getBeanFactory().getBeansOfType(ApplicationListener.class).forEach((beanName, listener) -> {
            applicationEventMulticaster.addApplicationListener(listener);
        });
    }

    private void onFresh() {
        //占位，给子类扩展
    }

    private void initApplicationEventMulticaster() {
        this.applicationEventMulticaster = new SimpleApplicationEventMulticaster();
        getBeanFactory().registerSingleton(APPLICATION_EVENT_MULTICASTER_BEAN_NAME, applicationEventMulticaster);
    }

    private void registerBeanPostProcessors(ConfigurableListableBeanFactory beanFactory) {
        Map<String, BeanPostProcessor> postProcessorMap = beanFactory.getBeansOfType(BeanPostProcessor.class);
        postProcessorMap.forEach((beanName, beanPostProcessor) -> {
            beanFactory.addBeanPostProcessor(beanPostProcessor);
        });
    }

    private void invokeBeanFactoryPostProcessors(ConfigurableListableBeanFactory beanFactory) {
        Map<String, BeanFactoryPostProcessor> postProcessorMap = beanFactory.getBeansOfType(BeanFactoryPostProcessor.class);
        postProcessorMap.forEach((beanName, beanPostProcessor) -> {
            beanPostProcessor.postProcessBeanFactory(beanFactory);
        });
    }

    private void postBeanFactoryProcessor() {
        //占位，给子类扩展
    }

    private void prepareBeanFactory(ConfigurableListableBeanFactory beanFactory) {
        beanFactory.addBeanPostProcessor(new ApplicationContextAwareProcessor(this));
    }



    public abstract ConfigurableListableBeanFactory getBeanFactory() throws IllegalStateException ;

    private void finishRefresh() {
        applicationEventMulticaster.multicastEvent(new ContextRefreshedEvent(this));
    }

    private void prepareRefresh() {

        initPropertySources();

        getEnvironment();
    }

    protected void initPropertySources() {

    }

    private ConfigurableEnvironment getEnvironment() {
        if (this.environment == null)
            this.environment = new StandardEnvironment();
        return environment;
    }

    protected abstract ConfigurableListableBeanFactory obtainFreshBeanFactory();

    @Override
    public void close() {
        applicationEventMulticaster.multicastEvent(new ContextClosedEvent(this));

        getBeanFactory().destroySingletons();
    }

    @Override
    public void registerShutdownHook() {
        Thread thread = new Thread(this::close);
        Runtime.getRuntime().addShutdownHook(thread);
    }



    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeanException {
        return getBeanFactory().getBeansOfType(type);
    }

    @Override
    public Object getBean(String beanName) {
        return getBeanFactory().getBean(beanName);
    }

    @Override
    public <T> T getBean(String beanName, Class<T> requiredType) {
        return getBeanFactory().getBean(beanName, requiredType);
    }
    @Override
    public <T> T getBean(Class<T> requiredType) {
        return getBeanFactory().getBean(requiredType);
    }
    @Override
    public boolean containsBean(String beanName) {
        return getBeanFactory().containsBean(beanName);
    }
}
