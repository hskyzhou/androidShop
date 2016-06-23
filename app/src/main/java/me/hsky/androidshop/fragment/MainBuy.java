package me.hsky.androidshop.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.w3c.dom.Text;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import me.hsky.androidshop.ProjectDetail;
import me.hsky.androidshop.R;
import me.hsky.androidshop.UserLogin;
import me.hsky.androidshop.consts.CONSTS;
import me.hsky.androidshop.data.ResponseCart;
import me.hsky.androidshop.data.ResponseProjectFirstCatagory;
import me.hsky.androidshop.utils.SharedUtils;

public class MainBuy extends Fragment {
    private static final String TAG = "tag";

    @ViewInject(R.id.cart_show)
    private LinearLayout cart_show;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_buy, null);
        x.Ext.init(getActivity().getApplication());
        x.view().inject(this, view);
        /*显示购物车*/
        showCart();
        return view;
    }

    public void showCart(){
        RequestParams params = new RequestParams(CONSTS.CartUrl);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                ResponseCart cartInfo = gson.fromJson(result, ResponseCart.class);
                if(cartInfo.result == 1){
                    /*显示购物车产品*/
                    CartHolder cartHolder = null;
                    CartContentHolder cartContentHolder = null;
                    for (int i = 0; i < cartInfo.cart.size(); i++) {
                        cartHolder = new CartHolder();
                        View itemView = LayoutInflater.from(getContext()).inflate(R.layout.buy_project_category, null);
                        x.view().inject(cartHolder, itemView);
                        itemView.setTag(cartHolder);

                        cartHolder.buy_project_category.setText(cartInfo.cart.get(i).category_name);

                        if(i != 0){
                            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                            lp.setMargins(0, 20, 0, 0);
                            itemView.setLayoutParams(lp);
                        }
                        cart_show.addView(itemView);
                        for(int j=0; j<cartInfo.cart.get(i).shops.size(); j++){
                            cartContentHolder = new CartContentHolder();
                            View contentView1 = LayoutInflater.from(getContext()).inflate(R.layout.buy_project_content, null);
                            x.view().inject(cartContentHolder, contentView1);
                            contentView1.setTag(cartContentHolder);

                            cartContentHolder.buy_project_title.setText(cartInfo.cart.get(i).shops.get(j).shop_name);
                            cartContentHolder.buy_project_desc.setText(cartInfo.cart.get(i).shops.get(j).shop_desc);
                            cartContentHolder.buy_project_price.setText(cartInfo.cart.get(i).shops.get(j).shop_price);
                            cartContentHolder.buy_project_price_desc.setText(cartInfo.cart.get(i).shops.get(j).shop_standard);
                            cartContentHolder.buy_number.setText("" + cartInfo.cart.get(i).shops.get(j).shop_number);
                            cartContentHolder.buy_image.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    getContext().startActivity(new Intent(getContext(), ProjectDetail.class));
                                }
                            });
                            cart_show.addView(contentView1);
                        }
                    }
                    /*提交订单view*/
                    CartResultHolder cartResultHolder = new CartResultHolder();
                    View resultView = LayoutInflater.from(getContext()).inflate(R.layout.buy_project_result, null);
                    x.view().inject(cartResultHolder, resultView);
                    resultView.setTag(cartResultHolder);
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    lp.setMargins(0, 20, 0, 0);
                    resultView.setLayoutParams(lp);
                    cartResultHolder.buy_project_total_price.setText("" + cartInfo.total_price);
                    cartResultHolder.buy_project_category_number.setText("" + cartInfo.category_total);
                    cart_show.addView(resultView);
                }
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

    public class CartHolder{
        @ViewInject(R.id.buy_project_category)
        public TextView buy_project_category;
    }

    public class CartContentHolder{
        @ViewInject(R.id.buy_content)
        public RelativeLayout buy_content;

        @ViewInject(R.id.buy_image)
        public ImageView buy_image;

        @ViewInject(R.id.buy_project_title)
        public TextView buy_project_title;

        @ViewInject(R.id.buy_project_desc)
        public TextView buy_project_desc;

        @ViewInject(R.id.buy_project_price)
        public TextView buy_project_price;

        @ViewInject(R.id.buy_project_price_desc)
        public TextView buy_project_price_desc;

        @ViewInject(R.id.buy_number)
        public TextView buy_number;
    }

    public class CartResultHolder{
        @ViewInject(R.id.buy_project_total_price)
        public TextView buy_project_total_price;

        @ViewInject(R.id.buy_project_category_number)
        public TextView buy_project_category_number;

        @ViewInject(R.id.buy_project_result_submit)
        public TextView buy_project_result_submit;
    }
}
