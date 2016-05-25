package me.hsky.androidshop.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import me.hsky.androidshop.MainActivity;
import me.hsky.androidshop.R;
import me.hsky.androidshop.UserLogin;
import me.hsky.androidshop.utils.SharedUtils;

public class MainMe extends Fragment {
    @ViewInject(R.id.logout)
    private Button logout;

    @ViewInject(R.id.me_login_btn)
    private  Button me_login_btn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (SharedUtils.getUserLoginStatus(getContext())) {

            View view = inflater.inflate(R.layout.main_me_has_login, null);

            x.Ext.init(getActivity().getApplication());
            x.view().inject(this, view);
            return view;
        } else {
            View view = inflater.inflate(R.layout.main_me, null);
            x.view().inject(this, view);
            return view;
        }
    }

    @Event(R.id.logout)
    private void logoutClick(View view) {
        SharedUtils.setUserLoginStatus(getContext(), false);
        Toast.makeText(getContext(), "退出成功", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getContext(), MainActivity.class));
    }

    @Event(R.id.me_login_btn)
    private void loginClick(View view){
        startActivityForResult(new Intent(getContext(), UserLogin.class), 1);
        getActivity().finish();
    }

}
