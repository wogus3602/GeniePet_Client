package com.example.bottombar_navigation_with_fragment.model;

import android.os.Parcel;
import android.os.Parcelable;

public class ListVO implements Parcelable {
    private String rank;
    private String img; //이미지
    private String title; // 제목
    private String price; // 가격
    private String desc;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public ListVO(){
    }


    public ListVO(Parcel src){
        rank = src.readString();
        img = src.readString();
        title = src.readString();
        price = src.readString();
        desc = src.readString();
    }
    @Override
    public int describeContents() {
        return 0;
    }
    // create Parcelable
    public static final Creator<ListVO> CREATOR = new Creator<ListVO>() {
        @Override
        public ListVO createFromParcel(Parcel parcel) {
            return new ListVO(parcel);
        }
        @Override
        public ListVO[] newArray(int size) {
            return new ListVO[size];
        }
    };
    @Override
    public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(rank);
            dest.writeString(img);
            dest.writeString(title);
            dest.writeString(price);
            dest.writeString(desc);
    }
}
