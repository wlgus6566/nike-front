package com.nike.dnp.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

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
@Service
public class RedisService {

    /**
     * RedisTemplate
     * @author [오지훈]
     */
    private final transient RedisTemplate redisTemplate;

    /**
     * ValueOperations
     * @author [오지훈]
     */
    private final transient ValueOperations valueOperations;

    /**
     * Instantiates a new Redis service.
     *
     * @param redisTemplate the redis template
     * @author [오지훈]
     */
    public RedisService(final RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.valueOperations = redisTemplate.opsForValue();
    }

    /**
     * redis key set
     *
     * @param key     - 키명
     * @param object  the object
     * @param timeout - 유지시간(분단위) - 0일 경우 무제한
     * @author [오지훈]
     */
    public void set(final String key, final Object object, final long timeout) {
        valueOperations.set(key, object);
        if (timeout > 0) {
            redisTemplate.expire(key, timeout, TimeUnit.MINUTES);
        }
    }

    /**
     * redis key get
     *
     * @param key 키
     * @return Object object
     * @author [오지훈]
     */
    public Object get(final String key) {
        return valueOperations.get(key);
    }

    /**
     * redis key delete
     *
     * @param key 키
     * @author [오지훈]
     */
    public void delete(final String key) {
        redisTemplate.delete(key);
    }

    /**
     * redis key array return
     *
     * @param pattern 패턴
     * @return string[] set
     * @author [오지훈]
     */
    public Set<String> keys(final String pattern) {
        return redisTemplate.keys(pattern);
    }

}
