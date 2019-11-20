package com.alpha.detailpage;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    //수량 List Dialog Adapter
    ArrayAdapter<String> adapter;

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
        setContentView(R.layout.detailpage);
        ButterKnife.bind(this);
        // processIntent();

        // 수량 팝업용 Adapter
        adapter = new ArrayAdapter<>(this, android.R.layout.select_dialog_singlechoice);
        adapter.addAll("1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30");
        adapter.notifyDataSetChanged();
    }

    // 수량 버튼 클릭
    public void quantity_button_clicked(View v){
        CreateListDialog();
    }

    // 수량 리스트 다이얼로그 생성
    public void CreateListDialog(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("수량");     //타이틀
//        alert.setIcon(R.drawable.icon); //아이콘

        //어답터 , 클릭이벤트 설정
        alert.setAdapter(adapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String menu = adapter.getItem(which);
                Toast.makeText(DetailActivity.this, "수량 : "+menu, Toast.LENGTH_SHORT).show();
                // 위에 토스트 메시지 대신에 수량:n 칸에 n값을 넣어주고 싶은데.. 혹시 아시나요 ㅜ
            }
        });
        alert.show();
    }
}