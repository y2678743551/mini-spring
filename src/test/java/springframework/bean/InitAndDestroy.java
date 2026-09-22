package springframework.bean;

import org.springframework.beans.BeanException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.DisposableBean;
public class InitAndDestroy implements InitializingBean, DisposableBean {
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("afterPropertiesSet");
    }
    @Override
    public void destroy() throws BeanException {
        System.out.println("destroy");

    }
    public void init() {
        System.out.println("init-method");
    }
    public void des() {
        System.out.println("destroy-method");
    }


}
