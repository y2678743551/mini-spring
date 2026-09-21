package springframework.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;

public class AnnotationTestBean {
    @Autowired
    @Qualifier("exampleBean2")
    private ExampleBean exampleBean;
    @Value("test")
    private String name;
    public ExampleBean getExampleBean() {
        return exampleBean;
    }
    public void setExampleBean(ExampleBean exampleBean) {
        this.exampleBean = exampleBean;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
