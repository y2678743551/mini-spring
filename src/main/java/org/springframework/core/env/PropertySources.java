package org.springframework.core.env;

import java.util.Iterator;

/*
 * 属性源集合接口
 * @date 2026/9/13
 */
public interface PropertySources extends Iterable<PropertySource<?>> {

    /**
     * @param name 属性源名称
     * @return 属性源
     */
    PropertySource<?> get(String name);

    /**
     * @param name 属性源名称
     * @return 是否包含该属性源
     */
    boolean contains(String name);
    /**
     * 返回属性源的迭代器
     *
     * @return 属性源迭代器
     */
    @Override
    Iterator<PropertySource<?>> iterator();
}
