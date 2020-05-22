package com.nike.dnp.controller;

import com.nike.dnp.entity.Member;
import com.nike.dnp.service.MemberServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/memberTest")
public class MemberTestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MemberServiceImpl memberService;

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<Member>> getAllMembers() {
        List<Member> members = memberService.findAll();
        return new ResponseEntity<>(members, HttpStatus.OK);
    }

    // 회원번호로 한명의 회원 조회 
    @GetMapping(value = "/{seq}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Member> getMember(@PathVariable("seq") long seq) {
        Optional<Member> member = memberService.findById(seq);
        return new ResponseEntity<>(member.get(), HttpStatus.OK);
    }
    
    // 회원번호로 회원 삭제 
    @DeleteMapping(value = "/{seq}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Void> deleteMember(@PathVariable("seq") long seq) {
        memberService.deleteById(seq);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
    // 회원번호로 회원 수정(seq 회원을 찾아 Member 객체의 id, name로 수정함)
    @PutMapping(value = "/{seq}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Member> updateMember(@PathVariable("seq") long seq, Member member) {
        memberService.updateById(seq, member);
        return new ResponseEntity<>(member, HttpStatus.OK);
    }
    
    // 회원 입력 
    @PostMapping
    public ResponseEntity<Member> save(Member member) {
        return new ResponseEntity<>(memberService.save(member), HttpStatus.OK);
    }
    
    // 회원 입력 
    @GetMapping(value="/saveMember")
    public ResponseEntity<Member> save(HttpServletRequest req, Member member){
        return new ResponseEntity<>(memberService.save(member), HttpStatus.OK);
    }


}
