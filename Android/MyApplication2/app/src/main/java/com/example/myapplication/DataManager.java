package com.example.myapplication;

public class DataManager {
    private static final DataManager ourInstance = new DataManager();
    private static String Species;
    public static DataManager getInstance() {
        return ourInstance;
    }

    public String getSpecies() {
        return Species;
    }

    public void setSpecies(String species) {
        Species = species;
    }

    private DataManager() {
    }
}
