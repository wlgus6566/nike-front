package com.nike.dnp.dto.email;

import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * The Class Send dto.
 *
 * @author [오지훈]
 * @CreatedOn 2020. 7. 3. 오전 11:22:37
 * @Description
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
    //TODO[ojh] 2020-07-02 : 변경예정
    private String loginUrl = "";

    /**
     * The Password url
     *
     * @author [오지훈]
     */
    //TODO[ojh] 2020-07-02 : 변경예정
    private String passwordUrl = "";

    /**
     * The Contents url
     *
     * @author [오지훈]
     */
    //TODO[ojh] 2020-07-02 : 변경예정
    private String contentsUrl = "";

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
     * @CreatedOn 2020. 7. 3. 오전 11:22:37
     * @Description
     */
    public String getProcessingDt() {
        LocalDate currentDate = LocalDate.now();
        return currentDate.getYear() + "년 " + currentDate.getMonthValue() + "월 " + currentDate.getDayOfMonth() + "일";
    }
}
