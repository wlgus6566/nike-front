package com.nike.dnp.util;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;

import java.util.Locale;
import java.util.Objects;

/**
 * MessageUtil Util
 *
 * @author [오지훈]
 * @since 2020. 7. 13. 오후 4:53:33
 * @implNote MessageUtil 작성
 */
@Slf4j
@UtilityClass
public class MessageUtil {

    /**
     * Gets message.
     *
     * @param enumCode the enum code
     * @return the message
     * @author [오지훈]
     * @since 2020. 7. 27. 오전 11:49:32
     * @implNote
     */
    public String getMessage(final String enumCode) {
        MessageSource messageSource = (MessageSource) BeanUtil.getBean("messageSource");
        return messageSource.getMessage(Objects.requireNonNull(enumCode), null, Locale.KOREA);
    }

}
