package org.springframework.core.convert.support;

import org.springframework.core.convert.converter.Converter;

public class StringToNumberConverter<T extends Number> implements Converter<String, T> {
    private Class<T> targetType;
    @Override
    public T convert(String source) {
        if (source == null || source.isEmpty())
            return null;
        if(targetType.equals(Integer.class))
            return (T) Integer.valueOf(source);
        else if(targetType.equals(Double.class))
            return (T) Double.valueOf(source);
        else if(targetType.equals(Float.class))
            return (T) Float.valueOf(source);
        else if(targetType.equals(Long.class))
            return (T) Long.valueOf(source);
        else if(targetType.equals(Short.class))
            return (T) Short.valueOf(source);
        else if(targetType.equals(Byte.class))
            return (T) Byte.valueOf(source);
        else
            throw new IllegalArgumentException("Unsupported target type: " + targetType.getName());
    }
}
