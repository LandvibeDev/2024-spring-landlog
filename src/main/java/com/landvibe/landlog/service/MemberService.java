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

    public Long join(Member member) { //새로운 회원 등록+ 중복검증
        validateDuplicateMember(member); //중복 회원 검증
        validateDuplicateEmail(member);//중복 이메일 검증
        memberRepository.save(member); //회원객체저장역할
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }
    private void validateDuplicateEmail(Member member) { //이메일 중복 검사 추가
        memberRepository.findByEmail(member.getEmail())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 이메일입니다.");
                });
    }
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Optional<Member> findByEmail(String email){return memberRepository.findByEmail(email);}
    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }
    //이메일로 사용자를 찾고 비밀번호가 일치한지 판단하는 로직은 비즈니스 로직이므로 Service에서 수행하도록 수정
    public Optional<Member> emailEqualPassword(String email, String password){
        Optional<Member> memberEmail = memberRepository.findByEmail(email);
        if(memberEmail.isPresent()){
            if(memberEmail.get().getPassword().equals(password)){
                return memberEmail;
            }
            else {
                return Optional.empty();
            }
        }
        else {
            return Optional.empty();
        }
    }
}
