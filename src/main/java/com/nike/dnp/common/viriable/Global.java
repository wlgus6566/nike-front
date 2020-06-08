package com.nike.dnp.common.viriable;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Global
 *
 * @since 2020.02.20
 * @author [오지훈]
 * @Description GlobalVariable 작성
 * @history [오지훈] [2020.02.20] [최초 작성]
 *
 */
@Slf4j
@Component("Global")
public class Global {

    /**
     * Instantiates a new Global.
     *
     * @author [오지훈]
     */
    public Global() {
        super();
        log.info("Global Variable Loading");
    }

}
