package org.springframework.beans.factory;

public class BeanWrapperImpl implements BeanWrapper {
    private Object wrappedInstance;
    private Class<?> wrappedClass;

    public BeanWrapperImpl(Object bean) {
        wrappedClass = bean.getClass();
        wrappedInstance = bean;
    }

    @Override
    public Object getWrappedInstance() {
        return wrappedInstance;
    }

    @Override
    public Class<?> getWrappedClass() {
        return wrappedClass;
    }



    @Override
    public void setPropertyValues(PropertyValues propertyValues) throws Exception {

    }
}
