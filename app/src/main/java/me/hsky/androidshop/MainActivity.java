package me.hsky.androidshop;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import me.hsky.androidshop.fragment.MainBuy;
import me.hsky.androidshop.fragment.MainHome;
import me.hsky.androidshop.fragment.MainMe;
import me.hsky.androidshop.fragment.MainProject;

public class MainActivity extends FragmentActivity implements RadioGroup.OnCheckedChangeListener {
    @ViewInject(R.id.group_bottom_btn)
    private RadioGroup group_bottom_btn;
    @ViewInject(R.id.main_home)
    private RadioButton main_home;

    private FragmentManager fm;
    Fragment[] cacheFragment = new Fragment[4];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        x.view().inject(this);

        fm = getSupportFragmentManager();

        main_home.setChecked(true);

        group_bottom_btn.setOnCheckedChangeListener(this);

        changeFragment(new MainHome(), false);


    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId){
            case R.id.main_home:

                changeFragment(new MainHome(), true);
                break;
            case R.id.main_project:
                changeFragment(new MainProject(), true);
                break;
            case R.id.main_buy:
                changeFragment(new MainBuy(), true);
                break;
            case R.id.main_me:
                changeFragment(new MainMe(), true);
                break;

        }
    }

    public void changeFragment(Fragment fragment, boolean isInit){
        FragmentTransaction transaction = fm.beginTransaction();

        transaction.replace(R.id.main_page, fragment);

        transaction.commit();
    }

}
