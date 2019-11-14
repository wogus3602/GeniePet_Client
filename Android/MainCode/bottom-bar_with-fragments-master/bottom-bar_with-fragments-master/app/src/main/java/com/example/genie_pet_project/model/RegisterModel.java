package com.example.genie_pet_project.model;

public class RegisterModel {

    private String email;
    private String username;
    private String password1;
    private String password2;

    public RegisterModel(
                String email,
                String username,
                String password1,
                String password2
    ) {
        this.email = email;
        this.username = username;
        this.password1 = password1;
        this.password2 = password2;
    }

}