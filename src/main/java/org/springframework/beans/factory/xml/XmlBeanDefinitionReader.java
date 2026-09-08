package org.springframework.beans.factory.xml;

import cn.hutool.core.util.StrUtil;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.BeanException;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanReference;
import org.springframework.beans.factory.support.AbstractBeanDefinitionReader;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.core.io.Resource;

import java.io.IOException;

/**
 * @date: 2026/8/31
 * @description: XML bean definition reader
 */
public class XmlBeanDefinitionReader extends AbstractBeanDefinitionReader {
    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry) {
        super(registry);
    }

    @Override
    public void loadBeanDefinitions(String location) throws DocumentException, IOException {
        Resource resource = getResourceLoader().getResource(location);

        loadBeanDefinitions(resource);
    }

    @Override
    public void loadBeanDefinitions(Resource resource) throws IOException, DocumentException {
        SAXReader reader = new SAXReader();
        Document document = reader.read(resource.getInputStream());

        Element rootElement = document.getRootElement();


        for (Element bean : rootElement.elements(XmlConstants.BEAN_ELEMENT)){
            String id = bean.attributeValue(XmlConstants.ID_ATTRIBUTE);
            String name = bean.attributeValue(XmlConstants.NAME_ATTRIBUTE);
            String className = bean.attributeValue(XmlConstants.CLASS_ATTRIBUTE);
            String scope = bean.attributeValue(XmlConstants.SCOPE_ATTRIBUTE);
            String lazyInit = bean.attributeValue(XmlConstants.LAZY_INIT_ATTRIBUTE);
            String initMethod = bean.attributeValue(XmlConstants.INIT_METHOD_ATTRIBUTE);
            String destroyMethod = bean.attributeValue(XmlConstants.DESTROY_METHOD_ATTRIBUTE);
            BeanDefinition beanDefinition = new BeanDefinition();
            beanDefinition.setBeanName(name);
            beanDefinition.setBeanId(id);
            beanDefinition.setClass(className);
            beanDefinition.setScope(scope);
            beanDefinition.setLazyInit(Boolean.parseBoolean(lazyInit));
            beanDefinition.setInitMethodName(initMethod);
            beanDefinition.setDestroyMethodName(destroyMethod);

            for (Element property : bean.elements(XmlConstants.PROPERTY_ELEMENT)){
                String propertyName = property.attributeValue(XmlConstants.PROPERTY_NAME_ATTRIBUTE);
                String value = property.attributeValue(XmlConstants.PROPERTY_VALUE_ATTRIBUTE);
                String ref = property.attributeValue(XmlConstants.PROPERTY_REF_ATTRIBUTE);
                if(StrUtil.isNotEmpty(ref)&&StrUtil.isNotEmpty(value))
                    throw new BeanException("value与ref不能同时存在");
                if(StrUtil.isNotEmpty(ref))
                    beanDefinition.getPropertyValues().addPropertyValue(new PropertyValue(propertyName, new BeanReference(ref)));
                else
                    beanDefinition.getPropertyValues().addPropertyValue(new PropertyValue(propertyName, value));
            }
            getRegistry().registerBeanDefinition(id, beanDefinition);
        }
    }
}
