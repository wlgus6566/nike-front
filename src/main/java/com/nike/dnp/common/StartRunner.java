package com.nike.dnp.common;

import com.nike.dnp.service.RedisService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * The type Start runner.
 */
@Component
public class StartRunner implements ApplicationRunner {

    /**
     *
     */
    private final transient RedisService redisService;

    /**
     * Instantiates a new Start runner.
     *
     * @param redisService the redis service
     */
    public StartRunner(final RedisService redisService) {
        this.redisService = redisService;
    }

    @Override
    //@VisibleForTesting
    public void run(final ApplicationArguments args) {
    }

}
