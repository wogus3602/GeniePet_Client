package com.example.genie_pet_project.Fragment;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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

    @BindView(R.id.dogprofile)
    Button mDogProfile;
    @BindView(R.id.logout)
    Button mLogout;
    @BindView(R.id.profileimage)
    ImageView mProfileImage;
    @BindView(R.id.dogname)
    TextView mDogName;
    @BindView(R.id.age)
    TextView mAge;
    @BindView(R.id.specie)
    TextView mSpecie;

    String profileimage;
    String dogname;
    String dogage;
    String specie;
    public static Fragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_fragment,container, false);
        ButterKnife.bind(this,view);

        profileimage = SaveSharedPreference.getDogImagePath(MainActivity.getInstance());
        dogage = SaveSharedPreference.getDogAge(MainActivity.getInstance());
        dogname = SaveSharedPreference.getDogName(MainActivity.getInstance());
        specie = SaveSharedPreference.getSpecie(MainActivity.getInstance());


        Bitmap myBitmap = BitmapFactory.decodeFile(profileimage);
        mProfileImage.setImageBitmap(myBitmap);

        mDogName.setText(dogname);
        mAge.setText(dogage);
        mSpecie.setText(specie);

        mDogProfile.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), Camera.class);
            startActivity(intent);
        });

        mLogout.setOnClickListener(v -> {
            SaveSharedPreference.clearPreference(MainActivity.getInstance());
            Fragment fragment = null;
            fragment = new HomeFragment();
            replaceFragment(fragment);
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
