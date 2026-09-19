package org.springframework.beans.factory.config;

import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.annotation.AutowiredAnnotationPostProcessor;

/*
 * @description:描述bean的元数据
 * @date 2026/8/30
 */
public class BeanDefinition {

    private final static String SCOPE_SINGLETON = "singleton";
    private final static String SCOPE_PROTOTYPE = "prototype";

    private String beanName;
    private String beanId;
    private String scope=SCOPE_SINGLETON;
    private boolean lazyInit;
    private String initMethod;
    private String destroyMethod;
    private Class<?> beanClass;
    private PropertyValues propertyValues=new PropertyValues();

    public BeanDefinition(Class<?> beanClass) {
        this.beanClass = beanClass;
        beanName = beanClass.getName();
    }
    public BeanDefinition() {

    }

    public void setPropertyValues(PropertyValues propertyValues) {
        this.propertyValues = propertyValues;
    }
    public PropertyValues getPropertyValues() {
        return propertyValues;
    }

    public void setBeanClass(Class<?> beanClass) {
        this.beanClass = beanClass;
    }
    public Class<?> getBeanClass() {
        return beanClass;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }
    public String getBeanName() {
        return beanName;
    }
    public void setBeanId(String beanId) {
        this.beanId = beanId;
    }
    public String getBeanId() {
        return beanId;
    }
    public void setScopeSingleton(String scope) {
        this.scope = SCOPE_SINGLETON;
    }
    public void setScopePrototype(String scope) {
        this.scope = SCOPE_PROTOTYPE;
    }
    public void setScope(String scope) {
        if (scope != null && (scope.equals(SCOPE_PROTOTYPE)||scope.equals(SCOPE_SINGLETON)))
            this.scope = scope;
    }

    public String getScope() {
        return scope;
    }

    public void setLazyInit(boolean lazyInit) {
        this.lazyInit = lazyInit;
    }
    public boolean isLazyInit() {
        return lazyInit;
    }
    public void setInitMethodName(String initMethod) {
        this.initMethod = initMethod;
    }
    public String getInitMethodName() {return initMethod;}
    public void setDestroyMethodName(String destroyMethod) {
        this.destroyMethod = destroyMethod;
    }
    public String getDestroyMethodName() {
        return destroyMethod;
    }
    public void setClass(String className) {
        if(className != null && !className.isEmpty())
            try {
                this.beanClass = Class.forName(className,true,Thread.currentThread().getContextClassLoader());
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
    }

    public boolean isSingleton() {
        return SCOPE_SINGLETON.equals(scope);
    }
    public boolean isPrototype() {
        return SCOPE_PROTOTYPE.equals(scope);
    }
}

