package com.genie_pet_project.models;


public class User {
    private int id;
    private String email;
    private String username;
    private String password1;
    private String password2;
    private String token;


    public User(int id,
                String email,
                String username,
                String password1,
                String password2,
                String token
    ) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.password1 = password1;
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