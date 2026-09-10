package org.springframework.context.support;

import org.springframework.beans.BeanException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ConfigableApplicationContext;
import org.springframework.core.io.DefaultResourceLoader;

import java.util.Map;

/*
* @description: 抽象应用上下文
* @date 2026/9/10
* */
public abstract class AbstractApplicationContext extends DefaultResourceLoader implements ConfigableApplicationContext {
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

    }

    private void onFresh() {
        //占位，给子类扩展
    }

    private void initApplicationEventMulticaster() {

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
        //TODO 准备beanFactory
    }



    public abstract ConfigurableListableBeanFactory getBeanFactory() throws IllegalStateException ;

    private void finishRefresh() {
        //TODO 完成刷新并发布事件
    }

    private void prepareRefresh() {
        //TODO 准备刷新环境
    }

    protected abstract ConfigurableListableBeanFactory obtainFreshBeanFactory();

    @Override
    public void close() {
        //TODO 关闭事件


        getBeanFactory().destroySingletons();
    }

    @Override
    public void registerShutdownHook() {
        //TODO 注册关闭钩子
    }



    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeanException {
        return getBeanFactory().getBeansOfType(type);
    }

    @Override
    public Object getBean(String beanName) throws Exception {
        return getBeanFactory().getBean(beanName);
    }

    @Override
    public <T> T getBean(String beanName, Class<T> requiredType) throws Exception {
        return getBeanFactory().getBean(beanName, requiredType);
    }

    @Override
    public boolean containsBean(String beanName) {
        return getBeanFactory().containsBean(beanName);
    }
}
