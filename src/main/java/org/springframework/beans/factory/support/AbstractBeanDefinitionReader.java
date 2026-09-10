package org.springframework.beans.factory.support;

import org.dom4j.DocumentException;
import org.springframework.beans.BeanException;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;

/**
 *
 * @date: 2026/8/31
 */
public abstract class AbstractBeanDefinitionReader implements BeanDefinitionReader {
    private final BeanDefinitionRegistry registry;
    private ResourceLoader resourceLoader;



    public AbstractBeanDefinitionReader(BeanDefinitionRegistry registry) {
        this.registry = registry;
        resourceLoader = new DefaultResourceLoader();
    }


    public AbstractBeanDefinitionReader(BeanDefinitionRegistry registry, ResourceLoader resourceLoader) {
        this.registry = registry;
        this.resourceLoader = resourceLoader;
    }

    /**
     * 获取注册表
     * @return
     */
    public BeanDefinitionRegistry getRegistry() {
        return registry;
    }

    /**
     * 获取资源加载器
     * @return
     */
    public ResourceLoader getResourceLoader() {
        return resourceLoader;
    }

    /**
     * 设置资源加载器
     * @param resourceLoader
     */
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    /**
     * 加载Bean定义
     * @param locations
     * @throws BeanException
     * @throws IOException
     */
    @Override
    public void loadBeanDefinitions(String[] locations) throws BeanException{
        for (String location : locations)
            loadBeanDefinitions(getResourceLoader().getResource(location));
    }
}
