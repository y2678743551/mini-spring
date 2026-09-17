package org.springframework.aop;

import java.lang.reflect.Method;
/**
 *
 * @date 2026/9/17
 */
public interface AfterReturningAdvice extends AfterAdvice {
    void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable;
}
