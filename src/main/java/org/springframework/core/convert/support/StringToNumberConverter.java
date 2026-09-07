package org.springframework.core.convert.support;

import org.springframework.core.convert.ConverterException;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

import java.lang.reflect.InvocationTargetException;

public class StringToNumberConverter implements ConverterFactory<String, Number> {

    @Override
    public <T extends Number> Converter<String, T> getConverter(Class<T> targetType) {
        return new StringToNumber<T>(targetType);

    }
    private static class StringToNumber<T extends Number> implements Converter<String, T> {
        private Class<T> targetType;
        public StringToNumber(Class<T> targetType) {
            this.targetType = targetType;
        }
        @Override
        public T convert(String source) {
            if (source == null || source.isEmpty())
                return null;
            try {
                return (T) targetType.getMethod("valueOf", String.class).invoke(null, source);
            } catch (NoSuchMethodException  e) {
                try {
                    T t = targetType.getConstructor(String.class).newInstance(source);
                    return t;
                } catch (Exception ex) {
                    throw new ConverterException("无法将 " + source + " 转换为 " + targetType, ex);
                }
            } catch (Exception e) {
                throw new ConverterException("无法将 " + source + " 转换为 " + targetType, e);
            }
        }
    }

}
