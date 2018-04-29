package com.test.fixapp.model;

/**
 * Created by MSI-GL62 on 24/4/2561.
 */

public class user {
    String email;
    String password;
    String name;
    String tel;
    String location;
    String picture;

    public user(String email, String password, String name, String tel, String location, String picture) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.tel = tel;
        this.location = location;
        this.picture = picture;
    }

    public user(String email) {
        this.email = email;
    }
}
