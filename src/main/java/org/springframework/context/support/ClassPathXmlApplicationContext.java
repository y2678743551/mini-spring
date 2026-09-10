package org.springframework.context.support;

import org.springframework.beans.BeanException;

public class ClassPathXmlApplicationContext extends AbstractXmlApplicationContext {
    private String [] configLocations;

    public ClassPathXmlApplicationContext(String... configLocations) throws BeanException {
        this.configLocations = configLocations;
    }





    @Override
    protected String[] getConfigLocations() {
        return configLocations;
    }
}
