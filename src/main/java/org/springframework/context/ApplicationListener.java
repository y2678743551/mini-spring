package org.springframework.context;

import java.util.EventListener;
/*
* @description: 应用事件监听器
* @date: 2026/9/11
* */
public interface ApplicationListener<E extends ApplicationEvent> extends EventListener{
    void onApplicationEvent(E event);
}
