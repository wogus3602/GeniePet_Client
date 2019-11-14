package com.example.genie_pet_project.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.genie_pet_project.Camera.Camera;
import com.example.genie_pet_project.Activity.MainActivity;
import com.example.genie_pet_project.R;
import com.example.genie_pet_project.SaveSharedPreference;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ProfileFragment extends Fragment {

    @BindView(R.id.button)
    Button mButton;
    @BindView(R.id.button2)
    Button mButton2;
    @BindView(R.id.button3)
    Button mButton3;
    public static Fragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_fragment,container, false);
        ButterKnife.bind(this,view);
        mButton.setOnClickListener(v -> {
            SaveSharedPreference.clearPreference(MainActivity.getInstance());
            Fragment fragment = null;
            fragment = new HomeFragment();
            replaceFragment(fragment);
        });
        mButton2.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), Camera.class);
            startActivity(intent);

        });

        mButton3.setOnClickListener(v -> {

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
