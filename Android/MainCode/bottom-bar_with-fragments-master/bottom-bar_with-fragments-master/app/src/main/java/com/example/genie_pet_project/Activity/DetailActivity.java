package com.example.genie_pet_project.Activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.genie_pet_project.R;
import com.example.genie_pet_project.model.ListVO;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.tipView)
    TextView mTipView;
    @BindView(R.id.goodsImage)
    ImageView mGoodsImage;
    @BindView(R.id.rating)
    TextView mGragePoint;
    @BindView(R.id.ratingBar)
    RatingBar mRatingBar;
    @BindView(R.id.goodsName)
    TextView mGoodsName;
    @BindView(R.id.goodsValue)
    TextView mGoodsValue;
    @BindView(R.id.putInCart)
    Button mPutInCartButton;
    @BindView(R.id.dest)
    TextView mDest;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        processIntent();
    }

    private void processIntent(){
        Bundle bundle = getIntent().getExtras();

        ListVO data = bundle.getParcelable("object");

        mGoodsName.setText(data.getTitle());
        mGoodsValue.setText(data.getPrice());
        mDest.setText(data.getDesc());
        Glide.with(getApplicationContext()).load(data.getImg()).into(mGoodsImage);

    }

}
