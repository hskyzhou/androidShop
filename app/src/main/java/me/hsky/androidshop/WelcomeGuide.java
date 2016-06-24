package me.hsky.androidshop;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ViewUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

public class WelcomeGuide extends Activity {
    @ViewInject(R.id.welcome_btn)
    private Button btn;

    @ViewInject(R.id.welcome_pager)
    private ViewPager pager;

    private List<View> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_guide);

        x.view().inject(this);
//        x.Ext.setDebug(BuildConfig.DEBUG);

        initViewPager();

    }

    @Event(R.id.welcome_btn)
    private void click(View view){
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }

    /*初始化viewpager*/
    public void initViewPager(){
        list = new ArrayList<View>();
        ImageView iv = new ImageView(this);
        iv.setImageResource(R.drawable.guide_01);
        list.add(iv);

        ImageView iv1 = new ImageView(this);
        iv1.setImageResource(R.drawable.guide_02);
        list.add(iv1);

        ImageView iv2 = new ImageView(this);
        iv2.setImageResource(R.drawable.guide_03);
        list.add(iv2);

        pager.setAdapter(new MyPagerAdapter());
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position == 2){
                    btn.setVisibility(View.VISIBLE);
                }else{
                    btn.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageSelected(int position) {}

            @Override
            public void onPageScrollStateChanged(int state) {}
        });
    }

    /*定义 viewPager适配器*/
    class MyPagerAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        /*初始化item实例方法*/
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(list.get(position));
            return list.get(position);
//            return super.instantiateItem(container, position);
        }

        /*item销毁方法*/
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
//            super.destroyItem(container, position, object);
            container.removeView(list.get(position));
        }
    }
}
