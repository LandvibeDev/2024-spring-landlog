package com.landvibe.landlog.controller;

import com.landvibe.landlog.domain.Member;
import com.landvibe.landlog.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
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

    @GetMapping(value = "/members/login")
    public String login() {
        return "members/login";
    }

    @PostMapping(value = "/members/new")
    public String create(@ModelAttribute MemberForm form) {
        // 멤버 폼 바인딩이 제대로 되었는지 확인
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




    @GetMapping(value = "/members")
    public String list(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }

    @PostMapping("/members/login")
    public String login(@ModelAttribute LoginForm form, RedirectAttributes redirectAttributes) {

        // 로그인 폼 바인딩 확인
        System.out.println("Login Form Data : " + form.getEmail() + " " + form.getPassword());


        Optional<Member> memberOpt = memberService.login(form.getEmail(), form.getPassword());

        if (memberOpt.isPresent()) {
            // 로그인 성공 시 /blogs 페이지로 리다이렉트
            Member member = memberOpt.get();
            String encodedName;
            try {
                encodedName = URLEncoder.encode(member.getName(), StandardCharsets.UTF_8.toString());
            } catch (UnsupportedEncodingException e) {
                encodedName = ""; // 기본 값 처리
            }
            return "redirect:/blogs?name=" + encodedName;
        } else {
            redirectAttributes.addFlashAttribute("error", "로그인 실패! 이메일 또는 비밀번호를 확인하세요.");
            return "redirect:/";
        }
    }
    @GetMapping("/blogs")
    public String blogList(@RequestParam(name = "name", required = false) String name, Model model) {
        model.addAttribute("name", name);
        return "members/blogList"; // 템플릿 파일의 이름
    }
}
