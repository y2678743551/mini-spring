package springframework.aop;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import springframework.bean.ExampleBean;

public class AutoProxyTest {
    @Test
    public void test() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:AutoProxy.xml");

        ExampleBean bean = (ExampleBean) applicationContext.getBean("exampleBean");
        System.out.println(bean.getName());
    }
}
