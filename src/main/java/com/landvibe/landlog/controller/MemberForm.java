package com.landvibe.landlog.controller;

public class MemberForm {
    private String name;
    private String email;
    private String pwd;

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
