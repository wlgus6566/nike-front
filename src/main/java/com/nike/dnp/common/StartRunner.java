package com.nike.dnp.common;

import com.nike.dnp.util.RedisUtil;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * The type Start runner.
 */
@Component
public class StartRunner implements ApplicationRunner {

    @Override
    public void run(final ApplicationArguments args) {
        RedisUtil.set("test", "12312312313", 10);
    }

}
