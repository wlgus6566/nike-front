package com.nike.dnp.dto.email;

import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * The Class Send dto.
 *
 * @author [오지훈]
 * @since 2020. 7. 3. 오전 11:22:37
 * @implNote
 */
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class SendDTO {

    /**
     * The Nickname
     *
     * @author [오지훈]
     */
    private String nickname = "";

    /**
     * The Email
     *
     * @author [오지훈]
     */
    private String email = "";

    /**
     * The Emails
     *
     * @author [오지훈]
     */
    private String[] emails;

    /**
     * The Cert code
     *
     * @author [오지훈]
     */
    private String certCode = "";

    /**
     * The Contents name
     *
     * @author [오지훈]
     */
    private String contentsName = "";

    /**
     * The Contents img.
     */
    private String contentsImg = "";

    /**
     * The Agency name
     *
     * @author [오지훈]
     */
    private String agencyName = "";

    /**
     * The Order comment
     *
     * @author [오지훈]
     */
    private String orderComment = "";

    /**
     * The Order area
     *
     * @author [오지훈]
     */
    private String orderArea = "";

    /**
     * The Order dt
     *
     * @author [오지훈]
     */
    private String orderDt = "";

    /**
     * The Processing dt
     *
     * @author [오지훈]
     */
    private String processingDt = "";

    /**
     * The Login url
     *
     * @author [오지훈]
     */
    private String loginUrl;

    /**
     * The Password url
     *
     * @author [오지훈]
     */
    private String passwordUrl;

    /**
     * The Contents url
     *
     * @author [오지훈]
     */
    private String contentsUrl;

    /**
     * The Product list
     *
     * @author [오지훈]
     */
    private List<OrderProductDTO> productList = new ArrayList<>();

    /**
     * Gets processing dt.
     *
     * @return the processing dt
     * @author [오지훈]
     * @since 2020. 7. 3. 오전 11:22:37
     * @implNote
     */
    public String getProcessingDt() {
        final LocalDate currentDate = LocalDate.now();
        return currentDate.getYear() + "년 " + currentDate.getMonthValue() + "월 " + currentDate.getDayOfMonth() + "일";
    }
}
