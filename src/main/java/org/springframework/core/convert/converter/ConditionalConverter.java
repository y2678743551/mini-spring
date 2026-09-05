package org.springframework.core.convert.converter;
/*
 * @description:条件转换器
 * @date:2026/9/5
 * */
public interface ConditionalConverter {
    boolean matches(Class<?> sourceType, Class<?> targetType);
}
