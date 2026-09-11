package org.springframework.context;

import org.springframework.beans.BeanException;
/*
* @description: 应用程序上下文感知接口
* @date: 2026/9/11
* */
public interface ApplicationContextAware {
    void setApplicationContext(ApplicationContext applicationContext) throws BeanException;
}
