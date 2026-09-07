package springframework;

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

    public static class BeanWithWrapperType {
        private Integer number;
        public BeanWithWrapperType(Integer number) {
            this.number = number;
        }
        public BeanWithWrapperType() {
            System.out.println("Bean instance created.");
        }
        public int getNumber() {
            return number;
        }

        public void setNumber(Integer number) {
            this.number = number;
        }
    }

}
