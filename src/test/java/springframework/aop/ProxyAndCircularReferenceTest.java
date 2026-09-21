package springframework.aop;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import springframework.bean.BeanA;
import springframework.bean.BeanB;
import springframework.bean.BeanC;
import springframework.bean.ExampleBean;

public class ProxyAndCircularReferenceTest {
    @Test
    public void test()  {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:ProxyAndCircularReference.xml");
        Object A = context.getBean("A");
        Object B = context.getBean("B");
        System.out.println(A+"  A的B属性：" + ((BeanA)A).getB());
        System.out.println(B+"  B的A属性：" + ((BeanB)B).getA());
        Object C = context.getBean("C");
        System.out.println(C+"  C的C属性：" + ((BeanC)C).getC());
    }
}
