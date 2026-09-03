package org.springframework.beans.factory.support;

import org.dom4j.DocumentException;
import org.springframework.beans.BeanException;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;

/**
 *
 * @date: 2026/8/31
 */
public interface BeanDefinitionReader {
    void loadBeanDefinitions(String location) throws BeanException, DocumentException, IOException;

    void loadBeanDefinitions(Resource resource) throws BeanException, IOException, DocumentException;

    void loadBeanDefinitions(Resource... resources) throws BeanException, DocumentException, IOException;

    BeanDefinitionRegistry getRegistry();


    ResourceLoader getResourceLoader();
}
