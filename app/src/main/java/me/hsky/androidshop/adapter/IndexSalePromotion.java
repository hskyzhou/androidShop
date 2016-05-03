package me.hsky.androidshop.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.LinkedList;

import me.hsky.androidshop.R;
import me.hsky.androidshop.data.Shop;

/**
 * Created by Administrator on 2016/5/2.
 */
public class IndexSalePromotion extends BaseAdapter {
    private LinkedList<Shop> mData;

    public IndexSalePromotion(LinkedList<Shop> mData){
        this.mData = mData;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyHolder myHolder = null;
        if(convertView == null){
            myHolder = new MyHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.index_sale_promotion, null);
            x.view().inject(myHolder, convertView);
            convertView.setTag(myHolder);
        }else{
            myHolder = (MyHolder) convertView.getTag();
        }

        myHolder.image.setImageResource(mData.get(position).getImg());
        myHolder.name.setText(mData.get(position).getName());
        myHolder.price.setText(mData.get(position).getPrice());
        return convertView;
    }

    public class MyHolder{
        @ViewInject(R.id.index_sale_promotion_img)
        public ImageView image;
        @ViewInject(R.id.index_sale_promotion_name)
        public TextView name;
        @ViewInject(R.id.index_sale_promotion_price)
        public TextView price;
    }
}
