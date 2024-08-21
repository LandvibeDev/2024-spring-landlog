package com.landvibe.landlog.repository;

import com.landvibe.landlog.domain.Blog;
import com.landvibe.landlog.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface BlogRepository {

    Blog save(Blog blog);

    Optional<Blog> findById(Long id);

    void deleteById(Long id);


    List<Blog> findAll();
}