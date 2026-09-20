package springframework.bean;

import org.springframework.stereotype.Component;

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
