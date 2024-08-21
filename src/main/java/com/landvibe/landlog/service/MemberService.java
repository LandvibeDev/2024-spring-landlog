package com.landvibe.landlog.service;

import com.landvibe.landlog.domain.Member;
import com.landvibe.landlog.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Long join(Member member) {
        validateDuplicateMember(member); //중복 회원 검증
        validateDuplicateEmail(member); //중복 이메일 검증
        memberRepository.save(member);
        return member.getId();
    }

    public Optional<Member> checkLogin(Member member){
        Optional<Member> user = findOne(member.getEmail());
        if(user.isPresent()){
            if(user.get().getPassword().equals(member.getPassword())){
                return user;
            }else {
                return Optional.empty();
            }
        }
        else {
            return Optional.empty();
        }
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

    private void validateDuplicateEmail(Member member) {
        memberRepository.findByEmail(member.getEmail())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 사용되고 있는 이메일입니다.");
                });
    }

    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }

    public Optional<Member> findOne(String email){
        return memberRepository.findByEmail(email);
    }

}
