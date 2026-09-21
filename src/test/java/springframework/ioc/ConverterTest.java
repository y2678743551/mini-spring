package springframework.ioc;

import org.dom4j.DocumentException;
import org.junit.Test;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import springframework.bean.BeanWithWrapperType;

import java.io.IOException;

public class ConverterTest {

    @Test
    public void testConvert() throws DocumentException, IOException {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:Converter.xml");
        System.out.println(((BeanWithWrapperType)context.getBean("BeanWithWrapperType")).getNumber());

    }



}
