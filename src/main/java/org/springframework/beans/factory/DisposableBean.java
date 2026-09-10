package org.springframework.beans.factory;

import org.springframework.beans.BeanException;

public interface DisposableBean {
    void destroy() throws BeanException;
}
