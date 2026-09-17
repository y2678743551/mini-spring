package org.springframework.aop.adapter;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.BeforeAdvice;
import org.springframework.aop.MethodBeforeAdvice;

/**
 * MethodBeforeAdvice的适配器
 * @date 2026/9/17
 */
public class MethodBeforeAdviceAdapter implements BeforeAdvice, MethodInterceptor {
    private MethodBeforeAdvice advice;

    public MethodBeforeAdviceAdapter() {
    }
    public MethodBeforeAdviceAdapter(MethodBeforeAdvice beforeAdvice) {
        this.advice = beforeAdvice;
    }

    public void setAdvice(MethodBeforeAdvice beforeAdvice) {
        this.advice = beforeAdvice;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        advice.before(invocation.getMethod(), invocation.getArguments(), invocation.getThis());
        return invocation.proceed();
    }



}
