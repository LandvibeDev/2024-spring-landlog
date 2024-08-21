package com.landvibe.landlog.service;

import com.landvibe.landlog.domain.Blog;
import com.landvibe.landlog.dto.AddBlogRequest;
import com.landvibe.landlog.dto.UpdateBlogRequest;
import com.landvibe.landlog.repository.BlogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BlogService {

    private final BlogRepository blogRepository;

    public Long save(Blog blog) {
         blogRepository.save(blog);
         return blog.getCreatorId();
    }

    // 블로그 글 목록 조회 메소드
    public List<Blog> findAll() {
        return blogRepository.findAll();
    }

    // 블로그 글 1개 조회 메소드
    public Blog findById(long id) {
        return blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found : " + id));
    }

    // 블로그 글 삭제 메소드
    public void delete(long id) {
        blogRepository.deleteById(id);
    }

    // 블로그 글 수정 메소드
    public Blog update(long id, UpdateBlogRequest request) {

        Blog blog = blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found : " + id));

        blog.update(request.getTitle(), request.getContent());

        return blog;
    }
}
