package org.springframework.beans.factory.support;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
/**
 *
 * @date: 2026/8/31
 */
public interface BeanDefinitionReader {
    void loadBeanDefinitions(String location) throws Exception;

    void loadBeanDefinitions(Resource resource) throws Exception;

    void loadBeanDefinitions(Resource... resources) throws Exception;

    BeanDefinitionRegistry getRegistry();


    ResourceLoader getResourceLoader();
}
