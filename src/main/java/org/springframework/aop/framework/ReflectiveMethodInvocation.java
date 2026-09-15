package org.springframework.aop.framework;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;
import java.util.List;
/**
 * 实现MethodInvocation接口，提供了方法拦截的实现
 * @date 2026 /9/15
 */
public class ReflectiveMethodInvocation implements MethodInvocation {

    protected final Object proxy;

    protected final Method method;

    protected final Object[] arguments;

    protected final Object target;

    protected final Class<?> targetClass;

    protected final List<Object> interceptorsAndDynamicMethodMatchers;

    private int currentInterceptorIndex = -1;

    public ReflectiveMethodInvocation(Object proxy,Object target, Method method, Object[] arguments,Class<?> targetClass,List<Object> chain) {
        this.proxy=proxy;
        this.target = target;
        this.method = method;
        this.arguments = arguments;
        this.targetClass=targetClass;
        this.interceptorsAndDynamicMethodMatchers=chain;
    }

    /**
     * 执行拦截器链
     * @return
     * @throws Throwable
     */
    @Override
    public Object proceed() throws Throwable {
        if (this.currentInterceptorIndex == this.interceptorsAndDynamicMethodMatchers.size() - 1) {
            return this.method.invoke(this.target, this.arguments);
        }
        Object interceptorOrInterceptionAdvice =
                this.interceptorsAndDynamicMethodMatchers.get(++this.currentInterceptorIndex);
        if (interceptorOrInterceptionAdvice instanceof InterceptorsAndDynamicMethodMatcher) {
            InterceptorsAndDynamicMethodMatcher dm =
                    (InterceptorsAndDynamicMethodMatcher) interceptorOrInterceptionAdvice;
            if (dm.methodMatcher.matches(this.method, this.targetClass)) {
                return dm.methodInterceptor.invoke(this);
            }else {
                return proceed();
            }
        }
        MethodInterceptor interceptor = (MethodInterceptor) interceptorOrInterceptionAdvice;
        return interceptor.invoke(this);

    }

    @Override
    public Method getMethod() {
        return method;
    }

    @Override
    public Object[] getArguments() {
        return arguments;
    }



    @Override
    public Object getThis() {
        return proxy;
    }

    @Override
    public AccessibleObject getStaticPart() {
        return method;
    }
}
