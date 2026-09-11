package org.springframework.context;
/*
 * @description: 应用事件发布器
 * @date: 2026/9/11
 * */
public interface ApplicationEventPublisher {
    public void publishEvent(ApplicationEvent event) ;
}
