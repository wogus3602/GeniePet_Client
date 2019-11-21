package com.example.genie_pet_project.Adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.genie_pet_project.Activity.StoreListActivity;
import com.example.genie_pet_project.R;
import com.example.genie_pet_project.model.StoreList;

import java.util.ArrayList;

public class StoreListViewAdapter extends BaseAdapter {

    private ArrayList<StoreList> storeList = new ArrayList<StoreList>() ;
    public StoreListViewAdapter() {

    }

    @Override
    public int getCount() {
        return storeList.size() ;
    }

    // ** 이 부분에서 리스트뷰에 데이터를 넣어줌 **
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //postion = ListView의 위치      /   첫번째면 position = 0
        final int pos = position;
        final Context context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.store_item, parent, false);
        }

        TextView rank = convertView.findViewById(R.id.rank);
        ImageView image =  convertView.findViewById(R.id.img) ;
        TextView title =  convertView.findViewById(R.id.title) ;
        TextView price = convertView.findViewById(R.id.price);

        StoreList listViewItem = storeList.get(position);

        rank.setText(listViewItem.getRank());
        // 아이템 내 각 위젯에 데이터 반영
        Glide.with(context).load(listViewItem.getImg()).into(image);
        title.setText(listViewItem.getTitle());
        price.setText(listViewItem.getPrice());

        convertView.setOnClickListener(view -> StoreListActivity.getInstance().Click(storeList.get(pos)));

        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return position ;
    }

    @Override
    public Object getItem(int position) {
        return storeList.get(position) ;
    }

    // 데이터값 넣어줌
    public void addVO(String rank,String icon, String title, String desc,String price) {
        StoreList item = new StoreList();
        item.setRank(rank);
        item.setImg(icon);
        item.setTitle(title);
        item.setDesc(desc);
        item.setPrice(price);
        storeList.add(item);
    }
}
