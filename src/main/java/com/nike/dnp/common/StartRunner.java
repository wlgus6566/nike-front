package com.nike.dnp.common;

import com.nike.dnp.entity.Member;
import com.nike.dnp.service.MemberServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class StartRunner implements ApplicationRunner {

    @Autowired
    private MemberServiceImpl memberService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Member member = new Member();
        member.setId("killbe");
        member.setName("5j");
        member.setPassword("1234");
        member.setRole("ADMIN");
        memberService.save(member);

        Member member1 = new Member().builder()
                        .id("killbe1")
                        .name("5j1")
                        .build();
        memberService.save(member1);
    }

}
