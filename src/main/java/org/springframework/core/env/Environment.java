package org.springframework.core.env;

import java.util.Set;

/*
 * 环境接口
 * @date 2026/9/13
 */
public interface Environment {
    /**
     * 根据键获取属性
     * @param key 键
     * @return 属性
     */
    String getProperty(String key);

    /**
     * 根据键获取属性，如果不存在则返回默认值
     * @param key 键
     * @param defaultValue 默认值
      * @return 属性
     */
    String getProperty(String key, String defaultValue);


    /**
     * 根据键获取特定类的属性
     * @param key 键
     * @param targetType 类型
     * @param <T> 类型
      * @return 属性
     */
    <T> T getProperty(String key,Class<T> targetType);


    /**
     * 根据键获取特定类的属性，如果不存在则返回默认值
     * @param key 键
     * @param targetType 类型
     * @param defaultValue 默认值
     * @param <T> 类型
      * @return 属性
     */
    <T> T getProperty(String key,Class<T> targetType,T defaultValue);

    /**
     * 根据键判断是否包含该属性
     * @param key 键
      * @return 是否包含该键
     */
    boolean containsProperty(String key);

    /**
     * 判断是否接受该配置文件
     * @param profiles 配置文件
      * @return 是否接受该配置文件
     */
    boolean acceptsProfiles(String... profiles);

    /**
     * 判断是否接受该配置文件
     * @param profiles 配置文件
      * @return 是否接受该配置文件
     */
    boolean acceptsProfiles(String profiles);

    /**
      * @return 激活的配置文件
     */
    Set<String> getActiveProfiles();

    /**
      * @return 默认的配置文件
     */
    Set<String> getDefaultProfiles();
}
