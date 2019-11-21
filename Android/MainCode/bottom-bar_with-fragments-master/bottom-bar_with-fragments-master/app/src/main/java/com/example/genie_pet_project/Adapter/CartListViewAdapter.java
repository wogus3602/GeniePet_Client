package com.example.genie_pet_project.Adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.genie_pet_project.Activity.StoreListActivity;
import com.example.genie_pet_project.Fragment.CartFragment;
import com.example.genie_pet_project.R;
import com.example.genie_pet_project.model.CartItemList;
import com.example.genie_pet_project.model.StoreList;

import java.util.ArrayList;

import butterknife.BindView;

public class CartListViewAdapter extends BaseAdapter {

    @BindView(R.id.img)
    ImageView mImage;
    @BindView(R.id.price)
    TextView mPrice;
    @BindView(R.id.itemname)
    TextView mItemName;

    @BindView(R.id.amount)
    Button mAmount;
    @BindView(R.id.delect)
    Button mDelect;

    private ArrayList<CartItemList> cartItemList = new ArrayList<CartItemList>() ;
    public CartListViewAdapter() {

    }

    @Override
    public int getCount() {
        return cartItemList.size() ;
    }

    // ** 이 부분에서 리스트뷰에 데이터를 넣어줌 **
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.cart_item, parent, false);
        }

        CartItemList listViewItem = cartItemList.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        Glide.with(context).load(listViewItem.getImg()).into(mImage);
        mItemName.setText(listViewItem.getItemname());
        mPrice.setText(listViewItem.getPrice());

        //convertView.setOnClickListener(view -> CartFragment.getInstance().Click(cartItemList.get(pos)));

        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return position ;
    }

    @Override
    public Object getItem(int position) {
        return cartItemList.get(position) ;
    }

    // 데이터값 넣어줌
    public void addVO(String icon, String itemname, String price) {
        CartItemList item = new CartItemList();
        item.setImg(icon);
        item.setItemname(itemname);
        item.setPrice(price);
        cartItemList.add(item);
    }
}
