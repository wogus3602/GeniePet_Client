package com.example.genie_pet_project.model;

import android.os.Parcel;
import android.os.Parcelable;

public class CartItemList implements Parcelable {
    private String img;
    private String price; //이미지
    private String itemname; // 제목



    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public CartItemList(){
    }


    public CartItemList(Parcel src){
        img = src.readString();
        itemname = src.readString();
        price = src.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }
    // create Parcelable
    public static final Creator<CartItemList> CREATOR = new Creator<CartItemList>() {

        @Override
        public CartItemList createFromParcel(Parcel parcel) {
            return new CartItemList(parcel);
        }

        @Override
        public CartItemList[] newArray(int size) {
            return new CartItemList[size];
        }
    };
    @Override
    public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(img);
            dest.writeString(itemname);
            dest.writeString(price);
    }
}
