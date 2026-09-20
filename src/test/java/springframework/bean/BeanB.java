package springframework.bean;

public class BeanB {
    private BeanA a;
    public void setA(BeanA a) {
        this.a = a;
    }
    public BeanA getA() {
        return a;
    }
}
