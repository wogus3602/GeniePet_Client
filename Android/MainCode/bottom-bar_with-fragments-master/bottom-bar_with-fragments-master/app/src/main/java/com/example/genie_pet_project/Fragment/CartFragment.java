package com.example.genie_pet_project.Fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.genie_pet_project.Activity.StoreListActivity;
import com.example.genie_pet_project.Adapter.CartListViewAdapter;
import com.example.genie_pet_project.Adapter.StoreListViewAdapter;
import com.example.genie_pet_project.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CartFragment extends Fragment {
    public static CartListViewAdapter adapter;
    public static CartFragment mCartFragment;

    @BindView(R.id.List_view)
    ListView mListView;

    public static CartFragment getInstance() {
        if(mCartFragment == null){
            mCartFragment = new CartFragment();
        }
        return mCartFragment;
    }
    public CartFragment() {
    }

    public static Fragment newInstance() {
        return new CartFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCartFragment = this;
    }

    public void addItem(){
        adapter = new CartListViewAdapter();
        mListView.setAdapter(adapter);
        String img="1";
        String itemname = "1";
        String price = "1";

        adapter.addVO(img,itemname,price);
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.cart_fragment, container, false);
        ButterKnife.bind(this,view);

        return view;
    }

}