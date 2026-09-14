package springframework.ioc;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class FactoryBeanTest {
    @Test
    public void testFactoryBean() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:FactoryBean.xml");
        System.out.println(context.getBean("exampleFactoryBean").getClass());
    }

}
