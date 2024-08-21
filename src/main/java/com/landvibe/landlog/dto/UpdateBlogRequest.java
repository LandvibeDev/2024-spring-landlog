package com.landvibe.landlog.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateBlogRequest {
    private String title;
    private String content;
}
