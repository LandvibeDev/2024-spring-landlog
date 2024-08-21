package com.landvibe.landlog.service;

import com.landvibe.landlog.domain.Member;
import com.landvibe.landlog.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MemberServiceTest {

    MemberService memberService;
    MemoryMemberRepository memberRepository;

    @BeforeEach
    public void beforeEach() {
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }

    @AfterEach
    public void afterEach() {
        memberRepository.clearStore();
    }

    @Test
    public void 회원가입() throws Exception {
        //Given
        Member member = new Member();
        member.setName("hello");
        member.setEmail("hello@gmail.com");
        member.setPassword("12345");
        //When
        Long saveId = memberService.join(member);
        //Then
        Member findMember1 = memberRepository.findById(saveId).get();
        assertEquals(member.getName(), findMember1.getName());
    }
    @Test
    public void 중복_회원_예외() throws Exception {
        //Given
        Member member1 = new Member();
        member1.setName("spring");
        member1.setEmail("abc123@gmail.com");
        member1.setPassword("12345");
        Member member2 = new Member();
        member2.setName("spring");
        member2.setEmail("abc123@naver.com");
        member2.setPassword("12345");
        //When
        memberService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class,
                () -> memberService.join(member2));//예외가 발생해야 한다.
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
    }

    @Test
    public void 중복_이메일_예외() throws Exception{
        //Given
        Member member1 = new Member();
        member1.setName("spring1");
        member1.setEmail("abc123@gmail.com");
        member1.setPassword("12345");
        Member member2 = new Member();
        member1.setName("spring2");
        member2.setEmail("abc123@gmail.com");
        member2.setPassword("12345");
        //When
        memberService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class,
                () -> memberService.join(member2)); //예외가 발생해야 한다.
        assertThat(e.getMessage()).isEqualTo("이미 사용되고 있는 이메일입니다.");
    }
}