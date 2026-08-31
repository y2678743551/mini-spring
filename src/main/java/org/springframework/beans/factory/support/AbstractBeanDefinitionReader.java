package org.springframework.beans.factory.support;

import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
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

    public BeanDefinitionRegistry getRegistry() {
        return registry;
    }

    public ResourceLoader getResourceLoader() {
        return resourceLoader;
    }

    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }



    @Override
    public void loadBeanDefinitions(Resource... resources) throws Exception {
        for (Resource resource : resources)
            loadBeanDefinitions(resource);

    }
}
