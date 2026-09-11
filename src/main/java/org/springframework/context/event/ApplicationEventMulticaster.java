package org.springframework.context.event;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
/*
* @description: 应用事件多播器接口
* @date: 2026/9/11
* */
public interface ApplicationEventMulticaster {
    /**
     * 多播事件
     * @param event
     */
    void multicastEvent(ApplicationEvent event);

    /**
     * 添加监听器
     * @param listener
     */
    void addApplicationListener(ApplicationListener<?> listener);

    /**
     * 移除监听器
     * @param listener
     */
    void removeApplicationListener(ApplicationListener<?> listener);
}
