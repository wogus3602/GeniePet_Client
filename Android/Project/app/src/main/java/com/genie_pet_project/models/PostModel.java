package com.genie_pet_project.models;

public class PostModel {

    private String name;
    private String age;
    private  String species;

    public PostModel(String name, String species, String age)
    {
        this.name = name;
        this.age = age;
        this.species = species;
    }
}
