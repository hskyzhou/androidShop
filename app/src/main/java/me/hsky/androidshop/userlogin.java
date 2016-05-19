package me.hsky.androidshop;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import me.hsky.androidshop.consts.CONSTS;
import me.hsky.androidshop.data.ResponseLogin;
import me.hsky.androidshop.utils.SharedUtils;

public class UserLogin extends Activity {
    private static final String TAG = "tag";
    /*ui interface*/
    @ViewInject(R.id.username)
    private AutoCompleteTextView username;
    @ViewInject(R.id.pwd)
    private TextView pwd;
    @ViewInject(R.id.btnLogin)
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userlogin);

        /*引入注解*/
        x.view().inject(this);
    }

    @Event(R.id.btnLogin)
    private void onLoginClick(View view){
//        Toast.makeText(getBaseContext(), "点击登录", Toast.LENGTH_SHORT).show();
        attemptLogin();
    }

    private void attemptLogin() {
        // Reset errors.
        username.setError(null);
        pwd.setError(null);

        // Store values at the time of the login attempt.
        String email = username.getText().toString();
        String password = pwd.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            username.setError("用户名不能为空");
            focusView = username;
            cancel = true;
        }else{
            // Check for a valid password, if the user entered one.
            if(TextUtils.isEmpty(password)){
                pwd.setError("密码不能为空");
                focusView = pwd;
                cancel  = true;
            }
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            /*调用接口--进行登录*/
            RequestParams params = new RequestParams(CONSTS.LoginUrl);

            params.addQueryStringParameter("username",email );
            params.addQueryStringParameter("password", password);
            Log.i(TAG, "attemptLogin: " + params);
            x.http().get(params, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    Gson gson = new Gson();

                    ResponseLogin loginInfo = gson.fromJson(result, ResponseLogin.class);

                    Toast.makeText(getBaseContext(), loginInfo.message, Toast.LENGTH_SHORT).show();
                    if (1 == loginInfo.result) {
                        /*返回上一个activity*/
                        /*记录登录状态*/
                        SharedUtils.setUserLoginStatus(getBaseContext(), true);
                        setResult(2, new Intent());
                        finish();
                    }
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    Log.i(TAG, "onError: " + ex.toString());
                    Toast.makeText(getBaseContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
                }

                @Override
                public void onCancelled(CancelledException cex) {
                    Toast.makeText(getBaseContext(), "cancelled", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFinished() {

                }
            });
        }
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }
}
