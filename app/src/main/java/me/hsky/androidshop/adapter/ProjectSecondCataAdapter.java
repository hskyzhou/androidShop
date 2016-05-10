package me.hsky.androidshop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import me.hsky.androidshop.R;

/**
 * Created by user on 2016/5/9.
 */
public class ProjectSecondCataAdapter extends BaseAdapter {
    private String[] catagory;
    private final Context context;
    public ProjectSecondCataAdapter(String[] catagory,Context context){
        this.catagory = catagory;
        this.context = context;
    }
    @Override
    public int getCount() {
        return catagory.length;
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
    public View getView(int position, View convertView, final ViewGroup parent) {
        SecondCataHolder myHolder = null;
        if(convertView == null){
            myHolder = new SecondCataHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.project_second_catagory_item, null);
            x.view().inject(myHolder, convertView);
            convertView.setTag(myHolder);
        }else{
            myHolder = (SecondCataHolder) convertView.getTag();
        }

        myHolder.project_item_second_catagory_number.setText("");
        myHolder.project_item_second_catagory_name.setText(catagory[position]);
        if(0 == position){
            myHolder.project_item_second.setBackgroundColor(parent.getResources().getColor(R.color.colorProjectCataWord));
            myHolder.project_item_second_catagory_name.setTextColor(parent.getResources().getColor(R.color.white));
        }

        myHolder.project_item_second.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < catagory.length; i++) {
                    SecondCataHolder holder = (SecondCataHolder) parent.getChildAt(i).getTag();
                    holder.project_item_second.setBackgroundColor(parent.getResources().getColor(R.color.colorProjectSecondList));
                    holder.project_item_second_catagory_name.setTextColor(parent.getResources().getColor(R.color.black));
                    holder.project_item_second_catagory_name.setEnabled(true);
                }
                // 设置当前position的TextView背景为红色，不可点击
                SecondCataHolder currentHolder = (SecondCataHolder) v.getTag();
                currentHolder.project_item_second.setBackgroundColor(parent.getResources().getColor(R.color.colorProjectCataWord));
                currentHolder.project_item_second_catagory_name.setTextColor(parent.getResources().getColor(R.color.white));
                currentHolder.project_item_second_catagory_name.setEnabled(false);
                /*进行产品列表获取*/

            }
        });

        return convertView;
    }

    public class SecondCataHolder{
        @ViewInject(R.id.project_item_second)
        public RelativeLayout project_item_second;

        @ViewInject(R.id.project_item_second_catagory_number)
        public TextView project_item_second_catagory_number;

        @ViewInject(R.id.project_item_second_catagory_name)
        public TextView project_item_second_catagory_name;
    }
}
