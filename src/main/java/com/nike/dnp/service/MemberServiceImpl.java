package com.nike.dnp.service;

import com.nike.dnp.entity.Member;
import com.nike.dnp.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MemberServiceImpl implements MemberService/*, UserDetailsService*/ {

    @Autowired
    private MemberRepository memberRepository;

    public List<Member> findAll() {
        List<Member> members = new ArrayList<>();
        memberRepository.findAll().forEach(e -> members.add(e));
        return members;
    }

    public Optional<Member> findById(long seq) {
        Optional<Member> member = memberRepository.findById(seq);
        return member;
    }

    public void deleteById(long seq) {
        memberRepository.deleteById(seq);
    }

    public Member save(Member member) {
        memberRepository.save(member);
        return member;
    }

    public void updateById(long seq, Member member) {
        Optional<Member> e = memberRepository.findById(seq);

        if (e.isPresent()) {
            e.get().setSeq(seq);
            e.get().setId(member.getId());
            e.get().setName(member.getName());
            memberRepository.save(member);
        }
    }

    /*@Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        Member member = memberRepository.getMember(id);
        List<GrantedAuthority> authorityList = new ArrayList<>();

        if (Role.ADMIN.getKey().equals(member.getRole())) {
            authorityList.add(new SimpleGrantedAuthority(Role.ADMIN.getKey()));
        } else {
            authorityList.add(new SimpleGrantedAuthority(Role.USER.getKey()));
        }
        return Login.of(member, authorityList);
    }*/

}
