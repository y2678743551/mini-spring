package org.springframework.core.convert.support;

import org.springframework.core.convert.ConverterException;

/*
 * @description: 默认转换服务
 * @date: 2026/9/5
 * */
public class DefaultConversionService extends GenericConversionService{
    public DefaultConversionService() {
        try {
            addConverterFactory(new StringToNumberConverter());

        } catch (ConverterException e) {
            throw new ConverterException(e.getMessage());
        }
    }
}
