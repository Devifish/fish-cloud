package cn.devifish.cloud.common.core.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * SpringUtils
 * Spring工具类
 *
 * @author Devifish
 * @date 2020/7/21 16:10
 */
@Component
public class SpringUtils implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringUtils.applicationContext = applicationContext;
    }

    /**
     * 获取SpringApplicationContext
     *
     * @return ApplicationContext
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 通过 Bean name 获取Bean
     *
     * @param beanName bean name
     * @param <T> type
     * @return bean
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String beanName) {
        return (T) applicationContext.getBean(beanName);
    }

    /**
     * 通过Bean Class 获取Bean
     *
     * @param beanType class
     * @param <T> type
     * @return bean
     */
    public static <T> T getBean(Class<T> beanType) {
        return applicationContext.getBean(beanType);
    }

}
