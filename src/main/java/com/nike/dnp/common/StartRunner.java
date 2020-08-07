package com.nike.dnp.common;

import com.nike.dnp.util.S3Util;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jets3t.service.CloudFrontServiceException;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.text.ParseException;

/**
 * Runner
 *
 * @author [오지훈]
 * @since 2020. 6. 22. 오후 5:05:10
 * @implNote Runner 작성
 */
@Slf4j
@Component
@NoArgsConstructor
public class StartRunner implements ApplicationRunner {

    /**
     * Run.
     *
     * @param args the args
     * @author [오지훈]
     * @since 2020. 6. 22. 오후 5:05:10
     * @implNote
     */
    @Override
    public void run(final ApplicationArguments args) throws CloudFrontServiceException, ParseException {
        log.info("Application Runner!!");

        //S3 init
        S3Util.init();
    }

}
