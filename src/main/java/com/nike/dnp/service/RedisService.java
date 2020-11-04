package com.nike.dnp.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * RedisUtil
 *
 * @author [오지훈]
 * @since 2020. 6. 19. 오후 4:47:26
 * @implNote RedisUtil 작성
 */
@Slf4j
@Service
public class RedisService {

    /**
     * RedisTemplate
     *
     * @author [오지훈]
     */
    private final transient RedisTemplate redisTemplate;

    /**
     * ValueOperations
     *
     * @author [오지훈]
     */
    private final transient ValueOperations valueOperations;

    /**
     * Instantiates a new Redis service.
     *
     * @param redisTemplate the redis template
     * @author [오지훈]
     * @since 2020. 6. 19. 오후 4:47:26
     * @implNote 생성자 주입
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
     * @since 2020. 6. 19. 오후 4:47:26
     * @implNote redis key 등록
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
     * @since 2020. 6. 19. 오후 4:47:26
     * @implNote redis key 조회
     */
    public Object get(final String key) {
        return valueOperations.get(key);
    }

    /**
     * redis key delete
     *
     * @param key 키
     * @author [오지훈]
     * @since 2020. 6. 19. 오후 4:47:26
     * @implNote redis key 삭제
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
     * @since 2020. 6. 19. 오후 4:47:26
     * @implNote redis key 배열 조회
     */
    public Set<String> keys(final String pattern) {
        return redisTemplate.keys(pattern);
    }

    /**
     * Scan set.
     *
     * @param pattern the pattern
     * @return the set
     * @author [오지훈]
     * @implNote redis key scan
     * @since 2020. 11. 4. 오전 11:59:22
     */
    public Set<String> scan(final String pattern) {
        final Set result = new HashSet<String>();
        final RedisConnection connection = Objects.requireNonNull(redisTemplate.getConnectionFactory()).getConnection();
        final ScanOptions options = ScanOptions.scanOptions().match(pattern).count(5).build();

        Cursor<byte[]> c = connection.scan(options);
        while (c.hasNext()) {
            result.add(new String(c.next()));
        }

        return result;
    }
}
