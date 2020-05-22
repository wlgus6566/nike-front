package com.nike.dnp.service;

public interface MemberService {

    /*@Autowired
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
    }*/

}
