package org.springframework.beans.factory;

/*
 * @description: 层次结构的bean工厂接口
 * @date: 2026/9/3
 */
public interface HierarchicalBeanFactory extends BeanFactory {
    BeanFactory getParentBeanFactory();
}
