package org.springframework.context;

import org.springframework.beans.BeanException;
/**
 * @date :2026/9/10
 */
public interface ConfigableApplicationContext extends ApplicationContext {
    /**
     * 刷新应用上下文
     * @throws BeanException
     */
    void refresh() throws BeanException;

    /**
     *关闭上下文
     */
    void close();

    /**
     *注册关闭钩子方法
     */
    void registerShutdownHook();

}
