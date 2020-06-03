package com.nike.dnp.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;

/**
 * GlobalVariable
 *
 * @since 2020.02.20
 * @author [오지훈]
 * @Description GlobalVariable 작성
 * @history [오지훈] [2020.02.20] [최초 작성]
 *
 */
@Slf4j
@Component("GlobalVariable")
public class GlobalVariable {
    // Redis 관련
    public static String GLOBAL_REDIS_HOST;
    public static int GLOBAL_REDIS_PORT;

    private final ServletContext servletContext;
    public GlobalVariable(ServletContext servletContext) {
        log.info("Component GlobalVariable Scan Complete.");
        this.servletContext = servletContext;
    }

    @Value("${spring.redis.host:}")
    public void setREDIS_HOST(String REDIS_HOST) {
        this.GLOBAL_REDIS_HOST = REDIS_HOST;
    }

    @Value("${spring.redis.port:}")
    public void setREDIS_PORT(int REDIS_PORT) {
        this.GLOBAL_REDIS_PORT = REDIS_PORT;
    }

}
