package org.springframework.aop.framework;

import org.springframework.aop.Advisor;
import org.springframework.aop.PointcutAdvisor;
import org.springframework.aop.TargetSource;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
/**
 * Aop框架核心类
 * @date 2026/9/15
 */
public class AdvisedSupport {
    private boolean frozen = false;

    private TargetSource targetSource;


    private Class<?> targetClass;


    private List<Advisor> advisors = new ArrayList<>();

    private boolean proxyTargetClass = true;

    private transient Map<Method, List<Object>> methodCache = new ConcurrentHashMap<>(32);

    public boolean isFrozen() {
        return frozen;
    }

    public void setFrozen(boolean frozen) {
        if (!isFrozen())
            this.frozen = frozen;
    }

    public TargetSource getTargetSource() {
        return targetSource;
    }

    public void setTargetSource(TargetSource targetSource) {

        if (!isFrozen())
            this.targetSource = targetSource;
    }

    public Class<?> getTargetClass() {
        return targetClass;
    }

    public void setTargetClass(Class<?> targetClass) {

        if (!isFrozen())
            this.targetClass = targetClass;
    }

    public List<Advisor> getAdvisors() {
        return advisors;
    }

    public void addAdvisor(Advisor advisor) {

        if (!isFrozen())
            this.advisors.add(advisor);
    }
    public boolean isProxyTargetClass() {
        return proxyTargetClass;
    }

    public void setProxyTargetClass(boolean proxyTargetClass) {
        if (!isFrozen())
            this.proxyTargetClass = proxyTargetClass;
    }
    /**
     * 根据方法和目标类获取拦截器链
     * @param method 方法
     * @param targetClass 目标类
     * @return 拦截器链
     */
    public List<Object> getInterceptorsAndDynamicInterceptionAdvice(Method method, Class<?> targetClass) {
        List<Object> cached = this.methodCache.get(method);
        if (cached != null) {
            return cached;
        }
        List<Object> interceptors = new ArrayList<>();
        for (Advisor advisor : this.advisors){
            if (advisor instanceof PointcutAdvisor pointcutAdvisor) {
                boolean classMatches = pointcutAdvisor.getPointcut().getClassFilter().matches(targetClass);
                Class<?>[] interfaces = targetClass.getInterfaces();
                if (!classMatches) {

                    for (Class<?> anInterface : interfaces) {
                        boolean methodMatches = pointcutAdvisor.getPointcut().getMethodMatcher().matches(method, anInterface);
                        if (methodMatches)
                            break;
                    }
                }

                boolean methodMatches = pointcutAdvisor.getPointcut().getMethodMatcher().matches(method, targetClass);
                if (!methodMatches){
                    for (Class<?> anInterface : interfaces){
                        try {
                            Method interfaceMethod = anInterface.getMethod(method.getName(), method.getParameterTypes());
                            methodMatches = pointcutAdvisor.getPointcut().getMethodMatcher().matches(interfaceMethod, targetClass);
                            if (methodMatches)
                                break;
                        } catch (NoSuchMethodException e) {
                            continue;
                        }
                    }
                }


                if (classMatches && methodMatches) {
                    interceptors.add(pointcutAdvisor.getAdvice());
                }
            }else
                interceptors.add(advisor.getAdvice());
        }

        this.methodCache.put(method, interceptors);
        return interceptors;
    }


}
