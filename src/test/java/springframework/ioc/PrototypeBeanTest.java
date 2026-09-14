package springframework.ioc;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class PrototypeBeanTest {
    @Test
    public void testPrototypeBean() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:PrototypeBean.xml");
        Object bean1 = context.getBean("prototypeBean");
        Object bean2 = context.getBean("prototypeBean");
        System.out.println(bean1 == bean2);
    }
}
