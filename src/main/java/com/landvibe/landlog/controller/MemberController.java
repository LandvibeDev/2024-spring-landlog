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
        String email = form.getEmail();
        String pwd = form.getPwd();
        Optional<Member> member = memberService.findByEmail(email);

        if (member.isPresent()) {
            model.addAttribute("member",member);
            return "redirect:/blogs?creatorId=" + member.get().getId();
        } else {
            return "redirect:/";
        }
    }

    @GetMapping(value = "/blogs")
    public String blog(@RequestParam(name = "creatorId") Long creatorId, Model model) {
        List<Member> member = memberService.findMembers();

        if (member != null) {
            model.addAttribute("member", member);
        }

        return "members/blogList";

    }

    @GetMapping(value = "/members")
    public String list(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }
}
