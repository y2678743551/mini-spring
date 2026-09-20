package springframework.ioc;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import springframework.bean.BeanA;
import springframework.bean.BeanB;
import springframework.bean.BeanC;
import springframework.bean.ExampleBean;

public class PlaceholderTest {
    @Test
    public void testCircularReference() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:Placeholder.xml");
        ExampleBean bean =(ExampleBean) context.getBean("exampleBean");
        System.out.println(bean.getName());
    }
}
