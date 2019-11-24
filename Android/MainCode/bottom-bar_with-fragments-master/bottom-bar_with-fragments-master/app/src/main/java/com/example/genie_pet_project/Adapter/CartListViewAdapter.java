package com.example.genie_pet_project.Adapter;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.genie_pet_project.Activity.CartActivity;
import com.example.genie_pet_project.DataManager;
import com.example.genie_pet_project.R;
import com.example.genie_pet_project.model.CartItemList;

import java.util.ArrayList;

public class CartListViewAdapter extends BaseAdapter {


    private ArrayList<CartItemList> cartItemList = new ArrayList<>() ;
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
        ImageView mImage  = convertView.findViewById(R.id.img);
        TextView mPrice = convertView.findViewById(R.id.price);
        TextView mItemName = convertView.findViewById(R.id.itemname);

        Button mDelectButton = convertView.findViewById(R.id.delect);

        CartItemList listViewItem = cartItemList.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        Glide.with(context).load(listViewItem.getImg()).into(mImage);
        mItemName.setText(listViewItem.getItemname());
        mPrice.setText(listViewItem.getPrice());
        mDelectButton.setOnClickListener(view ->{
            String str = cartItemList.get(pos).getPrice().replaceAll("[^0-9]", "");
            cartItemList.remove(pos);
            DataManager.getInstance().minusSum(Integer.parseInt(str));
            CartActivity.getInstance().SumChange();
            CartActivity.getInstance().Click(pos);
        });
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

    public void set(){
        if(DataManager.getInstance().getArray()!=null) {
            ArrayList<CartItemList> p = DataManager.getInstance().getArray();
            for(CartItemList cartItemList2 : p){
                cartItemList.add(cartItemList2);
            }
            return;
        }
    }

    // 데이터값 넣어줌
    public void addVO(String icon, String itemname, String price) {
        CartItemList item = new CartItemList();
        item.setImg(icon);
        item.setItemname(itemname);
        item.setPrice(price);
        String str = price.replaceAll("[^0-9]", "");
        DataManager.getInstance().setArray(item);
        DataManager.getInstance().addSum(Integer.parseInt(str));
        cartItemList.add(item);
    }
}
