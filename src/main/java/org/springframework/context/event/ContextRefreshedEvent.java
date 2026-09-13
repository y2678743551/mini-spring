package org.springframework.context.event;

import org.springframework.context.ApplicationContext;

import org.springframework.context.ApplicationEvent;
/*
 * 上下文刷新事件
 * @date 2026/9/13
 */
public class ContextRefreshedEvent extends ApplicationEvent{
    public ContextRefreshedEvent(Object source) {
        super(source);
    }


    public ApplicationContext getSource() {
        return (ApplicationContext)super.getSource();
    }
}
