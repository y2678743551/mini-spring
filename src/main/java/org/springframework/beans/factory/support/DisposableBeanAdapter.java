package org.springframework.beans.factory.support;

import org.springframework.beans.BeanException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.DisposableBean;

import java.lang.reflect.InvocationTargetException;

public class DisposableBeanAdapter  implements DisposableBean{
    Object bean;
    String beanName;
    String destroyMethodName;

    public DisposableBeanAdapter(Object bean, String beanName, String destroyMethodName) {
        this.bean = bean;
        this.beanName = beanName;
        this.destroyMethodName = destroyMethodName;
    }

    /**
     * 执行销毁方法
     * @throws BeanException
     */
    @Override
    public void destroy() throws BeanException {
        if(bean instanceof DisposableBean)
            ((DisposableBean) bean).destroy();
        if(destroyMethodName != null&&!(destroyMethodName.equals("destroy")&&bean instanceof DisposableBean)){
            try {
                bean.getClass().getMethod(destroyMethodName).invoke(bean);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new BeanException("执行销毁方法"+destroyMethodName+"失败",e);
            }catch (NoSuchMethodException e){
                throw new BeanException("销毁方法"+destroyMethodName+"不存在",e);
            }
        }
    }
}
