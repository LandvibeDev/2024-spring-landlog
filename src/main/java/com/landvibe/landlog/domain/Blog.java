package com.landvibe.landlog.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Blog {
    private Long creatorId;
    private String title;
    private String content;

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
