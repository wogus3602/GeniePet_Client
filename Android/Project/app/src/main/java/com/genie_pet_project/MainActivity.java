package com.genie_pet_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity{
    public static MainActivity mMyApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMyApplication = this;
    }

    public static MainActivity getInstance() {
        if(mMyApplication == null){
            mMyApplication = new MainActivity();
        }
        return mMyApplication;
    }
}
