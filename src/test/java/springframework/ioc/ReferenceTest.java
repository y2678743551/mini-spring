package springframework.ioc;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import springframework.bean.ReferenceBean;

public class ReferenceTest {
    @Test
    public void testReference() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:ReferenceTest.xml");
        ReferenceBean bean = (ReferenceBean)context.getBean("referenceBean");
        System.out.println(bean);
        ReferenceBean.ReferencedBean referencedBean = bean.getReferencedBean();
        System.out.println(referencedBean.getName());
    }
}
