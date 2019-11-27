package com.example.genie_pet_project.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;

import com.bumptech.glide.Glide;
import com.example.genie_pet_project.DataManager;
import com.example.genie_pet_project.R;
import com.example.genie_pet_project.model.StoreList;

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
    @BindView(R.id.quantity)
    Button mQuantity;

    String itemName;
    String value;
    String image;

    TextView textCartItemCount;
    int mCartItemCount;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        // processIntent();

        Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        AppbarExcute();

        mPutInCartButton.setOnClickListener(v -> {
            CartActivity.getInstance().addItem(image,itemName,value);
            mCartItemCount = DataManager.getInstance().getArray().size();
            setupBadge();
            Toast.makeText(getApplicationContext(),"장바구니에 추가 되었습니다.",Toast.LENGTH_SHORT).show();
        });

        // 수량 팝업용 Adapter
        adapter = new ArrayAdapter<>(this, android.R.layout.select_dialog_singlechoice);
        adapter.addAll("1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30");
        adapter.notifyDataSetChanged();


        processIntent();
    }
    // 수량 버튼 클릭
    public void quantity_button_clicked(View v){
        CreateListDialog();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{ //toolbar의 back키 눌렀을 때 동작
                finish();
                return true;
            }
            case R.id.action_cart: {
                Intent intent = new Intent(getApplicationContext(), CartActivity.class);
                startActivity(intent);
                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    public void AppbarExcute(){
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true); //커스터마이징 하기 위해 필요
        actionBar.setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼, 디폴트로 true만 해도 백버튼이 생김
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);
        final MenuItem mMenuItem = menu.findItem(R.id.action_cart);

        View actionView = MenuItemCompat.getActionView(mMenuItem);
        textCartItemCount = actionView.findViewById(R.id.cart_badge);
        mCartItemCount = DataManager.getInstance().getArray().size();
        setupBadge();

        actionView.setOnClickListener(v -> onOptionsItemSelected(mMenuItem));

        return true;
    }

    // 수량 리스트 다이얼로그 생성
    public void CreateListDialog(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("수량");     //타이틀

        //어답터 , 클릭이벤트 설정
        alert.setAdapter(adapter, (dialog, which) -> {
            String menu = adapter.getItem(which);
            DataManager.setQuantity(menu);
            mQuantity.setText("수량 : "+menu);
        });
        alert.show();
    }

    private void setupBadge() {
        if (textCartItemCount != null) {
            if(mCartItemCount < 0) return;
            if (mCartItemCount == 0) {
                if (textCartItemCount.getVisibility() != View.GONE) {
                    textCartItemCount.setVisibility(View.GONE);
                }
            } else {
                textCartItemCount.setText(String.valueOf(Math.min(mCartItemCount, 99)));
                if (textCartItemCount.getVisibility() != View.VISIBLE) {
                    textCartItemCount.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCartItemCount = DataManager.getInstance().getArray().size();
        setupBadge();
    }

    private void processIntent(){
        Bundle bundle = getIntent().getExtras();

        StoreList data = bundle.getParcelable("object");
        itemName = data.getTitle();
        value = data.getPrice();
        image = data.getImg();
        mGoodsName.setText(itemName);
        mGoodsValue.setText(value);
        mDest.setText(data.getDesc());
        Glide.with(getApplicationContext()).load(image).into(mGoodsImage);
    }

}
