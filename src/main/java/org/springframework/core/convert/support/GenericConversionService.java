package org.springframework.core.convert.support;

import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.ConditionalGenericConverter;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterRegistry;
import org.springframework.core.convert.converter.GenericConverter;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.locks.Condition;
/*
* @description: 通用转换服务
* @date: 2026/9/5
* */
public class GenericConversionService implements ConversionService, ConverterRegistry {

    Map<GenericConverter.ConvertiblePair, ConditionalGenericConverter> converters = new HashMap<>(16);

    @Override
    public void addConverter(Converter<?, ?> converter) {
        GenericConverter.ConvertiblePair convertiblePair = getRequiredTypeInfo(converter);
        ConverterAdapter converterAdapter = new ConverterAdapter(converter, convertiblePair.getSourceType(), convertiblePair.getTargetType());
        for (GenericConverter.ConvertiblePair convertiblePair1 : converterAdapter.getConvertibleTypes()) {
            converters.put(convertiblePair, converterAdapter);
        }
    }
    private ParameterizedType findGenericConverterPt(Object object){
        Type[] types = object.getClass().getGenericInterfaces();

        for (Type type:types) {

            ParameterizedType parameterizedType = (ParameterizedType) type;
            Type rawType = parameterizedType.getRawType();
            if(rawType instanceof GenericConverter){
                return parameterizedType;

            }
        }
        return null;
    }
    private GenericConverter.ConvertiblePair getRequiredTypeInfo(Object object) {
        ParameterizedType parameterized=findGenericConverterPt(object);
        if (parameterized == null)
            return null;
        Type[] actualTypeArguments = parameterized.getActualTypeArguments();
        Class sourceType = (Class) actualTypeArguments[0];
        Class targetType = (Class) actualTypeArguments[1];
        return new GenericConverter.ConvertiblePair(sourceType, targetType);
    }
    @Override
    public void removeConverter(Class<?> sourceType, Class<?> targetType) {
        converters.remove(new GenericConverter.ConvertiblePair(sourceType, targetType));

    }

    @Override
    public <S, T> void addConverter(Converter<? super S, ? extends T> converter, Class<S> sourceType, Class<T> targetType) {
        ConverterAdapter converterAdapter = new ConverterAdapter(converter, sourceType, targetType);
        for (GenericConverter.ConvertiblePair convertiblePair : converterAdapter.getConvertibleTypes()) {
            converters.put(convertiblePair, converterAdapter);
        }
    }

    @Override
    public boolean canConvert(Class<?> sourceType, Class<?> targetType) {

        if (converters.containsKey(new GenericConverter.ConvertiblePair(sourceType, targetType))) {
            return true;
        }
        return false;
    }
    protected GenericConverter getConverter(Class<?> sourceType, Class<?> targetType) {
        List<Class<?>> sourceTypeHierarchy = getClassHierarchy(sourceType);
        List<Class<?>> targetTypeHierarchy = getClassHierarchy(targetType);
        for (Class<?> source : sourceTypeHierarchy) {
            for (Class<?> target : targetTypeHierarchy) {
                ConditionalGenericConverter converter = converters.get(new GenericConverter.ConvertiblePair(source, target));
                if (converter != null && converter.matches(source, target)) {
                    return converter;
                }
            }
        }
        return null;
    }

    private List<Class<?>>  getClassHierarchy(Class<?> type) {
        List<Class<?>> hierarchy = new ArrayList<>();
        Set<Class<?>> visited = new HashSet<>();
        collectClassHierarchy(type, hierarchy, visited);
        return hierarchy;
    }
    private void collectClassHierarchy(Class<?> type, List<Class<?>> hierarchy, Set<Class<?>> visited) {
        if (type == null || visited.contains(type)) {
            return;
        }

        visited.add(type);
        hierarchy.add(type);

        // 收集接口
        for (Class<?> interfaceType : type.getInterfaces()) {
            collectClassHierarchy(interfaceType, hierarchy, visited);
        }

        // 收集父类
        collectClassHierarchy(type.getSuperclass(), hierarchy, visited);
    }

    @Override
    public Object convert(Object source,Class<?> targetType) {
        return null;
    }

    private class ConverterAdapter implements ConditionalGenericConverter {
        private final Converter converter;
        private final ConvertiblePair convertiblePair;
        public ConverterAdapter(Converter converter, Class<?> sourceType, Class<?> targetType) {
            this.converter = converter;
            this.convertiblePair = new ConvertiblePair(sourceType, targetType);
        }

        @Override
        public Set<ConvertiblePair> getConvertibleTypes() {
            return Collections.singleton(convertiblePair);
        }

        @Override
        public Object convert(Object source, Class<?> sourceType, Class<?> targetType) {


            return converter.convert(source);
        }

        @Override
        public boolean matches(Class<?> sourceType, Class<?> targetType) {
            return this.convertiblePair.getSourceType().isAssignableFrom(sourceType) &&
                    targetType.isAssignableFrom(this.convertiblePair.getTargetType());
        }
    }
}
