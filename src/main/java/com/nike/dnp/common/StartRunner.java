package com.nike.dnp.common;

import com.nike.dnp.common.mail.MailService;
import com.nike.dnp.common.mail.SendEmailOffice365;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private SendEmailOffice365 sendEmailOffice365;

    @Autowired
    private MailService mailService;

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

        /*String id = "nikednp";
        String oldPassword = "qwe123QWE!@#";
        String[] password = {
                "12qQ%("
                ,"12345678"
                , "abcdefg"
                , "1234qwer"
                , "1q2w3e4r"
                , "!@#$qwer%T"
                , "qwe123QWE!@#"
                , "test91!!"
                , "test91!!mm"
                , "1n2i3k4e!!!"
                , "nike!!!123"
                , "nike!!!1234"
                , "nike!!!1111"
                , "nike!!!hhhh"
        };
        for (String tmp : password) {
            System.out.println(PatternUtil.pwdRegularExpressionChk(tmp, oldPassword, id));
        }
        */
        /*
        System.out.println(Expressions.dateTemplate(LocalDateTime.class, "{0}", "2020-06-23 00:00:00"));
        System.out.println(Expressions.dateTemplate(LocalDateTime.class, "{0}", "2020-06-23 23:59:59"));
        System.out.println(Expressions.dateTemplate(LocalDateTime.class, "DATE_FORMAT({0},{1})", "2020.06.30", ConstantImpl.create("%Y-%m-%d %H:%M:%s")));
        */

        //private final String from = "cokeplay.emotion@emotion.co.kr";
        //private final String to = "jihoon.oh@emotion.co.kr";
        //private String subject = "[NIKE D&P] Test";
        //private String messageContent = "[NIKE D&P] 발신 테스트 메일입니다.";

        //TODO[ojh] 오지훈테스트메일
        /*
        sendEmailOffice365.sendEmail("cokeplay.emotion@emotion.co.kr"
                ,"jihoon.oh@emotion.co.kr"
                , "[NIKE D&P] 발신 테스트 메일입니다.");
        sendEmailOffice365.sendEmail("cokeplay.emotion@emotion.co.kr"
                ,"nalchi91@daum.net"
                , "[NIKE D&P] 발신 테스트 메일입니다.");
        sendEmailOffice365.sendEmail("cokeplay.emotion@emotion.co.kr"
                ,"lion226@naver.com"
                , "[NIKE D&P] 발신 테스트 메일입니다.");
        sendEmailOffice365.sendEmail("cokeplay.emotion@emotion.co.kr"
                ,"nalch91@gmail.com"
                , "[NIKE D&P] 발신 테스트 메일입니다.");
        */

        //TODO[ojh] 지은유테스트메일
        /*
        sendEmailOffice365.sendEmail("cokeplay.emotion@emotion.co.kr"
                ,"rabygun@naver.com"
                , "[NIKE D&P] 발신 테스트 메일입니다.");
        sendEmailOffice365.sendEmail("cokeplay.emotion@emotion.co.kr"
                ,"seonyeaji@gmial.com"
                , "[NIKE D&P] 발신 테스트 메일입니다.");
        */

        //TODO[ojh] [계정 생성 안내] 이메일 발송
        /*
        SendDTO sendDTO = new SendDTO();
        sendDTO.setNickname("ojh");
        sendDTO.setEmail("lion226@naver.com");
        mailService.sendMail(
                ServiceEnumCode.EmailType.USER_CREATE.toString()
                ,ServiceEnumCode.EmailType.USER_CREATE.getMessage()
                ,sendDTO
        );
        */

        //TODO[ojh] [주문서] 이메일 발송
        /*
        SendDTO sendDTO1 = new SendDTO();
        sendDTO1.setNickname("NIKE 홍대점");
        sendDTO1.setEmail("lion226@naver.com");
        sendDTO1.setOrderDt("2020.06.29 12:12:12");
        sendDTO1.setAgencyName("이모션");
        sendDTO1.setOrderComment("여기로 이렇게 보내주세용");
        List<OrderProductDTO> productList = new ArrayList<>();
        OrderProductDTO orderProductDTO = new OrderProductDTO();
        orderProductDTO.setProductName("조던 255");
        orderProductDTO.setProductDesc("내가 다 갖고싶어요...");
        orderProductDTO.setAmount(100000000);
        productList.add(orderProductDTO);
        OrderProductDTO orderProductDTO1 = new OrderProductDTO();
        orderProductDTO1.setProductName("나이키 헤어밴드");
        orderProductDTO1.setProductDesc("겐쥐템");
        orderProductDTO1.setAmount(10000);
        productList.add(orderProductDTO1);
        sendDTO1.setProductList(productList);
        DecimalFormat format = new DecimalFormat("###,###");
        StringBuilder sb = new StringBuilder();
        for (OrderProductDTO dto : sendDTO1.getProductList()) {
            sb.append("<tr style=\"border-top:1px solid #ddd\">");
            sb.append("    <td style=\"padding:15px; border-right:1px solid #ddd;\">");
            sb.append("        <p style=\"margin:0\">"+dto.getProductName()+"</p>");
            sb.append("        <p style=\"margin:2px 0 0 0; font-size:12px; color:#888;\">"+dto.getProductDesc()+"</p>");
            sb.append("    </td>");
            sb.append("    <td align=\"center\">");
            sb.append("        <p>"+ format.format(dto.getAmount())+"개</p>");
            sb.append("    </td>");
            sb.append("</tr>");
        }
        sendDTO1.setOrderArea(sb.toString());
        mailService.sendMail(
                ServiceEnumCode.EmailTypeEnumCode.ORDER.toString()
                ,ServiceEnumCode.EmailTypeEnumCode.ORDER.getMessage()
                ,sendDTO1
        );
        */
    }

}
