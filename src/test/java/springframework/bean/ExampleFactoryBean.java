package springframework.bean;

import org.springframework.beans.factory.FactoryBean;
public class ExampleFactoryBean implements FactoryBean<ExampleBean> {
    @Override
    public ExampleBean getObject() throws Exception {
        return new ExampleBean();
    }

    public Class<?> getObjectType() {
        return ExampleBean.class;
    }
    @Override
    public boolean isSingleton() {
        return true;
    }
}
