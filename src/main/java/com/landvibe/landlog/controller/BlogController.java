package com.landvibe.landlog.controller;


import com.landvibe.landlog.domain.Blog;
import com.landvibe.landlog.domain.Member;
import com.landvibe.landlog.repository.BlogRepository;
import com.landvibe.landlog.repository.MemberRepository;
import com.landvibe.landlog.service.BlogService;
import com.landvibe.landlog.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
public class BlogController {
    private final MemberService memberService;
    private final BlogService blogService;

    public BlogController(MemberService memberService, BlogService blogService){
        this.memberService = memberService;
        this.blogService = blogService;
    }

    @GetMapping("/blogs/new")
    public String createBlog(@RequestParam("creatorId") Long creatorId, Model model) {
        Optional<Member> user = memberService.findOne(creatorId);
        if(!user.isPresent()){
            throw new IllegalArgumentException("존재하지 않는 회원입니다.");
        }
        model.addAttribute("name", user.get().getName());
        model.addAttribute("creatorId", user.get().getId());
        return "blogs/createBlogForm";
    }

    @PostMapping("/blogs/new")
    public String RegisterBlog(@RequestParam("creatorId") Long creatorId, Blog form){
        Blog blog = new Blog();
        blog.setCreatorId(creatorId);
        blog.setTitle(form.getTitle());
        blog.setContents(form.getContents());
        blogService.join(blog);
        return "redirect:/blogs?creatorId=" + creatorId;
    }

    @GetMapping(value="/blogs")
    public String blogs(@RequestParam("creatorId") Long creatorId, Model model){
        Optional<Member> user = memberService.findOne(creatorId);
        if(!user.isPresent()){
            throw new IllegalArgumentException("존재하지 않는 회원입니다.");
        }
        model.addAttribute("name", user.get().getName());
        model.addAttribute("creatorId", creatorId);
        List<Blog> blogs = blogService.findCreatorBlogs(creatorId);
        model.addAttribute("blogs", blogs);
        return "blogs/blogList";
    }

    @GetMapping("/blogs/update")
    public String findUpdateBlog(@RequestParam("blogId") Long blogId, @RequestParam("creatorId") Long creatorId, Model model){
        Optional<Member> user = memberService.findOne(creatorId);
        if(!user.isPresent()){
            throw new IllegalArgumentException("존재하지 않는 회원입니다.");
        }
        List<Blog> blogs = blogService.findCreatorBlogs(creatorId);
        Optional<Blog> blog = blogService.findOne(blogs, blogId);

        model.addAttribute("blog", blog.get());
        model.addAttribute("creatorId", user.get().getId());
        return "blogs/updateBlogForm";
    }

    @PostMapping("/blogs/update")
    public String updateBlog(@RequestParam("creatorId") Long creatorId, Blog blog){
        blogService.update(creatorId, blog.getId(), blog);
        return "redirect:/blogs?creatorId=" + creatorId;
    }

    @PostMapping("/blogs/delete")
    public String deleteBlogFallback(@RequestParam("blogId") Long blogId, @RequestParam("creatorId") Long creatorId) {
        Optional<Member> user = memberService.findOne(creatorId);
        if(!user.isPresent()){
            throw new IllegalArgumentException("존재하지 않는 회원입니다.");
        }
        blogService.delete(creatorId, blogId);
        return "redirect:/blogs?creatorId=" + creatorId;
    }
}
