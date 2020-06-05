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
//@UtilityClass
public class RedisUtil {

    /**
     * redis key set
     *
     * @param key     - 키명
     * @param object  the object
     * @param timeout - 유지시간(분단위) - 0일 경우 무제한
     */
    public static void set(String key, Object object, long timeout) {
        RedisTemplate<String, Object> redisTemplate = (RedisTemplate<String, Object>) BeanUtil.getBean("redisTemplate");
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
    public static Object get(String key) {
        RedisTemplate<String, Object> redisTemplate = (RedisTemplate<String, Object>) BeanUtil.getBean("redisTemplate");
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * redis key delete
     * 
     * @param key
     */
    public static void delete(String key) {
        RedisTemplate<String, Object> redisTemplate = (RedisTemplate<String, Object>) BeanUtil.getBean("redisTemplate");
        redisTemplate.delete(key);
    }

    /**
     * redis key array return
     * @param pattern
     * @return string[]
     */
    public static Set<String> keys(String pattern) {
        RedisTemplate<String, Object> redisTemplate = (RedisTemplate<String, Object>) BeanUtil.getBean("redisTemplate");
        return redisTemplate.keys(pattern);
    }
}
