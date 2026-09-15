package org.springframework.aop.framework;

import org.aopalliance.intercept.MethodInterceptor;
import org.springframework.aop.MethodMatcher;

public class InterceptorsAndDynamicMethodMatcher {
    final MethodInterceptor methodInterceptor;

    final MethodMatcher methodMatcher;

    public InterceptorsAndDynamicMethodMatcher(MethodInterceptor methodInterceptor, MethodMatcher methodMatcher) {
        this.methodInterceptor = methodInterceptor;
        this.methodMatcher = methodMatcher;
    }
}
