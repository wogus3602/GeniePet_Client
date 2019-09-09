package com.example.myapplication.DjangoAdapter;

public class UserAdapter {
    private String token;

    public UserAdapter(String token) {

        this.token = token;
    }


    public String getToken(){
        return token;
    }
}