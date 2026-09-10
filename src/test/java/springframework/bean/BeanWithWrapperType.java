package springframework.bean;
public class BeanWithWrapperType {
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
