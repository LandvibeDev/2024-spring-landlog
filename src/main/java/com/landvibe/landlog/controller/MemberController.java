package com.landvibe.landlog.controller;

import com.landvibe.landlog.domain.Member;
import com.landvibe.landlog.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
public class MemberController {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping(value = "/members/new")
    public String createForm() {
        return "members/createMemberForm";
    } //회원가입 폼 반환

    @PostMapping(value = "/members/new")
    public String create(MemberForm form) { //새로운 회원생성, 회원가입 후 메인 페이지로 리다이렉트
        Member member = new Member();
        member.setName(form.getName());
        member.setEmail(form.getEmail());
        member.setPwd(form.getPwd());
        memberService.join(member);
        return "redirect:/";
    }

    @GetMapping(value = "/members")
    public String list(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }

    @GetMapping(value = "/members/login")
    public String loginForm() {
        return "members/loginForm";
    } //로그인 폼 반환

    //로그인 폼 요청처리
    @PostMapping(value = "/members/login")
    public String login(@RequestParam String email, @RequestParam String pwd, Model model) {
        try {
            Member member = memberService.checkLogin(email, pwd); //로그인 성공여부 판단
            model.addAttribute("member", member);
            Long creatorId = member.getId();
            return "redirect:/blogs?creatorId=" + creatorId;
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "redirect:/";
        }
    }

    @GetMapping(value = "/blogs")
    public String blogForm(@RequestParam Long creatorId, Model model) {
        Optional<Member> member = memberService.findOne(creatorId);
        model.addAttribute("creatorId", creatorId);
        Member member1 = member.get(); //'Member' 객체를 가져옴
        model.addAttribute("name", member1.getName()); //여기서 넘겨줘야 blogList.html에서 사용가능
        return "blogList";
    }

}
