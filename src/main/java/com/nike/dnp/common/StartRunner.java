package com.nike.dnp.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * Runner
 *
 * @author [오지훈]
 * @Description Runner 작성
 * @history [오지훈] [2020.05.21] [최초 작성]
 * @since 2020.05.21
 */
@Slf4j
@Component
public class StartRunner implements ApplicationRunner {


    /**
     * Instantiates a new Start runner.
     * @author [오지훈]
     */
    public StartRunner() {
        super();
    }

    @Override
    public void run(final ApplicationArguments args) {
        log.info("Application Runner!!");
    }

}
