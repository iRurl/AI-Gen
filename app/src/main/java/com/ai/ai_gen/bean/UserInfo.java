package com.ai.ai_gen.bean;

public class UserInfo {
    private String phonenum;
    private String name;
    private String avatar;
    private String work;
    private String qm;
    private String sex;

    public UserInfo(String phonenum, String name, String avatar, String work,String qm, String sex) {
        this.phonenum = phonenum;
        this.name = name;
        this.avatar = avatar;
        this.work = work;
        this.qm = qm;
        this.sex = sex;
    }

    // Getters and setters
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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }

    public String getSex() {
        return sex;
    }
    public String getQm() {
        return qm;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
