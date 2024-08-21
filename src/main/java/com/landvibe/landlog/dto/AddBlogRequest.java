package com.landvibe.landlog.dto;

import com.landvibe.landlog.domain.Blog;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddBlogRequest {
    private String creatorId;
    private String title;
    private String content;

}
