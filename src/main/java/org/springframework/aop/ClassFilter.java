package org.springframework.aop;
/*
 * ClassFilter接口，类过滤器
 * @date 2026/9/14
 * */
public interface ClassFilter {
    /**
     * 判断给定的类是否匹配
     * @param clazz 给定的类
     * @return
     */
    boolean matches(Class<?> clazz);
}
