package com.landvibe.landlog.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BlogForm {
    private Long creatorId;
    private String title;
    private String content;
}
