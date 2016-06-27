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
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import me.hsky.androidshop.CityActivity;
import me.hsky.androidshop.R;
import me.hsky.androidshop.adapter.IndexContent;
import me.hsky.androidshop.adapter.IndexSalePromotion;
import me.hsky.androidshop.adapter.IndexSalePromotionSmall;
import me.hsky.androidshop.data.Shop;
import me.hsky.androidshop.utils.SharedUtils;

public class MainHome extends Fragment implements AMapLocationListener{

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

    public AMapLocationClient mLocationClient = null;

    private double lat;
    private double lon;

    //声明mLocationOption对象
    public AMapLocationClientOption mLocationOption = null;

    public boolean isLocation = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_home, null);
        x.view().inject(this, view);
        /*获取数据并且显示*/

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
//        cityName = aMapLocation.getCity();

        Log.i(TAG, "onStart: 开始定位");
        mLocationClient = new AMapLocationClient(getContext());
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        mLocationClient.setLocationListener(this);
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);

//        mLocationOption.setInterval(2000);
        //设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        mLocationClient.startLocation();

        cityName = SharedUtils.getCityName(getActivity().getBaseContext());

        Log.i(TAG, "onStart: " + cityName);
        if(cityName.isEmpty()){
            isLocation = false;
        }else{
            isLocation = true;
            handler.sendEmptyMessage(1);
        }


//        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
//        boolean isOpen = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
//
//        /*没有打开gps*/
//        if (!isOpen) {
//            Intent intent = new Intent();
//            intent.setAction(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivityForResult(intent, 0);
//        }
//
//        /*开始定位*/
//        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
//        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 10, this);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        /*保存当前定位城市*/
//        SharedUtils.setCityName(getActivity(), cityName);
//        Log.i(TAG, "onDestroy: 定位结束");
        /*结束定位*/
//        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
//        locationManager.removeUpdates(this);
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

    @Event(R.id.index_top_city)
    private void selectCity(View v) {
        getActivity().startActivityForResult(new Intent(getContext(), CityActivity.class), 3);
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        /*定位回调*/
        if (aMapLocation != null && !isLocation) {
            Log.i(TAG, "onLocationChanged: " + aMapLocation);
            if (aMapLocation.getErrorCode() == 0) {
                //定位成功回调信息，设置相关消息
                aMapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                aMapLocation.getLatitude();//获取纬度
                aMapLocation.getLongitude();//获取经度
                aMapLocation.getAccuracy();//获取精度信息
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date(aMapLocation.getTime());
                df.format(date);//定位时间
                cityName = aMapLocation.getCity();
                handler.sendEmptyMessage(1);
                isLocation = true;
            } else {
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                Log.i(TAG, "location Error, ErrCode:"
                        + aMapLocation.getErrorCode() + ", errInfo:"
                        + aMapLocation.getErrorInfo());
                Toast.makeText(getActivity().getBaseContext(), aMapLocation.getErrorInfo(), Toast.LENGTH_SHORT).show();
                isLocation = true;
            }
        }
    }
}
