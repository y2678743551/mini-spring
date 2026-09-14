package org.springframework.core.env;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
/*
 * 抽象环境类
 * @date 2026/9/13
 */
public abstract class AbstractEnvironment implements ConfigurableEnvironment {

    public static final String ACTIVE_PROFILES_PROPERTY_NAME = "spring.profiles.active";

    public static final String DEFAULT_PROFILES_PROPERTY_NAME = "spring.profiles.default";

    protected static final String DEFAULT_PROFILE_NAME = "default";

    private final MutablePropertySources propertySources = new MutablePropertySources();

    private final Set<String> activeProfiles = new LinkedHashSet<>();

    private final Set<String> defaultProfiles = new LinkedHashSet<>(Collections.singleton(DEFAULT_PROFILE_NAME));

    protected abstract void customizePropertySources(MutablePropertySources propertySources) ;
    /**
     * 验证profile名称
     *
     * @param profiles profile名称
     * @throws IllegalArgumentException 如果profile名称不合法
     */
    private void validateProfiles(String... profiles) {
        for (String profile : profiles) {
            validateProfile(profile);
        }
    }
    /**
     * 验证profile名称
     *
     * @param profile profile名称
     * @throws IllegalArgumentException 如果profile名称不合法
     */
    private void validateProfile(String profile) {
        if (profile == null || profile.isEmpty()) {
            throw new IllegalArgumentException("Profile不能为空");
        }
    }

    /**
     * 添加活动profile
     *
     * @param profile profile名称
     */
    @Override
    public void addActiveProfile(String profile) {

        validateProfile(profile);
        this.activeProfiles.add(profile);
    }

    /**
     * 设置活动profile
     *
     * @param profiles profile名称数组
     */
    @Override
    public void setActiveProfiles(String... profiles) {
        if(profiles == null || profiles.length == 0)
        {
            activeProfiles.clear();
            return;
        }
        validateProfiles(profiles);
        this.activeProfiles.clear();
        Collections.addAll(this.activeProfiles, profiles);
    }

    /**
     * 设置默认profile
     *
     * @param profiles profile名称数组
     */
    @Override
    public void setDefaultProfiles(String... profiles) {
        if(profiles == null || profiles.length == 0)
        {
            defaultProfiles.clear();
            return;
        }
        validateProfiles(profiles);
        this.defaultProfiles.clear();
        Collections.addAll(this.defaultProfiles, profiles);

    }
    /**
     * 合并环境
     *
     * @param parent 父环境
     */

    @Override
    public void merge(ConfigurableEnvironment parent) {
        if (parent == null) {
            return;
        }
        for (Map.Entry<String, Object> entry : parent.getSystemProperties().entrySet())
            this.propertySources.addLast(new MapPropertySource(entry.getKey(), Collections.singletonMap(entry.getKey(), entry.getValue())));

        Set<String> parentActiveProfiles = parent.getActiveProfiles();
        if (!parentActiveProfiles.isEmpty())
            activeProfiles.addAll(parentActiveProfiles);
        Set<String> parentDefaultProfiles = parent.getDefaultProfiles();
        if (!parentDefaultProfiles.isEmpty())
            defaultProfiles.addAll(parentDefaultProfiles);
    }

    /**
     * 获取系统属性
     *
     * @return 系统属性
     */
    @Override
    public Map<String, Object> getSystemProperties() {

        return (Map)System.getProperties();
    }
    /**
     * 获取系统环境
     *
     * @return 系统环境
     */
    @Override
    public Map<String, Object> getSystemEnvironment() {

        return (Map)System.getenv();
    }

    /**
     * 获取属性
     *
     * @param key 属性键
     * @return 属性值
     */
    @Override
    public String getProperty(String key) {
        for (PropertySource<?> propertySource : this.propertySources) {
            Object value = propertySource.getProperty(key);
            if (value != null) {
                return value.toString();
            }
        }
        return null;
    }
    /**
     * 获取属性
     *
     * @param key          属性键
     * @param defaultValue 默认值
     * @return 属性值
     */
    @Override
    public String getProperty(String key, String defaultValue) {
        if (containsProperty(key)) {
            return getProperty(key);
        }
        return defaultValue;
    }
    /**
     * 获取属性
     *
     * @param key         属性键
     * @param targetType  属性类型
     * @param <T>         属性类型
     * @return 属性值
     */

    @Override
    public <T> T getProperty(String key, Class<T> targetType) {
        String value = getProperty(key);
        if (value == null) {
            return null;
        }
        return targetType.cast(value);
    }
    /**
     * 获取属性
     *
     * @param key         属性键
     * @param targetType  属性类型
     * @param defaultValue 默认值
     * @param <T>         属性类型
     * @return 属性值
     */

    @Override
    public <T> T getProperty(String key, Class<T> targetType, T defaultValue) {
        if (containsProperty(key)) {
            return getProperty(key, targetType);
        }
        return defaultValue;
    }
    /**
     * 是否包含属性
     *
     * @param key 属性键
     * @return 是否包含属性
     */

    @Override
    public boolean containsProperty(String key) {
        return propertySources.contains(key);
    }
    /**
     * 是否接受profile
     *
     * @param profiles profile名称数组
     * @return 是否接受profile
     */

    @Override
    public boolean acceptsProfiles(String... profiles) {
        for (String profile : profiles)
            if (activeProfiles.contains(profile)) {
                return true;
            }
        return false;
    }
    /**
     * 是否接受profile
     *
     * @param profiles profile名称
     * @return 是否接受profile
     */
    @Override
    public boolean acceptsProfiles(String profiles) {
        return activeProfiles.contains(profiles);
    }
    /**
     * 获取活动profile
     *
     * @return 活动profile
     */

    @Override
    public Set<String> getActiveProfiles() {
        return activeProfiles;
    }
    /**
     * 获取默认profile
     *
     * @return 默认profile
     */

    @Override
    public Set<String> getDefaultProfiles() {
        return defaultProfiles;
    }
}
