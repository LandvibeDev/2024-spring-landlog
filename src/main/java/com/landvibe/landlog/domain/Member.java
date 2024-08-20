package com.landvibe.landlog.domain;

public class Member {

    private Long id;
    private String name;
    private String email;
    private String pwd;

    public Member() {
    }

    public Member(Long id, String name, String email, String pwd) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.pwd = pwd;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public String getEmail() {return email;}
    public String getPwd() {return pwd;}

    public void setName(String name) {
        this.name = name;
    }
    public void setEmail(String email) {this.email = email;}
    public void setPwd(String pwd) {this.pwd = pwd;}
}
