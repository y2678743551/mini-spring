package org.springframework.aop.framework;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import net.sf.cglib.proxy.Callback;

import java.lang.reflect.Method;
import java.util.List;
/**
 * 支持CGLIB的AOP代理类
 * @date 2026/9/15
 */

public class CglibAopProxy implements AopProxy {
    private final AdvisedSupport advised;

    public CglibAopProxy(AdvisedSupport advised) {
        this.advised = advised;
    }

    @Override
    public Object getProxy() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(advised.getTargetSource().getTarget().getClass());
        enhancer.setInterfaces(advised.getTargetSource().getTargetClass());
        enhancer.setCallback(new DynamicAdvisedInterceptor(advised));
        return enhancer.create();
    }

    private static class DynamicAdvisedInterceptor implements MethodInterceptor {
        private final AdvisedSupport advised;
        public DynamicAdvisedInterceptor(AdvisedSupport advised) {
            this.advised = advised;
        }

        @Override
        public Object intercept(Object proxy, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {

            Object target = advised.getTargetSource().getTarget();
            Class<?> targetClass = target.getClass();
            List<Object> chain = advised.getInterceptorsAndDynamicInterceptionAdvice(method, targetClass);
            if (chain.isEmpty())
                return method.invoke(target, args);
            return new CglibMethodInvocation(proxy,target, method, args, targetClass, chain, methodProxy).proceed();

        }
    }
    private static class CglibMethodInvocation extends ReflectiveMethodInvocation {
        private final MethodProxy proxy;

        public CglibMethodInvocation(Object proxy, Object target, Method method, Object[] args, Class<?> targetClass, List<Object> chain, MethodProxy methodProxy) {
            super(proxy, target, method, args, targetClass, chain);
            this.proxy = methodProxy;
        }

    }
}
