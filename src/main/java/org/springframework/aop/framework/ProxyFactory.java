package org.springframework.aop.framework;
/**
 * 代理工厂类
 * @date 2026/9/17
 */
public class ProxyFactory {
    private AdvisedSupport advisedSupport;

    public ProxyFactory(AdvisedSupport advisedSupport) {
        this.advisedSupport = advisedSupport;
    }

    public Object getProxy() {
        return createAopProxy().getProxy();
    }
    private AopProxy createAopProxy() {
        if (!advisedSupport.isProxyTargetClass()) {
            Class<?>[] targetClass = advisedSupport.getTargetSource().getTarget().getClass().getInterfaces();
            if (targetClass.length == 0) {
                return new CglibAopProxy(advisedSupport);
            }
            return new JdkDynamicAopProxy(advisedSupport);
        }
        return new CglibAopProxy(advisedSupport);
    }


    public void setAdvisedSupport(AdvisedSupport advisedSupport) {
        this.advisedSupport = advisedSupport;
    }
}
