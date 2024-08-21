package com.landvibe.landlog.repository;

import com.landvibe.landlog.domain.Blog;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class MemoryBlogRepository implements BlogRepository {
    private static Map<Long, Blog> store = new HashMap<>();
    private static long sequence = 0L;

    @Override
    public Blog save(Blog blog) {
        blog.setCreatorId(++sequence);
        store.put(blog.getCreatorId(), blog);
        return blog;
    }

    @Override
    public Optional<Blog> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<Blog> findAll() {
        return new ArrayList<>(store.values());
    }

    public void clearStore() {
        store.clear();
    }

    @Override
    public void deleteById(Long id) {
        store.remove(id);
    }

}
