package org.springframework.beans.factory;

import org.springframework.beans.BeanException;

import java.util.Map;

/*
* @description: 可列表化的bean工厂接口
* @date: 2026/9/3
*/
public interface ListableBeanFactory extends BeanFactory {
    <T> Map<String, T> getBeansOfType(Class<T> type) throws BeanException;
}
