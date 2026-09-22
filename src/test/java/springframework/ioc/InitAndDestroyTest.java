package springframework.ioc;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import springframework.bean.InitAndDestroy;

public class InitAndDestroyTest {
    @Test
    public void testInitAndDestroy() {
        AbstractApplicationContext context = new ClassPathXmlApplicationContext("classpath:InitAndDestroy.xml");

        InitAndDestroy bean = context.getBean("initAndDestroy", InitAndDestroy.class);
        context.close();
    }
}
