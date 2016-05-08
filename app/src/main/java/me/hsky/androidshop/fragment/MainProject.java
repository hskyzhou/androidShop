package me.hsky.androidshop.fragment;

import android.app.Application;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.xutils.BuildConfig;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import me.hsky.androidshop.R;

public class MainProject extends Fragment {
    @ViewInject(R.id.project_major_catagory)
    private HorizontalScrollView project_major_catagory;
    @ViewInject(R.id.project_major_catagory_linear)
    private LinearLayout project_major_catagory_linear;
    private final String TAG = "tag";
    private String[] firstCatas = {
            "牙科",
            "心血管科",
            "神经科",
            "外科",
            "内科",
            "骨科",
            "眼科",
            "鼻科",
            "皮肤科"
    };
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_project, null);
        x.Ext.init(new Application());
        x.Ext.setDebug(BuildConfig.DEBUG);
        x.view().inject(this, view);
        /*初始顶级分类*/
        initFirstCatagory();
        initView();
        return view;
    }

    private void initView() {
        FirstCataHolder firstCataHolder = null;
        for(int i=0;i<firstCatas.length;i++){
            firstCataHolder = new FirstCataHolder();
            View itemView = LayoutInflater.from(getContext()).inflate(R.layout.project_first_catagory_item, null);
            x.view().inject(firstCataHolder, itemView);
            itemView.setTag(firstCataHolder);
            firstCataHolder.project_item_first_catagory_text.setText(firstCatas[i]);
            final int number = i;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(),firstCatas[number]+"" , Toast.LENGTH_LONG).show();
                }
            });

            project_major_catagory_linear.addView(itemView);
        }
    }

    public class FirstCataHolder{
        @ViewInject(R.id.project_item_first_catagory_text)
        public TextView project_item_first_catagory_text;
    }

    public void initFirstCatagory(){
        Log.i(TAG, "initFirstCatagory: ");
        RequestParams params = new RequestParams("http://127.0.0.1/api/firstcata");
        params.addQueryStringParameter("name", "hsky");
        Log.i(TAG, params + "");
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i(TAG, "onSuccess: " + result);
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

}
