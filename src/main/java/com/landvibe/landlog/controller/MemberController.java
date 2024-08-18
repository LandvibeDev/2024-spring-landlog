package com.landvibe.landlog.controller;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
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
    }

    @PostMapping(value = "/members/new")
    public String create(MemberForm form) {
        Member member = new Member();
        member.setName(form.getName());
        member.setEmail(form.getEmail());
        member.setPwd(form.getPwd());
        memberService.join(member);
        return "redirect:/";
    }
    @GetMapping(value = "/members/login")
    public String loginForm() {
        return "members/loginForm";
    }

    @PostMapping(value = "/members/login")
    public String login(MemberForm form, Model model) {
        // 실제 로그인 로직 구현
        String email =form.getEmail();
        String pwd = form.getPwd();
        Optional<Member> member = memberService.findByEmail(email);

        if (member.isPresent()) {
            // 로그인 성공 시 해당 멤버 정보를 Model에 추가하고 blogs 페이지로 리다이렉트
            model.addAttribute("member", member);
            return "redirect:/members/blogs?creatorId=" +member.get().getId();
        } else {
            // 로그인 실패 시 홈 화면으로 리다이렉트
            return "redirect:/";
        }
    }


    @GetMapping(value = "/members/blogs")
    public String blog(@RequestParam(name = "creatorId") Long creatorId, Model model) {
        Optional<Member> member = memberService.findById(creatorId);
        model.addAttribute("name", member.get().getName());
        return "members/blogList";
    }


    @GetMapping(value = "/members")
    public String list(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }
}
