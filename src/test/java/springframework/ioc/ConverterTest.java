package springframework.ioc;

import org.dom4j.DocumentException;
import org.junit.Test;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;

import java.io.IOException;

public class ConverterTest {

    @Test
    public void testConvert() throws DocumentException, IOException {
        DefaultListableBeanFactory defaultListableBeanFactory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(defaultListableBeanFactory);
        reader.loadBeanDefinitions("classpath:bean.xml");
        System.out.println(defaultListableBeanFactory.getBean("BeanWithWrapperType"));

    }



}
