package org.springframework.aop;

import java.lang.reflect.Method;
/**
 *
 * @date 2026/9/17
 */
public interface MethodBeforeAdvice extends BeforeAdvice {
    void before(Method method, Object[] args, Object target) throws Throwable;
}
