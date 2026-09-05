package org.springframework.core.convert.converter;
/*
 * @description:类型转换器
 * @date:2026/9/4
 * */
public interface Converter <S, T> {
    T convert(S source);
}
