package org.springframework.core.convert.converter;
/*
 * @description:转换器注册表
 * @date:2026/9/4
 * */
public interface ConverterRegistry {
    void addConverter(Converter<?,?> converter);
    void removeConverter(Class<?> sourceType, Class<?> targetType);
    <S,T>void addConverter(Converter<? super S, ? extends T> converter, Class<S> sourceType, Class<T> targetType);
    void addConverterFactory(ConverterFactory<?,?> converterFactory);
}
