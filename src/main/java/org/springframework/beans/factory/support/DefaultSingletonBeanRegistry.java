package org.springframework.beans.factory.support;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.SingletonBeanRegistry;

import java.util.HashMap;
import java.util.Map;
/*
* @date:2026/9/8
* */
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {
    //1级缓存
    private final Map<String, Object> singletonObjects = new HashMap<>();
    //2级缓存
    private final Map<String, Object> earlySingletonObjects = new HashMap<>();
    //3级缓存
    private final Map<String, ObjectFactory<?>> singletonFactories = new HashMap<>();
    //可销毁的Bean
    private final Map<String, DisposableBean> disposableBeans = new HashMap<>();

    /**
     * 获取单例Bean
     * @param beanName bean名称
     * @return bean对象
     */
    @Override
    public Object getSingleton(String beanName) {
        Object bean = singletonObjects.get(beanName);
        if (bean==null)
        {
            bean = earlySingletonObjects.get(beanName);
            if (bean==null)
            {
                ObjectFactory<?> objectFactory = singletonFactories.get(beanName);
                if(objectFactory!=null){
                    bean = objectFactory.getObject();
                    earlySingletonObjects.put(beanName, bean);
                }
            }
        }
        return bean;
    }

    /**
     * 注册单例Bean
     * @param beanName bean名称
     * @param singletonObject bean对象
     */
    @Override
    public void registerSingleton(String beanName, Object singletonObject) {
        if(!singletonObjects.containsKey(beanName))
            singletonObjects.put(beanName, singletonObject);
        earlySingletonObjects.remove(beanName);
        singletonFactories.remove(beanName);
    }

    /**
     * 注册单例Bean工厂
     * @param beanName bean名称
     * @param singletonFactory bean工厂
     */
    public void registerSingletonFactory(String beanName, ObjectFactory<?> singletonFactory) {
        if(!singletonFactories.containsKey(beanName)&&!singletonObjects.containsKey(beanName)&&!earlySingletonObjects.containsKey(beanName))
            singletonFactories.put(beanName, singletonFactory);

    }

    /**
     * 注册可销毁的Bean
     * @param beanName bean名称
     * @param bean bean对象
     */
    public void registerDisposableBean(String beanName, DisposableBean bean) {
        if(!disposableBeans.containsKey(beanName))
            disposableBeans.put(beanName, bean);

    }

    /**
     * 判断是否包含指定名称的单例Bean
      * @param beanName bean名称
     * @return 是否包含指定名称的单例Bean
     */
    @Override
    public boolean containsSingleton(String beanName) {
        return getSingleton(beanName) != null;
    }

    /**
     * 销毁所有可销毁的Bean
     */
    @Override
    public void destroySingletons() {
        synchronized (disposableBeans){
            disposableBeans.forEach((beanName, bean) -> {
                try {
                    bean.destroy();
                } catch (Exception e) {
                    System.err.println("Destroy method on bean with name '" + beanName + "' threw an exception");

                }
            });
            disposableBeans.clear();
        }
    }

}
