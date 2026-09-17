package org.springframework.aop.adapter;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.AfterAdvice;
import org.springframework.aop.AfterReturningAdvice;
/**
 * AfterReturningAdvice的适配器
 * @date 2026/9/17
 */
public class AfterReturningAdviceAdapter implements AfterAdvice, MethodInterceptor {
    private AfterReturningAdvice advice;
    public AfterReturningAdviceAdapter() {

    }
    public AfterReturningAdviceAdapter(AfterReturningAdvice afterAdvice) {
        this.advice = afterAdvice;
    }

    public void setAdvice(AfterReturningAdvice afterAdvice) {
        this.advice = afterAdvice;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Object result = invocation.proceed();
        advice.afterReturning(result, invocation.getMethod(), invocation.getArguments(), invocation.getThis());
        return result;
    }
}
