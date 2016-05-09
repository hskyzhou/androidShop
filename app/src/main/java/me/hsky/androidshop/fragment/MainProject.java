package me.hsky.androidshop.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;
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
import me.hsky.androidshop.adapter.ProjectSecondCataAdapter;
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
    private String[] firstCatas = null;
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
        for (int i = 0; i < firstCatas.length; i++) {
            firstCataHolder = new FirstCataHolder();
            View itemView = LayoutInflater.from(getContext()).inflate(R.layout.project_first_catagory_item, null);
            x.view().inject(firstCataHolder, itemView);
            itemView.setTag(firstCataHolder);
            firstCataHolder.project_item_first_catagory_text.setText(firstCatas[i]);
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

                    initSecondCatagory(firstCatas[number] + "");
                    Toast.makeText(getContext(), firstCatas[number] + "", Toast.LENGTH_LONG).show();
                }
            });

            project_major_catagory_linear.addView(itemView);
        }
    }

    /*获取一级分类数据*/
    public void initFirstCatagory() {
        RequestParams params = new RequestParams("http://api.hsky.me/api/firstcata");
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                firstCatas = gson.fromJson(result, String[].class);
                showFirstCata();
                initSecondCatagory(firstCatas[0]);
                Toast.makeText(getContext(), result, Toast.LENGTH_LONG).show();
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

    /*显示二级分类*/
    public void showSecondCatagory() {
        project_second_catagory.setAdapter(new ProjectSecondCataAdapter(secondCatas));
    }

    /*获取二级分类*/
    public void initSecondCatagory(String parent) {
        RequestParams params = new RequestParams("http://api.hsky.me/api/secondcata");
        params.addQueryStringParameter("parent", parent);
        Log.i(TAG, "initSecondCatagory: " + params);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i(TAG, "onSuccess: " + result);
                Gson gson = new Gson();
                secondCatas = gson.fromJson(result, String[].class);
                showSecondCatagory();
                initProjectList(secondCatas[0]);
                Toast.makeText(getContext(), result, Toast.LENGTH_LONG).show();
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


    /*显示全部产品数据详情*/
    public void showProjectList() {
        project_content_list.setAdapter(new ProjectListAdapter(shopList, getContext()));
    }

    /*初始化产品列表*/
    public void initProjectList(String cata) {
        RequestParams params = new RequestParams("http://api.hsky.me/api/projectlist");
        params.addQueryStringParameter("cata", cata);
        Log.i(TAG, "initSecondCatagory: " + params);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i(TAG, "onSuccess: " + result);
                Gson gson = new Gson();
                String[] temString = gson.fromJson(result, String[].class);
                for (int i = 0; i < temString.length; i++) {
                    shopList.add(new Shop("产品3", "3000", R.drawable.buy, "单位描述", "盒", "200"));
                }
                showProjectList();
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
}