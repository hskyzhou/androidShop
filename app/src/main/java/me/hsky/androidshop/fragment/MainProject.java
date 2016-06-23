package me.hsky.androidshop.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.LinkedList;

import me.hsky.androidshop.R;
import me.hsky.androidshop.adapter.ProjectListAdapter;
import me.hsky.androidshop.consts.CONSTS;
import me.hsky.androidshop.data.ResponseProjectFirstCatagory;
import me.hsky.androidshop.data.Shop;

public class MainProject extends Fragment {
    @ViewInject(R.id.project_major_catagory)
    private HorizontalScrollView project_major_catagory;
    @ViewInject(R.id.project_major_catagory_linear)
    private LinearLayout project_major_catagory_linear;

    /*产品二级分类*/
    @ViewInject(R.id.project_second_catagory)
    private ListView project_second_catagory;

    /*产品详情*/
    @ViewInject(R.id.project_content_list)
    private ListView project_content_list;

    private final String TAG = "tag";
    private ResponseProjectFirstCatagory firstCatas = null;
    private String[] secondCatas = null;

    private LinkedList<Shop> shopList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_project, null);
        x.Ext.init(getActivity().getApplication());
        x.view().inject(this, view);
        /*初始顶级分类*/
        initFirstCatagory();
        return view;
    }

    /*显示一级分类*/
    private void showFirstCata() {
        FirstCataHolder firstCataHolder = null;
        for (int i = 0; i < firstCatas.iData.size(); i++) {
            firstCataHolder = new FirstCataHolder();
            View itemView = LayoutInflater.from(getContext()).inflate(R.layout.project_first_catagory_item, null);
            x.view().inject(firstCataHolder, itemView);
            itemView.setTag(firstCataHolder);
            firstCataHolder.project_item_first_catagory_text.setText(firstCatas.iData.get(0).classifyName);
            if (0 == i) {
                firstCataHolder.project_item_first_catagory_text.setTextColor(getResources().getColor(R.color.colorProjectCataWord));
                firstCataHolder.project_item_first_catagory_text.setEnabled(false);
            }
            final int number = i;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (int i = 0; i < project_major_catagory_linear.getChildCount(); i++) {
                        FirstCataHolder childHolder = (FirstCataHolder) project_major_catagory_linear.getChildAt(i).getTag();
                        childHolder.project_item_first_catagory_text.setTextColor(getResources().getColor(R.color.colorProjectFirstWord));
                        childHolder.project_item_first_catagory_text.setEnabled(true);
                    }
                    // 设置当前position的TextView背景为红色，不可点击
                    FirstCataHolder newHolder = (FirstCataHolder) v.getTag();
                    newHolder.project_item_first_catagory_text.setTextColor(getResources().getColor(R.color.colorProjectCataWord));
                    newHolder.project_item_first_catagory_text.setEnabled(false);

                    initSecondCatagory(firstCatas.iData.get(0).ID + "");
                }
            });

            project_major_catagory_linear.addView(itemView);
        }
    }

    /*获取一级分类数据*/
    public void initFirstCatagory() {
        RequestParams params = new RequestParams(CONSTS.FirstCatagory);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                Log.i(TAG, "onSuccess: " + result);
                firstCatas = gson.fromJson(result, ResponseProjectFirstCatagory.class);
                Log.i(TAG, "onSuccess: " + firstCatas);
                showFirstCata();
                initSecondCatagory(firstCatas.iData.get(0).ID);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.i(TAG, "onError: " + ex.toString());
                Toast.makeText(getContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Toast.makeText(getContext(), "cancelled", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFinished() {

            }
        });
    }

    /*获取二级分类*/
    public void initSecondCatagory(String parent) {
        RequestParams params = new RequestParams(CONSTS.SecondCatagory);
        params.addQueryStringParameter("parent", parent);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                /*获取数据*/
                Gson gson = new Gson();
                secondCatas = gson.fromJson(result, String[].class);
                /*显示二级分类*/
                project_second_catagory.setAdapter(new ProjectSecondCataAdapter(secondCatas));
                /*初始化产品列表*/
                initProjectList(secondCatas[0]);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(getContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Toast.makeText(getContext(), "cancelled", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFinished() {

            }
        });
    }

    /*初始化产品列表*/
    public void initProjectList(String cata) {
        RequestParams params = new RequestParams(CONSTS.ProjectListUrl);
        params.addQueryStringParameter("cata", cata);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                String[] temString = gson.fromJson(result, String[].class);
                shopList = new LinkedList<Shop>();
                for (int i = 0; i < temString.length; i++) {
                    shopList.add(new Shop(temString[i], "3000", R.drawable.buy, "单位描述", "盒", "200"));
                }
                project_content_list.setAdapter(new ProjectListAdapter(shopList, getContext()));
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(getContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Toast.makeText(getContext(), "cancelled", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFinished() {

            }
        });
    }

    public class FirstCataHolder {
        @ViewInject(R.id.project_item_first_catagory_text)
        public TextView project_item_first_catagory_text;
    }

    public class ProjectSecondCataAdapter extends BaseAdapter {
        private String[] catagory;
        public ProjectSecondCataAdapter(String[] catagory){
            this.catagory = catagory;
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

            final int k = position;
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
                    initProjectList(catagory[k]);
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
}