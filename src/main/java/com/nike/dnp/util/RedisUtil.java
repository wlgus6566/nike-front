package com.nike.dnp.util;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * RedisUtil
 *
 * @since 2020.05.21
 * @author [오지훈]
 * @Description RedisUtil 작성
 * @history [오지훈] [2020.05.21] [최초 작성]
 * 
 */

@Component
public class RedisUtil {

    /**
     * redis key set
     *
     * @param key     - 키명
     * @param object  the object
     * @param timeout - 유지시간(분단위) - 0일 경우 무제한
     */
    public static void set(final String key, final Object object, final long timeout) {
        final RedisTemplate<String, Object> redisTemplate = (RedisTemplate<String, Object>) BeanUtil.getBean("redisTemplate");
        redisTemplate.opsForValue().set(key, object);
        if (timeout > 0) {
            redisTemplate.expire(key, timeout, TimeUnit.MINUTES);
        }
    }

    /**
     * redis key get
     * 
     * @param key
     * @return
     */
    public static Object get(final String key) {
        return ((RedisTemplate<String, Object>) BeanUtil.getBean("redisTemplate")).opsForValue().get(key);
    }

    /**
     * redis key delete
     * 
     * @param key
     */
    public static void delete(final String key) {
        ((RedisTemplate<String, Object>) BeanUtil.getBean("redisTemplate")).delete(key);
    }

    /**
     * redis key array return
     * @param pattern
     * @return string[]
     */
    public static Set<String> keys(final String pattern) {
        return ((RedisTemplate<String, Object>) BeanUtil.getBean("redisTemplate")).keys(pattern);
    }
}
