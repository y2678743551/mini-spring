package org.springframework.beans.factory;

import org.springframework.beans.BeanException;
/*
 *
 * @date: 2026/9/8
 * */
public interface ObjectFactory<T> {
    T getObject()throws BeanException;
}
