package org.springframework.aop;

import java.lang.reflect.Method;

/*
 * 方法匹配器
 * @date 2026/9/14
 * */
public interface MethodMatcher {
    /**
     * 判断给定的方法是否匹配
     * @param method 给定的方法
     * @return 是否匹配
     */
    boolean matches(Method method, Class<?> targetClass);
}
