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
            System.out.println("'exampleBean' 存在");
        }
        Object exampleBean = defaultListableBeanFactory.getBean("exampleBean");
        ExampleBean bean = (ExampleBean) exampleBean;
        System.out.println("exampleBean 的 name 属性值为：" + bean.getName());
        defaultListableBeanFactory.containsBean("exampleBean");
        System.out.println("exampleBean 的类型为：" + exampleBean.getClass());
        if(reader.getRegistry().containsBeanDefinition("exampleBean2")) {
            System.out.println("'exampleBean2' 存在");
        }
    }
    public static class ExampleBean {
        public ExampleBean() {
            System.out.println("ExampleBean instance created.");
        }
        private String name;

        public void setName(String name) {
            this.name = name;
        }
        public String getName() {
            return name;
        }
    }
}
