package com.landvibe.landlog.controller;

import com.landvibe.landlog.domain.Blog;
import com.landvibe.landlog.dto.AddBlogRequest;
import com.landvibe.landlog.dto.BlogForm;
import com.landvibe.landlog.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class blogController {

    private final BlogService blogService;

    @GetMapping("/blogs")
    public String blogList(@RequestParam(name = "name", required = false) String name, Model model) {
        model.addAttribute("name", name);
        return "blogs/blogList"; // 템플릿 파일의 이름
    }
}
