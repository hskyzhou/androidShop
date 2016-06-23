package me.hsky.androidshop.fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.SettingInjectorService;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import me.hsky.androidshop.R;
import me.hsky.androidshop.adapter.IndexContent;
import me.hsky.androidshop.adapter.IndexSalePromotion;
import me.hsky.androidshop.adapter.IndexSalePromotionSmall;
import me.hsky.androidshop.data.Shop;
import me.hsky.androidshop.utils.MyUtils;
import me.hsky.androidshop.utils.SharedUtils;

public class MainHome extends Fragment implements LocationListener {
    private static final String TAG = "tag";
    @ViewInject(R.id.index_top_city)
    private TextView topCity;

    /*当前城市名称*/
    private String cityName = "北京市";
    private LocationManager locationManager;

    /*秒杀*/
    @ViewInject(R.id.content_1)
    private GridView content_1;
    /*分类促销*/
    @ViewInject(R.id.index_sale_promotion)
    private GridView index_sale_promotion;
    /*分类促销2*/
    @ViewInject(R.id.index_sale_promotion_small)
    private GridView index_sale_promotion_small;

    private List<Shop> mData1 = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_home, null);
        x.view().inject(this, view);
        /*获取数据并且显示*/
        topCity.setText(SharedUtils.getCityName(getActivity()));
        mData1 = new LinkedList<Shop>();
        mData1.add(new Shop("产品1", "1000", R.drawable.buy));
        mData1.add(new Shop("产品2", "2000", R.drawable.buy));
        mData1.add(new Shop("产品3", "3000", R.drawable.buy));

        content_1.setAdapter(new IndexContent((LinkedList<Shop>) mData1, getContext()));
        index_sale_promotion.setAdapter(new IndexSalePromotion((LinkedList<Shop>) mData1, getContext()));
        index_sale_promotion_small.setAdapter(new IndexSalePromotionSmall((LinkedList<Shop>) mData1, getContext()));

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        /*检查gps模块*/
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        boolean isOpen = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        /*没有打开gps*/
        if (!isOpen) {
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivityForResult(intent, 0);
        }

        /*开始定位*/
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 10, this);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        /*保存当前定位城市*/
        SharedUtils.setCityName(getActivity(), cityName);
        /*结束定位*/
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.removeUpdates(this);
    }

    /*接受并且处理消息*/
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if(msg.what == 1){
                topCity.setText(cityName);
            }
            return false;
        }
    });

    /*位置信息更改*/
    @Override
    public void onLocationChanged(Location location) {
        Log.i("tag", "location changed");
        Log.i("tag", "onLocationChanged: " + location);
        double lat = 0.0;
        double lng = 0.0;
        if(location != null){
            lat = location.getLatitude();
            lng = location.getLongitude();
            Log.i(TAG, "经度是： " + lat + "纬度是：" + lng);
        }else{
            cityName = "北京市";
        }
        /*通过经纬度获取地址*/
        List<Address> list = null;
        Geocoder ge = new Geocoder(getActivity());

        try {
            list = ge.getFromLocation(lat, lng, 2);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.i(TAG, "list大小" + list.size());
        if(list != null && list.size() > 0){
            for (int i = 0; i< list.size(); i++) {
                Address ad = list.get(i);
                cityName = ad.getLocality();
                Log.i(TAG, "城市名" + cityName);
            }
        }
        handler.sendEmptyMessage(1);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.i("tag", "status changed");

    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.i("tag", "provider enable");

    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.i("tag", "provider disable");

    }


}
