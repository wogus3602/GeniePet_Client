package com.example.genie_pet_project;

import android.os.Parcelable;
import android.widget.ListView;

import com.example.genie_pet_project.model.CartItemList;

import java.util.ArrayList;
import java.util.Vector;

public class DataManager {
    private static final DataManager ourInstance = new DataManager();
    private static String Species;
    private static Parcelable p;
    private static CartItemList Array;
    private static int quantity;
    private static int sum=0;

    public static int getSum() {
        return sum;
    }

    public static int getQuantity() {
        return quantity;
    }

    public static void setQuantity(int quantity) {
        DataManager.quantity = quantity;
    }

    private static ArrayList<CartItemList> ArrayCart = new ArrayList<CartItemList>();

    public  ArrayList<CartItemList> getArray() {
        return ArrayCart;
    }

    public void setArray(CartItemList array) {
        ArrayCart.add(array);
    }

    public void addSum(int price){sum += price;}
    public void minusSum(int price){sum -= price;}

    public void deleteArray(int pos){
        ArrayCart.remove(pos);
    }
    public static DataManager getInstance() {
        return ourInstance;
    }

    public String getSpecies() {
        return Species;
    }

    public void setSpecies(String species) {
        Species = species;
    }

    public Parcelable getP() {
        return p;
    }

    public void setP(Parcelable p) {
        DataManager.p = p;
    }

}
