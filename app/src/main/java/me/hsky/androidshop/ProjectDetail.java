package me.hsky.androidshop;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

public class ProjectDetail extends Activity {
    public final static String TAG = "tag";
    @ViewInject(R.id.project_btn_back)
    private ImageView project_btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_detail);
        x.view().inject(this);
    }

    @Event(R.id.project_btn_back)
    private void onClickBtnBack(View v){
        Log.i(TAG, "onClickBtnBack: " + "aaaa");
        finish();
    }
}
