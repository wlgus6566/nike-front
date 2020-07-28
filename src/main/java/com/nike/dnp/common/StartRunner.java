package com.nike.dnp.common;

import com.nike.dnp.util.S3Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * Runner
 *
 * @author [오지훈]
 * @CreatedOn 2020. 6. 22. 오후 5:05:10
 * @Description Runner 작성
 */
@Slf4j
@Component
public class StartRunner implements ApplicationRunner {


    /**
     * Instantiates a new Start runner.
     *
     * @author [오지훈]
     * @CreatedOn 2020. 6. 22. 오후 5:05:10
     * @Description
     */
    public StartRunner() {
        super();
    }

    /**
     * Run.
     *
     * @param args the args
     * @author [오지훈]
     * @CreatedOn 2020. 6. 22. 오후 5:05:10
     * @Description
     */
    @Override
    public void run(final ApplicationArguments args) {
        log.info("Application Runner!!");

        //S3 init
        S3Util.init();
    }

}
