package org.springframework.core.env;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MutablePropertySources implements PropertySources {
    private final List<PropertySource<?>> propertySources ;
    MutablePropertySources() {
        this.propertySources = new ArrayList<PropertySource<?>>();
    }
    MutablePropertySources(ArrayList<PropertySource<?>> propertySources) {
        this.propertySources = propertySources;

    }
    /**
     * 在属性源列表的开头添加属性源
     *
     * @param propertySource 要添加的属性源
     */
    public void addFirst(PropertySource<?> propertySource) {
        removeIfPresent(propertySource);
        propertySources.add(0, propertySource);
    }
    /**
     * 在属性源列表的末尾添加属性源
     *
     * @param propertySource 要添加的属性源
     */
    public void addLast(PropertySource<?> propertySource) {
        removeIfPresent(propertySource);
        propertySources.add(propertySource);
    }
    /**
     * 在指定属性源之后添加属性源
     *
     * @param referenceName 参考属性源名称
     * @param propertySource 要添加的属性源
     */
    public void addAfter(String referenceName, PropertySource<?> propertySource) {
        assertLegalRelativeAddition(referenceName, propertySource);
        removeIfPresent(propertySource);
        for (int i = 0; i < propertySources.size(); i++) {
            if (propertySources.get(i).getName().equals(referenceName)) {
                propertySources.add(i + 1, propertySource);
                return;
            }
        }
        throw new IllegalArgumentException("No property source with name '" + referenceName + "'");
    }
    /**
     * 在指定属性源之前添加属性源
     *
     * @param referenceName 参考属性源名称
     * @param propertySource 要添加的属性源
     */
    public void addBefore(String referenceName, PropertySource<?> propertySource) {
        assertLegalRelativeAddition(referenceName, propertySource);
        removeIfPresent(propertySource);
        for (int i = 0; i < propertySources.size(); i++) {
            if (propertySources.get(i).getName().equals(referenceName)) {
                propertySources.add(i, propertySource);
                return;
            }
        }
        throw new IllegalArgumentException("No property source with name '" + referenceName + "'");
    }
    /**
     * 根据名称移除属性源
     *
     * @param name 要移除的属性源名称
     */
    public void remove(String name) {
        PropertySource<?> propertySource = get(name);
        if (propertySource != null) {
            propertySources.remove(propertySource);
        }
    }

    /**
     * 如果属性源已存在则移除
     *
     * @param propertySource 要移除的属性源
     */
    private void removeIfPresent(PropertySource<?> propertySource) {
        this.propertySources.removeIf(source -> source.getName().equals(propertySource.getName()));
    }

    /**
     * 检查相对添加是否合法
     *
     * @param relativePropertySourceName 相对属性源名称
     * @param propertySource 要添加的属性源
     * @throws IllegalArgumentException 如果相对添加不合法
     */
    private void assertLegalRelativeAddition(String relativePropertySourceName, PropertySource<?> propertySource) {
        if (relativePropertySourceName == null) {
            throw new IllegalArgumentException("相对属性源名称不能为null");
        }
        if (propertySource == null) {
            throw new IllegalArgumentException("属性源不能为null");
        }
        if (relativePropertySourceName.equals(propertySource.getName())) {
            throw new IllegalArgumentException("相对属性源与要添加的属性源不能同名");
        }
    }
    /**
     * 替换属性源
     *
     * @param name 要替换的属性源名称
     * @param propertySource 新的属性源
     */
    public void replace(String name, PropertySource<?> propertySource) {
        int index = propertySources.indexOf(name);
        if (index != -1) {
            propertySources.set(index, propertySource);
        }
    }
    /**
     * 清空属性源
     */
    public void clear() {
        propertySources.clear();
    }
    /**
     * 获取属性源数量
     *
     * @return 属性源数量
     */
    public int size() {
        return propertySources.size();
    }
    /**
     * 判断属性源是否为空
     *
     * @return 如果属性源为空返回true，否则返回false
     */
    public boolean isEmpty() {
        return propertySources.isEmpty();
    }
    /**
     * 根据名称获取属性源索引
     *
     * @param name 属性源名称
     * @return 属性源索引，如果不存在则返回-1
     */
    public int indexOf(String name) {
        for (int i = 0; i < propertySources.size(); i++){
            if (propertySources.get(i).getName().equals(name)) {
                return i;
            }
        }
        return -1;
    }
    /**
     * 根据名称获取属性源
     *
     * @param name 属性源名称
     * @return 属性源，如果不存在则返回null
     */

    @Override
    public PropertySource<?> get(String name) {
        for (PropertySource<?> propertySource : propertySources) {
            if (propertySource.getName().equals(name)) {
                return propertySource;
            }
        }
        return null;
    }
    /**
     * 判断属性源是否包含指定名称的属性源
     *
     * @param name 属性源名称
     * @return 如果包含返回true，否则返回false
     */

    @Override
    public boolean contains(String name) {
        for (PropertySource<?> propertySource : propertySources) {
            if (propertySource.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }
    /**
     * 获取属性源迭代器
     *
     * @return 属性源迭代器
     */

    @Override
    public Iterator<PropertySource<?>> iterator() {
        return propertySources.iterator();
    }
}
