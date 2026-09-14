package org.springframework.beans.factory;
/*
 *
 * @date: 2026/9/14
 */
public interface InitializingBean {

    void afterPropertiesSet() throws Exception;

}
