package com.example.myapplication;

public class User {
    private String token;

    public User(String token) {

        this.token = token;
    }


    public String getToken(){
        return token;
    }
}