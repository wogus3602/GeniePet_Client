package com.example.bottombar_navigation_with_fragment.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.example.bottombar_navigation_with_fragment.CircleMenuLayout;
import com.example.bottombar_navigation_with_fragment.R;
import com.example.bottombar_navigation_with_fragment.StoreListActivity;

public class CircleFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private CircleMenuLayout mCircleMenuLayout;
    private String mParam1;
    private String mParam2;

    public CircleFragment() {
    }

    public static CircleFragment newInstance() {
        return new CircleFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public void onResume() {
        super.onResume();
    }


    String[] menuArray = new String[] { "사료", "간식", "미용/목욕", "하우스", "장난감", "기타" };
    int[] imgArray = new int[] {R.drawable.dog_food,R.drawable.dog_snack,R.drawable.dog_lotion,R.drawable.dog_house,R.drawable.dog_etc,R.drawable.dog_etc};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_circle, container, false);

        TextView textView = view.findViewById(R.id.tiptext);

        ImageView imageView = view.findViewById(R.id.tip_image);
        Glide.with(this).load(R.drawable.dog_run).into(imageView);

        mCircleMenuLayout = view.findViewById(R.id.id_menulayout);
        mCircleMenuLayout.setMenuItemIconsAndTexts(menuArray,imgArray);
        mCircleMenuLayout.setOnMenuItemClickListener(new CircleMenuLayout.OnMenuItemClickListener() {
            @Override
            public void itemClick(View view, int pos) {
                Toast.makeText(getContext(), "현재 선택" + pos ,Toast.LENGTH_SHORT).show();
                if(pos==6){
                    Intent intent = new Intent(getContext(), StoreListActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void itemCenterClick(View view) {
                Toast.makeText(getContext(), "센터 클릭" ,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void itemSelChange(int pos) {
                //Toast.makeText(getApplicationContext(), "현재 선택" + pos ,Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
    public String[] getMenuArray() {
        return menuArray;
    }
}
