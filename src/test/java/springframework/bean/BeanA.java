package springframework.bean;

import org.springframework.context.annotation.Component;

@Component
public class BeanA {
    private BeanB b;
    public void setB(BeanB b) {
        this.b = b;
    }

    public BeanB getB() {
        return b;
    }
}
