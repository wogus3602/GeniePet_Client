package com.example.myapplication.DjangoAdapter;


public class PostsAdapter {

    private String title;
    private String text;
    private String species;

    public String getSpecies() {
        return species;
    }

    public PostsAdapter(String title, String text) {
        this.title = title;
        this.title = text;
    }


    public String getTitle(){
        return title;
    }

    public String getText(){
        return text;
    }

}