package springframework.ioc;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Listener {
    @Test
    public void ListenerTest() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:Listener.xml");
        context.close();
    }
}
