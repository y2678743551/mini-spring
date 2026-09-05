package org.springframework.core.convert.support;
/*
 * @description: 默认转换服务
 * @date: 2026/9/5
 * */
public class DefaultConversionService extends GenericConversionService{
    public DefaultConversionService() {
        addConverter(new StringToNumberConverter());
    }
}
