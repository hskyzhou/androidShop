package me.hsky.androidshop.adapter;

import android.content.Context;
import android.text.Layout;
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
public class IndexContent extends BaseAdapter {
    private LinkedList<Shop> mData;

    public IndexContent(LinkedList<Shop> mData){
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
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.index_content_1, null);
            x.view().inject(myHolder, convertView);
            convertView.setTag(myHolder);
        }else{
            myHolder = (MyHolder) convertView.getTag();
        }

        myHolder.imageview.setImageResource(mData.get(position).getImg());
        myHolder.textViewWord.setText(mData.get(position).getName());
        myHolder.textViewMoney.setText(mData.get(position).getPrice());
        return convertView;
    }

    public class MyHolder{
        @ViewInject(R.id.index_content_img_1)
        public ImageView imageview;
        @ViewInject(R.id.index_content_word_1)
        public TextView textViewWord;
        @ViewInject(R.id.index_content_money_1)
        public TextView textViewMoney;
    }
}
