package me.hsky.androidshop.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.xutils.x;

import me.hsky.androidshop.R;
import me.hsky.androidshop.UserLogin;
import me.hsky.androidshop.utils.SharedUtils;

public class MainBuy extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_buy, null);

        x.view().inject(this, view);

        if(!SharedUtils.getUserLoginStatus(getContext())){
            startActivityForResult(new Intent(getContext(), UserLogin.class), 2);
        }

        /*显示购物车*/
        return view;
    }
}
