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
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.io.Resource;
import org.springframework.core.filter.TypeFilter;

import java.io.IOException;
import java.util.List;

/**
 * @date: 2026/8/31
 * @description: XML bean definition reader
 */
public class XmlBeanDefinitionReader extends AbstractBeanDefinitionReader {
    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry) {
        super(registry);
    }

    @Override
    public void loadBeanDefinitions(String location) throws BeanException {
        Resource resource = getResourceLoader().getResource(location);

        loadBeanDefinitions(resource);
    }

    @Override
    public void loadBeanDefinitions(Resource resource) throws BeanException {
        SAXReader reader = new SAXReader();
        try {
            Document document = reader.read(resource.getInputStream());
            doLoadBeanDefinitions(document);
        } catch (DocumentException |IOException e) {
            throw new BeanException("解析XML文档失败: " + resource, e);
        }


    }
    private void doLoadBeanDefinitions(Document document) {
        Element rootElement = document.getRootElement();

        Element componentScan = rootElement.element(XmlConstants.COMPONENT_SCAN_ELEMENT);
        if (componentScan != null)
            scanPackage(componentScan);


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
                else {
                    beanDefinition.getPropertyValues().addPropertyValue(new PropertyValue(propertyName, value));
                }
            }
            getRegistry().registerBeanDefinition(id, beanDefinition);
        }
    }



    private void scanPackage(Element componentScan) {

        String scanPath = componentScan.attributeValue(XmlConstants.COMPONENT_SCAN_BASE_PACKAGE_ATTRIBUTE );
        if (StrUtil.isEmpty(scanPath)) {
            throw new BeanException("未指定包路径");
        }
        ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(getRegistry());

        for (Element excludeFilter: componentScan.elements(XmlConstants.COMPONENT_SCAN_EXCLUDE_FILTER_ATTRIBUTE)){

            String expression = excludeFilter.attributeValue(XmlConstants.COMPONENT_SCAN_EXPRESSION_ATTRIBUTE);

            List<String> excludeFilters = StrUtil.split(expression, ',');

            for (String filter : excludeFilters){
                scanner.addExcludeFilter(new TypeFilter() {
                    @Override
                    public boolean match(String className) {
                        return filter.contains(className);
                    }
                });
            }
        }
        for (Element includeFilter: componentScan.elements(XmlConstants.COMPONENT_SCAN_INCLUDE_FILTER_ATTRIBUTE)){

            String expression = includeFilter.attributeValue(XmlConstants.COMPONENT_SCAN_EXPRESSION_ATTRIBUTE);

            List<String> includeFilters = StrUtil.split(expression, ',');

            for (String filter : includeFilters){
                scanner.addIncludeFilter(new TypeFilter() {
                    @Override
                    public boolean match(String className) {
                        return filter.contains(className);
                    }
                });
            }
        }


        String[] basePackages = StrUtil.splitToArray(scanPath, ',');


        try {
            scanner.doScan(basePackages);

        } catch (IOException e) {
            throw new BeanException("扫描包路径失败: " + scanPath, e);
        }
    }
}
