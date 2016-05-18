package me.hsky.androidshop;

import android.os.AsyncTask;
import android.os.Bundle;
import android.renderscript.Type;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.w3c.dom.Text;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.Collection;

import me.hsky.androidshop.consts.CONSTS;
import me.hsky.androidshop.data.ResponseLogin;

public class UserLogin extends AppCompatActivity {
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
                    Log.i(TAG, "onSuccess: " + result);
                    Gson gson = new Gson();
//                    Type collectionType = (Type) new TypeToken<Collection<ResponseLogin>>(){}.getType();
//                    Collection<ResponseLogin> loginInfo = gson.fromJson(result, (java.lang.reflect.Type) collectionType);

                    ResponseLogin loginInfo = gson.fromJson(result, ResponseLogin.class);
                    Log.i(TAG, "onSuccess: " + loginInfo.message);
                    Log.i(TAG, "onSuccess: " + loginInfo.result);

                    Toast.makeText(getBaseContext(), loginInfo.message, Toast.LENGTH_SHORT).show();
                    if (loginInfo.result == "1") {
                        /*返回上一个activity*/

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
