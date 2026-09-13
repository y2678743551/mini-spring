package springframework.ioc;

import org.junit.Test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import springframework.bean.Aware;

public class AwareTest {
    @Test
    public void test() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:AwareTest.xml");

        Object bean = applicationContext.getBean("Aware");
        System.out.println(bean);
        Aware aware = (Aware) bean;
        System.out.println(aware.getBeanFactory());
        System.out.println(aware.getApplicationContext());
    }
}
