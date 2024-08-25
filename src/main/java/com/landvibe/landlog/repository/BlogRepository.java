package com.landvibe.landlog.repository;

import com.landvibe.landlog.domain.Blog;

import java.util.List;
import java.util.Optional;

public interface BlogRepository {

    Blog save(Blog blog);

    List<Blog> findByCreatorId(Long creatorId);

    Optional<Blog> findByBlogId(List<Blog> blogs, Long blogId);

    void updateById(Long creatorId, Long blogId, Blog updatedBlog);
    void deleteById(Long creatorId, Long blogId);

    List<Blog> findAll();
}
