package com.example.genie_pet_project.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.genie_pet_project.Adapter.CartListViewAdapter;
import com.example.genie_pet_project.DataManager;
import com.example.genie_pet_project.R;


import butterknife.BindView;
import butterknife.ButterKnife;

public class CartActivity extends AppCompatActivity {
    public static CartListViewAdapter adapter;
    private static CartActivity mCartActivity;
    public static ListView mListView;

    int mCartItemCount = 10;
    TextView textCartItemCount;

    @BindView(R.id.button4)
    Button button;
    @BindView(R.id.sum)
    TextView mSum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart_fragment);
        mListView = findViewById(R.id.Cart_List_view);

        ButterKnife.bind(this);
        Toolbar mToolbar = findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        adapter = new CartListViewAdapter();
        mListView.setAdapter(adapter);
        adapter.set();
        mSum.setText("총 가격 : "+String.valueOf(DataManager.getInstance().getSum()));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItem("https://ssl.pstatic.net/tveta/libs/1266/1266402/e95e882fbae59e3387df_20191122150559721.jpg","11","11");
                mCartItemCount+=1;
                setupBadge();
            }
        });
        mCartActivity = this;
    }

    public void SumChange(){
        mSum.setText("총 가격 : "+String.valueOf(DataManager.getInstance().getSum()));
    }

    public void Click(int pos){
        adapter.notifyDataSetChanged();
        DataManager.getInstance().deleteArray(pos);
        mCartItemCount-=1;
        setupBadge();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{ //toolbar의 back키 눌렀을 때 동작
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
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

    public static CartActivity getInstance() {
        if(mCartActivity == null){
            mCartActivity = new CartActivity();
        }
        return mCartActivity;
    }

    public void addItem(String img, String itemname, String price){
        adapter = new CartListViewAdapter();
        String cartImg= img;
        String cartItemName = itemname;
        String cartPrice = price;
        mCartItemCount+=1;
        setupBadge();
        adapter.addVO(cartImg,cartItemName,cartPrice);
        adapter.notifyDataSetChanged();
    }

}
