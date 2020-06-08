package com.nike.dnp.util;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * RedisUtil
 *
 * @author [오지훈]
 * @Description RedisUtil 작성
 * @history [오지훈] [2020.05.21] [최초 작성]
 * @since 2020.05.21
 */
@Component
public class RedisUtil {

    /**
     * The Redis template.
     */
    public static RedisTemplate<String, Object> redisTemplate;

    /**
     * Instantiates a new Redis util.
     */
    public RedisUtil() {
        redisTemplate = (RedisTemplate<String, Object>) BeanUtil.getBean("redisTemplate");
    }

    /**
     * redis key set
     *
     * @param key     - 키명
     * @param object  the object
     * @param timeout - 유지시간(분단위) - 0일 경우 무제한
     */
    public static void set(final String key, final String object, final long timeout) {
        redisTemplate.opsForValue().set(key, object);
        if (timeout > 0) {
            redisTemplate.expire(key, timeout, TimeUnit.MINUTES);
        }
    }

    /**
     * redis key get
     *
     * @param key 키
     * @return Object object
     */
    public static Object get(final String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * redis key delete
     *
     * @param key 키
     */
    public static void delete(final String key) {
        redisTemplate.delete(key);
    }

    /**
     * redis key array return
     *
     * @param pattern 패턴
     * @return string[] set
     */
    public static Set<String> keys(final String pattern) {
        return redisTemplate.keys(pattern);
    }
}
