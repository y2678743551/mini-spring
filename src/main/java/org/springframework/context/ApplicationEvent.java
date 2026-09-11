package org.springframework.context;

import java.util.EventObject;
/*
* @description: 应用事件
* @date: 2026/9/11
* */
public class ApplicationEvent extends EventObject {

    public ApplicationEvent(Object source) {
        super(source);
    }
}
