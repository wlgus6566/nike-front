package com.nike.dnp.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * BeanUtil
 *
 * @since 2020.05.21
 * @author [오지훈]
 * @Description BeanUtil 작성
 * @history [오지훈] [2020.05.21] [최초 작성]
 *
 */

@Component
public class BeanUtil implements ApplicationContextAware {

    private static ApplicationContext context;

    @Override
    public void setApplicationContext(final ApplicationContext context) {
        this.context = context;
    }

    /**
     * class타입으로 bean을 가져온다.
     * @param clazz
     * @return
     */
    public static <T> T getBean(final Class<T> clazz) {
        return context.getBean(clazz);
    }

    /**
     * 이름으로 bean을 가져온다.
     * @param beanName
     * @return
     */
    public static Object getBean(final String beanName) {
        return context.getBean(beanName);
    }

}
