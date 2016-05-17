package me.hsky.androidshop.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.LinkedList;

import me.hsky.androidshop.R;
import me.hsky.androidshop.UserLogin;
import me.hsky.androidshop.data.Shop;
import me.hsky.androidshop.utils.SharedUtils;


/**
 * Created by user on 2016/5/9.
 */
public class ProjectListAdapter extends BaseAdapter {
    private LinkedList<Shop> projectList;
    private Context context;
    public ProjectListAdapter(LinkedList<Shop> projectList, Context context) {
        this.projectList = projectList;
        this.context = context;
    }
    @Override
    public int getCount() {
        return projectList.size();
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
        ProjectListHolder myHolder = null;
        if(convertView == null){
            myHolder = new ProjectListHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.project_list_item, null);
            x.view().inject(myHolder, convertView);
            convertView.setTag(myHolder);
        }else{
            myHolder = (ProjectListHolder) convertView.getTag();
        }

        myHolder.project_image.setImageResource(projectList.get(position).getImg());
        myHolder.project_name.setText(projectList.get(position).getName());
        myHolder.project_desc.setText(projectList.get(position).getDesc());
        myHolder.project_price_unit.setText(projectList.get(position).getPrice() + "/" + projectList.get(position).getUnit());
        myHolder.project_standard.setText(projectList.get(position).getStandard());

        myHolder.project_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "增加商品数量", Toast.LENGTH_SHORT).show();

                v.getContext().startActivity(new Intent(v.getContext(), UserLogin.class));
                SharedUtils.setWelcomeBoolean(v.getContext(), true);
            }
        });

        myHolder.project_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "减少商品数量", Toast.LENGTH_SHORT).show();
            }
        });
        myHolder.project_relativelayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "点击了商品",Toast.LENGTH_SHORT).show();
            }
        });

        return convertView;
    }

    public class ProjectListHolder{
        @ViewInject(R.id.project_relativelayout)
        public RelativeLayout project_relativelayout;

        @ViewInject(R.id.project_image)
        public ImageView project_image;

        @ViewInject(R.id.project_name)
        public TextView project_name;

        @ViewInject(R.id.project_desc)
        public TextView project_desc;

        @ViewInject(R.id.project_price_unit)
        public TextView project_price_unit;

        @ViewInject(R.id.project_standard)
        public TextView project_standard;

        @ViewInject(R.id.project_add)
        public ImageView project_add;

        @ViewInject(R.id.buy_number)
        public ImageView buy_number;

        @ViewInject(R.id.project_delete)
        public ImageView project_delete;
    }
}
