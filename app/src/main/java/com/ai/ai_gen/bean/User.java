package com.ai.ai_gen.bean;

public class User {
    private String name;            //用户名
    private String avatar;            //头像
    private String password;        //密码
    private String email;        //邮箱
    private String phonenum;        //手机号码

    private int loginStatus;  // 新增的字段


    public User(String name, String avatar, String password, String email, String phonenum, int loginStatus) {
        this.name = name;
        this.avatar = avatar;
        this.password = password;
        this.email = email;
        this.phonenum = phonenum;
        this.loginStatus = loginStatus;
    }



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhonenum() {
        return phonenum;
    }

    public void setPhonenum(String phonenum) {
        this.phonenum = phonenum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                "avatar='" + avatar + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", phonenum='" + phonenum + '\'' +
                '}';
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}


