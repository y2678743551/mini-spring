package springframework.ioc;

import org.junit.Test;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;

public class PostProcessorTest {
    @Test
    public void postProcessor(){
        DefaultListableBeanFactory defaultListableBeanFactory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(defaultListableBeanFactory);
        reader.loadBeanDefinitions("classpath:bean.xml");
        defaultListableBeanFactory.addBeanPostProcessor(new BeanPostProcessor(){
            @Override
            public Object postProcessBeforeInitialization(Object bean, String beanName) {
                System.out.println("前置处理");
                return bean;
            }
            @Override
            public Object postProcessAfterInitialization(Object bean, String beanName) {
                System.out.println("后置处理");
                return bean;
            }
        });
        defaultListableBeanFactory.getBean("exampleBean");
    }
}
