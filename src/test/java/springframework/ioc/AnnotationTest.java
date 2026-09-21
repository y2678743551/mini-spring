package springframework.ioc;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import springframework.bean.AnnotationTestBean;

public class AnnotationTest {
    @Test
    public void testAutowired() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:Annotation.xml");

        AnnotationTestBean bean = applicationContext.getBean("annotationTestBean", AnnotationTestBean.class);
        System.out.println(bean.getExampleBean().getName());
        System.out.println(bean.getName());
    }
}
