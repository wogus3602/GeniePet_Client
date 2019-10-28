package com.example.bottombar_navigation_with_fragment.Fragment;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.bottombar_navigation_with_fragment.Camera.Camera;
import com.example.bottombar_navigation_with_fragment.MainActivity;
import com.example.bottombar_navigation_with_fragment.R;
import com.example.bottombar_navigation_with_fragment.SaveSharedPreference;


public class ProfileFragment extends Fragment {
    private SharedPreferences sharedPreferences;
    public ProfileFragment() {
    }

    public static Fragment newInstance() {

        return new ProfileFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_fragment,container, false);
        Button button = view.findViewById(R.id.button);
        Button button1 = view.findViewById(R.id.button2);
        Button button3 = view.findViewById(R.id.button3);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                SaveSharedPreference.clearPreference(MainActivity.getInstance());
                Fragment fragment = null;
                fragment = new HomeFragment();
                replaceFragment(fragment);
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Camera.class);
                startActivity(intent);

            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return view;
    }


    public void replaceFragment(Fragment someFragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, someFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
