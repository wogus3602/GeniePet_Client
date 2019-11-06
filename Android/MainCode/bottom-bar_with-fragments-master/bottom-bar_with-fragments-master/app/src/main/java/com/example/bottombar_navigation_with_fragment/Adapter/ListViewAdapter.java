package com.example.bottombar_navigation_with_fragment.Adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.bottombar_navigation_with_fragment.R;
import com.example.bottombar_navigation_with_fragment.model.ListVO;

import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter {

    private ArrayList<ListVO> listVO = new ArrayList<ListVO>() ;
    public ListViewAdapter() {

    }

    @Override
    public int getCount() {
        return listVO.size() ;
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

        ListVO listViewItem = listVO.get(position);

        rank.setText(listViewItem.getRank());
        // 아이템 내 각 위젯에 데이터 반영
        Glide.with(context).load(listViewItem.getImg()).into(image);
        title.setText(listViewItem.getTitle());
        price.setText(listViewItem.getPrice());

        //리스트뷰 클릭 이벤트
        convertView.setOnClickListener(view -> Toast.makeText(context, (pos+1)+"번째 리스트가 클릭되었습니다.", Toast.LENGTH_SHORT).show());
        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return position ;
    }

    @Override
    public Object getItem(int position) {
        return listVO.get(position) ;
    }

    // 데이터값 넣어줌
    public void addVO(String rank,String icon, String title, String desc,String price) {
        ListVO item = new ListVO();
        item.setRank(rank);
        item.setImg(icon);
        item.setTitle(title);
        item.setPrice(price);
        listVO.add(item);
    }
}
