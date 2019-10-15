package com.example.bottombar_navigation_with_fragment.Fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.bottombar_navigation_with_fragment.R;

public class LeftFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //just change the fragment_dashboard
        //with the fragment you want to inflate
        //like if the class is LeftFragment it should have R.layout.home_fragment
        //if it is CenterFragment it should have R.layout.fragment_dashboard
        return inflater.inflate(R.layout.fragment_left, null);
    }
}
