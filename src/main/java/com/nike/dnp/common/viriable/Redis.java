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
    public static String GLOBAL_REDIS_HOST;

    /**
     * The constant GLOBAL_REDIS_PORT.
     */
    public static int GLOBAL_REDIS_PORT;

    /**
     * Sets redis host.
     *
     * @param REDIS_HOST the redis host
     */
    @Value("${spring.redis.host:}")
    public void setREDIS_HOST(String REDIS_HOST) {
        this.GLOBAL_REDIS_HOST = REDIS_HOST;
    }

    /**
     * Sets redis port.
     *
     * @param REDIS_PORT the redis port
     */
    @Value("${spring.redis.port:}")
    public void setREDIS_PORT(int REDIS_PORT) {
        this.GLOBAL_REDIS_PORT = REDIS_PORT;
    }

}
