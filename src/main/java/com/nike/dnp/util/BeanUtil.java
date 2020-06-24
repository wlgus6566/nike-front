package com.nike.dnp.util;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * BeanUtil
 *
 * @author [오지훈]
 * @CreatedOn 2020. 6. 24. 오후 6:01:19
 * @Description BeanUtil 작성
 */
@Component
@RequiredArgsConstructor
public class BeanUtil implements ApplicationContextAware {

    /**
     * The constant context
     *
     * @author [오지훈]
     */
    private static ApplicationContext context;

    /**
     * Sets application context.
     *
     * @param context the context
     * @author [오지훈]
     * @CreatedOn 2020. 6. 24. 오후 6:01:19
     * @Description
     */
    @Override
    public void setApplicationContext(final ApplicationContext context) {
        this.context = context;
    }

    /**
     * Gets bean.
     *
     * @param <T>   the type parameter
     * @param clazz the clazz
     * @return bean bean
     * @author [오지훈]
     * @CreatedOn 2020. 6. 24. 오후 6:01:19
     * @Description class타입으로 bean을 가져온다.
     */
    public static <T> T getBean(final Class<T> clazz) {
        return context.getBean(clazz);
    }

    /**
     * Gets bean.
     *
     * @param beanName the bean name
     * @return bean bean
     * @author [오지훈]
     * @CreatedOn 2020. 6. 24. 오후 6:01:19
     * @Description 이름으로 bean을 가져온다.
     */
    public static Object getBean(final String beanName) {
        return context.getBean(beanName);
    }

}
