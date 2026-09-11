package org.springframework.context.event;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;


import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
/*
 * @description: 应用事件多播器
 * @date: 2026/9/11
 * */
public class SimpleApplicationEventMulticaster extends AbstractApplicationEventMulticaster {
    @Override
    public void multicastEvent(ApplicationEvent event) {
        for (ApplicationListener listener : applicationListeners) {
            if(supportsListener(listener,event))
                listener.onApplicationEvent(event);
            }
    }

    private boolean supportsListener(ApplicationListener listener, ApplicationEvent event) {
        Type[] types = listener.getClass().getGenericInterfaces();
        for (Type type : types) {
            if (type == ApplicationListener.class) {
                Class<?> eventClass = ((ParameterizedType) type).getActualTypeArguments()[0].getClass();
                return eventClass.isAssignableFrom(event.getClass());
            }
        }
        return false;
    }
}
