package com.landvibe.landlog.repository;

import com.landvibe.landlog.domain.Blog;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class MemoryBlogRepository implements BlogRepository {

    private Map<Long, Blog> store = new HashMap<Long, Blog>();
    private static long sequence = 0L;

    @Override
    public Blog save(Blog blog) {
        if(blog.getId()==null) {blog.setId(++sequence);}
        store.put(blog.getId(), blog);
        return blog;
    }

    @Override
    public List<Blog> findByCreatorId(Long creatorId) {
        List<Blog> blogs = new ArrayList<>();
        for(Blog blog : store.values()){
            if(blog.getCreatorId().equals(creatorId)){
                blogs.add(blog);
            }
        }
        return blogs;
    }

    @Override
    public Optional<Blog> findByBlogId(List<Blog> blogs, Long blogId) {
        for(Blog blog : blogs){
            if(blog.getId().equals(blogId)){
                return Optional.of(blog);
            }
        }
        return Optional.empty();
    }

    @Override
    public void updateById(Long creatorId, Long blogId, Blog updatedBlog){
        List<Blog> blogs = findByCreatorId(creatorId);
        Blog blog = findByBlogId(blogs, blogId).get();
        blog.setTitle(updatedBlog.getTitle());
        blog.setContents(updatedBlog.getContents());
        save(blog);
        return;
 /*
        for(Blog blog : blogs){
            if(blog.getId().equals(blogId)){
                blog.setTitle(updatedBlog.getTitle());
                blog.setContents(updatedBlog.getContents());
                save(blog);
                return;
            }
        }
        throw new IllegalStateException("존재하지 않는 게시글입니다.");*/

    }

    @Override
    public void deleteById(Long creatorId, Long blogId) {
        List<Blog> blogs = findByCreatorId(creatorId);
        Blog blog = findByBlogId(blogs, blogId).get();
        store.remove(blogId);
/*        for(Blog blog : blogs){
            if(blog.getId().equals(blogId)){
                store.remove(blogId);
                return;
            }
        }
        throw new IllegalStateException("존재하지 않는 게시글입니다.");*/
    }

    @Override
    public List<Blog> findAll() {
        return new ArrayList<Blog>(store.values());
    }

    public void clearStore() {
        store.clear();
    }
}
