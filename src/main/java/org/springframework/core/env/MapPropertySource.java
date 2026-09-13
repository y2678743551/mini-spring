package org.springframework.core.env;

import java.util.Map;
/*
 * Map属性源类
 * @date 2026/9/13
 */
public class MapPropertySource extends PropertySource<Map<String, Object>> {
    public MapPropertySource(String name, Map<String, Object> source) {
        super(name, source);
    }

    @Override
    public Object getProperty(String name) {
        return this.source.get(name);
    }
    @Override
    public boolean containsProperty(String name) {
        return this.source.containsKey(name);
    }
}
