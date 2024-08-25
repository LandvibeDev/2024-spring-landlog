package com.landvibe.landlog.service;

import com.landvibe.landlog.domain.Blog;
import com.landvibe.landlog.domain.Member;
import com.landvibe.landlog.repository.BlogRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BlogService {
    private final BlogRepository blogRepository;

    public BlogService(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }

    public Long join(Blog blog) {
        blogRepository.save(blog);
        return blog.getId();
    }

    public void update(Long creatorId, Long blogId, Blog updatedBlog){
        blogRepository.updateById(creatorId, blogId, updatedBlog);
    }

    public void delete(Long creatorId, Long blogId){
        blogRepository.deleteById(creatorId, blogId);
    }

    public List<Blog> findMembers() {
        return blogRepository.findAll();
    }

    public List<Blog> findCreatorBlogs(Long creatorId){ return blogRepository.findByCreatorId(creatorId);}

    public Optional<Blog> findOne(List<Blog> blogs, Long blogId) {
        return blogRepository.findByBlogId(blogs, blogId);
    }
}
