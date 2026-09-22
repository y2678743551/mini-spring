package org.springframework.core.env;

import org.springframework.beans.BeanException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;

import java.util.HashMap;
import java.util.Properties;

public class EnvironmentAwareProcessor implements BeanPostProcessor {
    private final ConfigurableEnvironment environment;

    public EnvironmentAwareProcessor(Environment environment) {
        this.environment = (ConfigurableEnvironment) environment;
    }
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeanException {
        if(bean instanceof EnvironmentAware)
            ((EnvironmentAware) bean).setEnvironment(environment);
        try {
            DefaultResourceLoader resourceLoader = new DefaultResourceLoader();
            Resource resource = resourceLoader.getResource("classpath:application.properties");
            Properties properties = new Properties();
            if (resource.getInputStream()!=null)
                properties.load(resource.getInputStream());
            HashMap<String, Object> map = new HashMap<String, Object>();
            for (String key : properties.stringPropertyNames())
                map.put(key, properties.getProperty(key));
            environment.getPropertySources().addLast(new MapPropertySource("application.properties", map));
            String activeProfiles = properties.getProperty("spring.profiles.active");
            environment.setActiveProfiles(activeProfiles != null ? activeProfiles.split(",") : new String[0]);
            if (environment.acceptsProfiles("dev"))
            {
                resource = resourceLoader.getResource("classpath:propertiesapplication-dev.properties");
                properties = new Properties();
                if (resource.getInputStream()!=null)
                   properties.load(resource.getInputStream());
                map.clear();
                for (String key : properties.stringPropertyNames())
                    map.put(key, properties.getProperty(key));
                environment.getPropertySources().addLast(new MapPropertySource("application-dev.properties", map));

            }
        } catch (Exception e) {
            throw new BeanException("加载properties失败:"+e.getMessage(), e);
        }


        return bean;

    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeanException {

        return bean;
    }
}
