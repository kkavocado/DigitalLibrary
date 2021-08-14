package com.example.digitallibrary;

import java.io.Serializable;

public class User implements Serializable {

    private int uid;
    private String name;
    private String email;
    private String passoword;

    private String about;

    public User() {
    }

    public User(int uid, String name, String email, String passoword, String about) {
        this.uid = uid;
        this.name = name;
        this.email = email;
        this.passoword = passoword;
        this.about = about;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassoword() {
        return passoword;
    }

    public void setPassoword(String passoword) {
        this.passoword = passoword;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }
}
