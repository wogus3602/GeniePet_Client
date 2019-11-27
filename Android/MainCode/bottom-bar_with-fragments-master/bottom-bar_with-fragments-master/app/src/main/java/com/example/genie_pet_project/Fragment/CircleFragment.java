package com.example.genie_pet_project.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.genie_pet_project.Activity.MainActivity;
import com.example.genie_pet_project.CircleMenuLayout;
import com.example.genie_pet_project.R;
import com.example.genie_pet_project.Activity.StoreListActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CircleFragment extends Fragment {
    String[] menuArray = new String[] { "사료", "간식", "미용/목욕", "하우스", "장난감", "기타" };
    int[] imgArray = new int[] {R.drawable.dog_food,R.drawable.dog_snack,R.drawable.dog_lotion,R.drawable.dog_house,R.drawable.dog_etc,R.drawable.dog_etc};

    @BindView(R.id.tip_image)
    ImageView mImageView;
    @BindView(R.id.id_menulayout)
    CircleMenuLayout mCircleMenuLayout;
    @BindView(R.id.tiptext)
    TextView mTipText;

    public static CircleFragment newInstance() {
        return new CircleFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_circle, container, false);
        ButterKnife.bind(this,view);

        Glide.with(this).load(R.drawable.dog_run).into(mImageView);
        mTipText.setText(MainActivity.getInstance().tipText);
        mCircleMenuLayout.setMenuItemIconsAndTexts(menuArray,imgArray);
        mCircleMenuLayout.setOnMenuItemClickListener(new CircleMenuLayout.OnMenuItemClickListener() {
            @Override
            public void itemClick(View view, int pos) {
                if(pos==6){
                    Intent intent = new Intent(getContext(), StoreListActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void itemCenterClick(View view) {
            }

            @Override
            public void itemSelChange(int pos) {
            }
        });
        return view;
    }


    public String[] getMenuArray() {
        return menuArray;
    }
}
