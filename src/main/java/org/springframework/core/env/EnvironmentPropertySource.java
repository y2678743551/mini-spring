package org.springframework.core.env;

import java.util.Map;
/*
 * 环境属性源类
 * @date 2026/9/13
 */
public class EnvironmentPropertySource extends MapPropertySource {
    public static final String ENVIRONMENT_PROPERTY_SOURCE_NAME = "systemEnvironment";
    public EnvironmentPropertySource() {
        super(ENVIRONMENT_PROPERTY_SOURCE_NAME, (Map) System.getenv());
    }
    @Override
    public Object getProperty(String name) {
        Object property = super.getProperty(name);
        if (property != null)
            return property;
        name = name.replace(' ', '_');

        return super.getProperty(name);
    }
    @Override
    public boolean containsProperty(String name) {
        Boolean containsProperty = super.containsProperty(name);
        if (containsProperty.equals(false))
            return containsProperty;
        name = name.replace(' ', '_');

        return super.containsProperty(name);
    }
}
