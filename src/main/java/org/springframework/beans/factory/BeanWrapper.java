package org.springframework.beans.factory;

public interface BeanWrapper {
    Object getWrappedInstance();
    Class<?> getWrappedClass();

    void setPropertyValues(PropertyValues propertyValues) throws Exception;
}
