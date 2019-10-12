package com.example.bottombar_navigation_with_fragment;

import android.graphics.drawable.Drawable;


public class ListVO {
    private Drawable img; //이미지
    private String title; // 제목
    private String context; // 상세정보
    private String price; // 가격

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Drawable getImg() {
        return img;
    }

    public void setImg(Drawable img) {
        this.img = img;
    }


    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }
}