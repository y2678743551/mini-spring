package springframework.aop;

import org.junit.Test;
import org.springframework.aop.aspectJ.AspectJExpressionPointcut;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import springframework.bean.BeanA;
import springframework.bean.BeanB;
import springframework.common.Hello;

public class ComponentScanTest {
    @Test
    public void test()  {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:PackageScan.xml");
        Object bean = context.getBean("ComponentBean");
        BeanB beanB = (BeanB)context.getBean("BeanB");
        System.out.println(bean);
        System.out.println(beanB);
        System.out.println(context.containsBean("BeanA"));
    }
}
