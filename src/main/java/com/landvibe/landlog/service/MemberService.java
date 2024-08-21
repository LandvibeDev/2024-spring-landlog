package com.landvibe.landlog.service;

import com.landvibe.landlog.domain.Member;
import com.landvibe.landlog.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Long join(Member member) {

        validateDuplicateMember(member);

        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        validateDuplicateMemberEmail(member);
        validateDuplicateMemberName(member);
    }

    private void validateDuplicateMemberName(Member member) {
        memberRepository.findByName(member.getName())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

    private void validateDuplicateMemberEmail(Member member) {
        memberRepository.findByEmail(member.getEmail())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 이메일입니다.");
                });
    }

    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }

    // 이메일과 비밀번호를 검증하여 회원을 찾는 메서드
    public Optional<Member> login(String email, String password) {
        Optional<Member> memberOpt = memberRepository.findByEmail(email);
        if (memberOpt.isEmpty()) {
            return Optional.empty(); // 이메일이 존재하지 않으면 빈 Optional 반환
        }

        Member member = memberOpt.get();
        // 입력한 비밀번호와 저장된 비밀번호 비교 (단순 문자열 비교)
        if (password.equals(member.getPassword())) {
            return Optional.of(member); // 비밀번호가 일치하면 회원 반환
        } else {
            return Optional.empty(); // 비밀번호가 일치하지 않으면 빈 Optional 반환
        }
    }
}
