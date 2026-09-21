package org.springframework.context.annotation;

import cn.hutool.core.util.StrUtil;
import org.springframework.beans.factory.annotation.AutowiredAnnotationPostProcessor;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;

import java.io.IOException;
import java.util.Set;

public class ClassPathBeanDefinitionScanner extends ClassPathScanningCandidateComponentProvider {

    public static final String AUTOWIRED_ANNOTATION_PROCESSOR_BEAN_NAME = "org.springframework.context.annotation.internalAutowiredAnnotationProcessor";
    private BeanDefinitionRegistry registry;

    public ClassPathBeanDefinitionScanner(BeanDefinitionRegistry registry) {
        this.registry = registry;
    }


    public void doScan(String... basePackages) throws IOException {

        for (String basePackage : basePackages) {
            Set<BeanDefinition> candidates = findCandidateComponents(basePackage);
            for (BeanDefinition candidate : candidates) {

                candidate.setBeanName(determineBeanName(candidate));
                candidate.setScope(resolveBeanScope(candidate));
                registry.registerBeanDefinition(candidate.getBeanName(), candidate);
            }
        }
        registry.registerBeanDefinition(AUTOWIRED_ANNOTATION_PROCESSOR_BEAN_NAME, new BeanDefinition(AutowiredAnnotationPostProcessor.class));
    }
    /**
     * 解析bean的作用域
     *
     * @param beanDefinition
     * @return
     */
    private String resolveBeanScope(BeanDefinition beanDefinition) {
        Class<?> beanClass = beanDefinition.getBeanClass();
        Scope scope = beanClass.getAnnotation(Scope.class);
        if (scope != null) {
            return scope.value();
        }

        return "";
    }


    /**
     * 生成bean的名称
     *
     * @param beanDefinition
     * @return
     */
    private String determineBeanName(BeanDefinition beanDefinition) {
        Class<?> beanClass = beanDefinition.getBeanClass();
        Component component = beanClass.getAnnotation(Component.class);
        if (component != null && !StrUtil.isEmpty(component.value())) {
            return component.value();
        }

        return beanClass.getSimpleName();
    }
}
