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
        member.setEmail(form.getEmail()); //추가
        member.setPassword(form.getPassword());
        try {
            memberService.join(member);
        } catch (IllegalStateException e) {
            return "redirect:/"; //중복 이메일 있을시 회원 등록이 안됨.
        }

        return "redirect:/";
    }

    @GetMapping(value = "/members")
    public String list(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }
    //폼 데이터: 사용자가 HTML 폼을 통해 제출한 데이터, MemberForm은 사용자가 입력한 데이터를 담기위한 객체
    //모델: 컨트롤러에서 처리된 결과를 뷰에 넘겨서 표시하기 위해 사용(사용자목록)
    @PostMapping(value = "/members/login")
    public String loginPost(MemberForm form, Model model){
        String email = form.getEmail();
        String password = form.getPassword();
        Optional<Member> member = memberService.emailEqualPassword(email,password);
        if(member.isPresent()){ //비번일치-> 블로그페이지, 불일치->홈

                return "redirect:/blogs?creatorId="+member.get().getId();

        }
        else{
            return "redirect:/"; //로그인 실패
        }
    }
    @GetMapping(value = "/members/login")
    public String login(){
        return "members/loginForm";
    }
    @GetMapping(value = "/blogs")
    public String blog(@RequestParam("creatorId")Long Id,Model model){ //url에 적힌값 복사해서 변수에 넣기
        Optional<Member> member = memberService.findOne(Id);
        model.addAttribute("name",member.get().getName());
        return "blogList";
    }
}
