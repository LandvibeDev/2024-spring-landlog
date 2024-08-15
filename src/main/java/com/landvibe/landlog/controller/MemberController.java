package com.landvibe.landlog.controller;

import com.landvibe.landlog.domain.Member;
import com.landvibe.landlog.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

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

    @GetMapping(value = "/members/login")
    public String login() {
        return "members/login";
    }

    @PostMapping(value = "/members/new")
    public String create(@ModelAttribute MemberForm form) {
        // 바인딩이 제대로 되었는지 확인
        // MemberForm에 @Setter 가 없으니 바인딩이 되지 않음 -> ??
        // @ModelAttribute 는 생략이 가능
        System.out.println("Form Data: " + form.getName() + ", " + form.getEmail() + ", " + form.getPassword());

        Member member = new Member();
        member.setName(form.getName());
        member.setEmail(form.getEmail());
        member.setPassword(form.getPassword());

        System.out.println("Member Data: " + member.getName() + ", " + member.getEmail() + ", " + member.getPassword());

        memberService.join(member);
        return "redirect:/";
    }

    @PostMapping(value = "/members/login")
    public String login(@ModelAttribute LoginForm form) {
        System.out.println("Form Data: " + form.getEmail() + ", " + form.getPassword());
        Member member = new Member();
        member.setEmail(form.getEmail());
        member.setPassword(form.getPassword());

        return "members/blogList";
    }


    @GetMapping(value = "/members")
    public String list(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }


}
