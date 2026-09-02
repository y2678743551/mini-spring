package springframework;

import org.junit.Test;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;

public class DefaultListableBeanFactoryTest {
    @Test
    public void test() throws Exception {
        DefaultListableBeanFactory defaultListableBeanFactory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(defaultListableBeanFactory);
        reader.loadBeanDefinitions("classpath:bean.xml");
        if(reader.getRegistry().containsBeanDefinition("exampleBean")) {
            System.out.println("Bean 'exampleBean' exists.");
        }
        Object exampleBean = defaultListableBeanFactory.getBean("exampleBean");
        defaultListableBeanFactory.containsBean("exampleBean");
        System.out.println(exampleBean.getClass());
        if(reader.getRegistry().containsBeanDefinition("exampleBean2")) {
            System.out.println("Bean 'exampleBean2' exists.");
        }
    }
    public static class ExampleBean {
        public ExampleBean() {
            System.out.println("ExampleBean instance created.");
        }
    }
}
