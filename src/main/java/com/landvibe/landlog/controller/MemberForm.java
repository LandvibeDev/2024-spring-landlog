package com.landvibe.landlog.controller;

public class MemberForm {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String email;

    private String pwd;

    public String getEmail(){return email;}

    public String getPwd(){return pwd;}

    public void setEmail(String email){this.email=email;}

    public void setPwd(String password ){this.pwd=pwd;}
}
