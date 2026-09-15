package org.springframework.aop.framework;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

/**
 * 支持JDK动态代理的AOP代理类
 * @date 2026/9/15
 */
public class JdkDynamicAopProxy implements AopProxy, InvocationHandler {
    private final AdvisedSupport advised;

    public JdkDynamicAopProxy(AdvisedSupport advised) {
        this.advised = advised;
    }

    @Override
    public Object getProxy() {
        return Proxy.newProxyInstance(getClass().getClassLoader(), advised.getTargetSource().getTargetClass(), this);
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object target = advised.getTargetSource().getTarget();
        Class<?> targetClass = target.getClass();
        List<Object> chain = advised.getInterceptorsAndDynamicInterceptionAdvice(method, targetClass);
        if (chain.isEmpty())
            return method.invoke(target, args);
        return new ReflectiveMethodInvocation(proxy,target, method, args, targetClass, chain).proceed();
    }
}

