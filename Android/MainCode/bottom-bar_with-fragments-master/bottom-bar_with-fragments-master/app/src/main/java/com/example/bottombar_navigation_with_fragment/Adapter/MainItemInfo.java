package com.example.bottombar_navigation_with_fragment.Adapter;

public class MainItemInfo {
    public int drawableId;
    public String price;
    public String name;
    public String all;
    public String menu;

    public MainItemInfo(int drawableId, String price, String name, String all){
        this.drawableId = drawableId;
        this.price = price;
        this.name = name;
        this.all = all;
    }

    public MainItemInfo(String menu){
        this.menu = menu;
    }
}
