package me.hsky.androidshop;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

public class Forgetpassword extends AppCompatActivity {

    @ViewInject(R.id.forgetpass_back)
    private ImageView forgetPassBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpassword);

        x.view().inject(this);
    }

    @Event(R.id.forgetpass_back)
    private void onClickForgetPassBack(View v){
        finish();
    }



}
