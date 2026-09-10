package springframework;

import org.junit.Test;
import org.springframework.core.io.DefaultResourceLoader;

public class ResourceTest {
    @Test
    public void use() {
        org.springframework.core.io.Resource resource = new DefaultResourceLoader().getResource("C:/Users/Administrator/Desktop/test.txt");
        System.out.println(resource.getClass());
        resource = new DefaultResourceLoader().getResource("http://www.baidu.com");
        System.out.println(resource.getClass());
        resource = new DefaultResourceLoader().getResource("classpath:test.txt");
        System.out.println(resource.getClass());
    }
}
