package com.nike.dnp.common.viriable;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Redis
 *
 * @since 2020.02.20
 * @author [오지훈]
 * @Description RedisVariable 작성
 * @history [오지훈] [2020.02.20] [최초 작성]
 *
 */
@Slf4j
@Component("Redis")
public class Redis {

    public Redis() {
        super();
        log.info("Redis Variable Loading");
    }

    /**
     * The constant GLOBAL_REDIS_HOST.
     */
    public static String REDIS_HOST;

    /**
     * The constant GLOBAL_REDIS_PORT.
     */
    public static int REDIS_PORT;

    /**
     * Sets redis host.
     *
     * @param redisHost the redis host
     */
    @Value("${spring.redis.host:}")
    public void setRedisHost(String redisHost) {
        this.REDIS_HOST = redisHost;
    }

    /**
     * Sets redis port.
     *
     * @param redisPort the redis port
     */
    @Value("${spring.redis.port:}")
    public void setRedisPort(int redisPort) {
        this.REDIS_PORT = redisPort;
    }

}
