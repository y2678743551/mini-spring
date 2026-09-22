package org.springframework.beans.factory;

import cn.hutool.core.lang.hash.Hash;
import org.springframework.beans.BeanException;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.core.env.*;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.util.StringValueResolver;

import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

public class PropertyPlaceholderConfigurer implements BeanFactoryPostProcessor , EnvironmentAware {

    public static final String PLACEHOLDER_PREFIX = "${";

    public static final String PLACEHOLDER_SUFFIX = "}";

    private String[] locations;

    private ConfigurableEnvironment environment;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeanException {

        loadProperties();




        StringValueResolver valueResolver = new PlaceholderResolvingStringValueResolver(environment);
        beanFactory.addEmbeddedValueResolver(valueResolver);
    }




    private void loadProperties() {
        for (String location : locations)
        {
            try {
                DefaultResourceLoader resourceLoader = new DefaultResourceLoader();
                Resource resource = resourceLoader.getResource(location);
                Properties properties = new Properties();
                properties.load(resource.getInputStream());
                HashMap<String, Object> map = new HashMap<>();
                for (String key : properties.stringPropertyNames()) {
                    map.put(key, properties.getProperty(key));
                }
                environment.getPropertySources().addLast(new MapPropertySource("properties", map));

            } catch (IOException e) {
                throw new BeanException("未找到属性配置文件", e);
            }
        }
    }

    public void setLocation(String location) {

        locations = location.split(",");
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = (ConfigurableEnvironment) environment;
    }

    private static class PlaceholderResolvingStringValueResolver implements StringValueResolver {

        private ConfigurableEnvironment environment;

        public PlaceholderResolvingStringValueResolver(ConfigurableEnvironment environment) {
            this.environment = environment;
        }

        public String resolveStringValue(String strVal) throws BeanException {

            StringBuffer buf = new StringBuffer(strVal);
            int startIndex = strVal.indexOf(PLACEHOLDER_PREFIX);
            while (startIndex != -1) {
                int endIndex = strVal.indexOf(PLACEHOLDER_SUFFIX);
                if (endIndex != -1) {
                    String placeholder = strVal.substring(startIndex + PLACEHOLDER_PREFIX.length(), endIndex);
                    String replacement = environment.getProperty(placeholder);
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
