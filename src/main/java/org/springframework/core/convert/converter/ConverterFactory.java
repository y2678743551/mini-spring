package org.springframework.core.convert.converter;
/*
 * @description:转换器工厂
 * @date:2026/9/4
 * */
public interface ConverterFactory<S, T> {
    <U extends T> Converter<S, U> getConverter(Class<U> targetType);
}
