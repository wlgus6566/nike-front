package com.nike.dnp.common;

import com.querydsl.core.types.ConstantImpl;
import com.querydsl.core.types.dsl.Expressions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

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

        System.out.println(Expressions.dateTemplate(LocalDateTime.class, "{0}", "2020-06-23 00:00:00"));
        System.out.println(Expressions.dateTemplate(LocalDateTime.class, "{0}", "2020-06-23 23:59:59"));
        System.out.println(Expressions.dateTemplate(LocalDateTime.class, "DATE_FORMAT({0},{1})", "2020.06.30", ConstantImpl.create("%Y-%m-%d %H:%M:%s")));
        System.out.println("=========================================================");


    }

}
