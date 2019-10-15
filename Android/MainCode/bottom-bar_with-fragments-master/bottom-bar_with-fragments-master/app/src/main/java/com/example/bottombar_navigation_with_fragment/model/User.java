package com.example.bottombar_navigation_with_fragment.model;


public class User {
    private int id;
    private String email;
    private String username;
    private String password;
    private String password2;
    private String token;


    public User(int id,
                String email,
                String username,
                String password,
                String password2,
                String token
    ) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.password = password;
        this.password2 = password2;
        this.token = token;
    }


    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getToken(){
        return token;
    }


}