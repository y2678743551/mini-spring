package org.springframework.beans.factory.xml;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.AbstractBeanDefinitionReader;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.core.io.Resource;
/**
 * @date: 2026/8/31
 * @description: XML bean definition reader
 */
public class XmlBeanDefinitionReader extends AbstractBeanDefinitionReader {
    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry) {
        super(registry);
    }

    @Override
    public void loadBeanDefinitions(String location) throws Exception {
        Resource resource = getResourceLoader().getResource(location);

        loadBeanDefinitions(resource);
    }

    @Override
    public void loadBeanDefinitions(Resource resource) throws Exception {
        SAXReader reader = new SAXReader();
        Document document = reader.read(resource.getInputStream());

        Element rootElement = document.getRootElement();


        for (Element bean : rootElement.elements(XmlConstants.BEAN_ELEMENT)){
            String id = bean.attributeValue(XmlConstants.ID_ATTRIBUTE);
            String className = bean.attributeValue(XmlConstants.CLASS_ATTRIBUTE);
            String scope = bean.attributeValue(XmlConstants.SCOPE_ATTRIBUTE);
            String lazyInit = bean.attributeValue(XmlConstants.LAZY_INIT_ATTRIBUTE);
            String initMethod = bean.attributeValue(XmlConstants.INIT_METHOD_ATTRIBUTE);
            String destroyMethod = bean.attributeValue(XmlConstants.DESTROY_METHOD_ATTRIBUTE);
            BeanDefinition beanDefinition = new BeanDefinition();
            beanDefinition.setClass(className);
            beanDefinition.setScope(scope);
            beanDefinition.setLazyInit(Boolean.parseBoolean(lazyInit));
            beanDefinition.setInitMethod(initMethod);
            beanDefinition.setDestroyMethod(destroyMethod);
            getRegistry().registerBeanDefinition(id, beanDefinition);
        }
    }
}
