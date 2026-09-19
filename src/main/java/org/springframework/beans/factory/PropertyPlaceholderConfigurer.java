package org.springframework.beans.factory;

import org.springframework.beans.BeanException;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.util.StringValueResolver;

import java.io.IOException;
import java.util.Properties;

public class PropertyPlaceholderConfigurer implements BeanFactoryPostProcessor {

    public static final String PLACEHOLDER_PREFIX = "${";

    public static final String PLACEHOLDER_SUFFIX = "}";

    private String location;


    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeanException {

        Properties properties = loadProperties();


        processProperties(beanFactory, properties);


        StringValueResolver valueResolver = new PlaceholderResolvingStringValueResolver(properties);
        beanFactory.addEmbeddedValueResolver(valueResolver);
    }

    private void processProperties(ConfigurableListableBeanFactory beanFactory, Properties properties) {
        String[] beanDefinitionNames = beanFactory.getBeanDefinitionNames();
        for (String beanName : beanDefinitionNames) {
            BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanName);
            resolvePropertyValues(beanDefinition, properties);
        }
    }

    private void resolvePropertyValues(BeanDefinition beanDefinition, Properties properties) {
        PropertyValues propertyValues = beanDefinition.getPropertyValues();
        for (PropertyValue propertyValue : propertyValues.getPropertyValueList())
            if (propertyValue.getValue() instanceof String value) {

                propertyValue.setValue(value);
            }
    }


    private Properties loadProperties() {
        try {
            DefaultResourceLoader resourceLoader = new DefaultResourceLoader();
            Resource resource = resourceLoader.getResource(location);
            Properties properties = new Properties();
            properties.load(resource.getInputStream());
            return properties;
        } catch (IOException e) {
            throw new BeanException("未找到属性配置文件", e);
        }
    }

    public void setLocation(String location) {
        this.location = location;
    }

    private class PlaceholderResolvingStringValueResolver implements StringValueResolver {

        private final Properties properties;

        public PlaceholderResolvingStringValueResolver(Properties properties) {
            this.properties = properties;
        }

        public String resolveStringValue(String strVal) throws BeanException {

            StringBuffer buf = new StringBuffer(strVal);
            int startIndex = strVal.indexOf(PLACEHOLDER_PREFIX);
            while (startIndex != -1) {
                int endIndex = strVal.indexOf(PLACEHOLDER_SUFFIX);
                if (endIndex != -1) {
                    String placeholder = strVal.substring(startIndex + PLACEHOLDER_PREFIX.length(), endIndex);
                    String replacement = properties.getProperty(placeholder);
                    if (replacement != null) {
                        buf.replace(startIndex, endIndex + PLACEHOLDER_SUFFIX.length(), replacement);
                    }
                }
                startIndex = buf.indexOf(PLACEHOLDER_PREFIX);
            }
            return buf.toString();

        }
    }
}
