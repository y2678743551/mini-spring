package org.springframework.core.env;

import org.springframework.core.env.PropertySources;
import java.util.Map;
/*
 * 可配置的环境接口
 * @date 2026/9/13
 */
public interface ConfigurableEnvironment extends Environment {

    MutablePropertySources getPropertySources();

    /**
     * @param profile 激活的环境
     */
    void addActiveProfile(String profile);

    /**
     * @param profiles 激活的环境
     */
    void setActiveProfiles(String... profiles);

    /**
     * @param profiles 默认的环境
     */
    void setDefaultProfiles(String... profiles);

    /**
     * @param parent 合并环境
     */
    void merge(ConfigurableEnvironment parent);

    /**
     * @return 系统属性
     */
    Map<String, Object> getSystemProperties();

    /**
     * @return 系统环境
     */
    Map<String, Object> getSystemEnvironment();
}
