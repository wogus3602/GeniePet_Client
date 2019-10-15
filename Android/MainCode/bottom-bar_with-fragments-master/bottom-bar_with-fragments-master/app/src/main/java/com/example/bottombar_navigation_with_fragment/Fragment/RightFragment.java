package com.example.bottombar_navigation_with_fragment.Fragment;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.bottombar_navigation_with_fragment.R;
import com.example.bottombar_navigation_with_fragment.SaveSharedPreference;

import static android.content.Context.MODE_PRIVATE;

public class RightFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_right,container, false);
        Button button = view.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                SaveSharedPreference.clearPreference(getActivity());
                Fragment fragment = null;
                fragment = new CenterFragment();
                replaceFragment(fragment);
            }
        });
        return view;
    }

    public void clearUserName() {
        SharedPreferences appData;
        appData = getActivity().getSharedPreferences("myPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = appData.edit();
        editor.clear();
        editor.commit();
    }

    public void replaceFragment(Fragment someFragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, someFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
