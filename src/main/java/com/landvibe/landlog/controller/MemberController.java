package com.landvibe.landlog.controller;

import com.landvibe.landlog.domain.Member;
import com.landvibe.landlog.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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

    @GetMapping(value ="/members/login")
    public String login(){
        return "members/loginForm";
    }

    @PostMapping(value = "/members/login")
    public String findBlog(@ModelAttribute MemberLoginForm memberLoginForm){
        Member member = new Member();
        member.setEmail(memberLoginForm.getEmail());
        member.setPassword(memberLoginForm.getPassword());
        Optional<Member> user =  memberService.checkLogin(member);
        if(user.isPresent()) {
            return "redirect:/blogs?creatorId="+user.get().getId();
        }
        return "redirect:/";
    }

    @GetMapping(value = "/members/new")
    public String createForm() {
        return "members/createMemberForm";
    }

    @PostMapping(value = "/members/new")
    public String create(@ModelAttribute MemberForm form) {
        Member member = new Member();
        member.setName(form.getName());
        member.setEmail(form.getEmail());
        member.setPassword(form.getPassword());
        memberService.join(member);
        return "redirect:/";
    }

    @GetMapping(value = "/members")
    public String list(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }
}
