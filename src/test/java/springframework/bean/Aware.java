package springframework.bean;

import org.springframework.beans.BeanException;
import org.springframework.beans.BeanFactoryAware;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class Aware implements BeanFactoryAware, ApplicationContextAware {
    private BeanFactory beanFactory;
    private ApplicationContext applicationContext;
    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeanException {
        this.beanFactory = beanFactory;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeanException {
        this.applicationContext = applicationContext;
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public BeanFactory getBeanFactory() {
        return beanFactory;
    }
}
