package org.springframework.core.env;

import java.util.Objects;
/*
 * 属性源接口
 * @date 2026/9/13
 */
public abstract class PropertySource<T> {

    private final String name;
    protected final T source;

    public PropertySource(String name, T source) {
        this.name = name;
        this.source = source;
    }

    /**
     * @return 属性源名称
     */
    public String getName() {
        return name;
    }

    /**
     * @return 属性源对象
     */
    public T getSource() {
        return source;
    }

    /**
     * @param name 属性名称
     * @return 是否包含该属性
     */
    public boolean containsProperty(String name) {
        return getProperty(name) != null;
    }

    /**
     * @param name 属性名称
     * @return 属性值
     */
    public Object getProperty(String name){
        return source;
    };

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof PropertySource that))
            return false;
        return Objects.equals(name, that.name) && Objects.equals(source, that.source);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }


    @Override
    public String toString() {
        return "PropertySource [name='" + name + "', source='" + source + "']";
    }
}
