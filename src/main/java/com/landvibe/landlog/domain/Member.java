package com.landvibe.landlog.domain;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    private Long id;
    private String name;

    private String email;

    private String password;

}
