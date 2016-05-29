package me.hsky.androidshop;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import me.hsky.androidshop.consts.CONSTS;
import me.hsky.androidshop.data.ResponseLogin;
import me.hsky.androidshop.data.ResponseResult;
import me.hsky.androidshop.fragment.MainHome;
import me.hsky.androidshop.utils.SharedUtils;

public class Forgetpassword extends Activity {

    private static final String TAG = "tag";
    @ViewInject(R.id.forgetpass_back)
    private ImageView forgetPassBack;

    @ViewInject(R.id.forgetpass_action_btn)
    private ImageView forgetpass_action_btn;

    @ViewInject(R.id.forgetpass_mobile)
    private EditText mobile;
    @ViewInject(R.id.forgetpass_code)
    private EditText code;
    @ViewInject(R.id.forgetpass_pass)
    private EditText pass;
    @ViewInject(R.id.forgetpass_repass)
    private EditText repass;


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


    @Event(R.id.forgetpass_action_btn)
    private void onForgetPassClick(View view){
//        Toast.makeText(getBaseContext(), "点击登录", Toast.LENGTH_SHORT).show();
        forgetPass();
    }

    private void forgetPass() {
        // Reset errors.
        mobile.setError(null);
        code.setError(null);
        pass.setError(null);
        repass.setError(null);

        // Store values at the time of the login attempt.
        String mobileStr = mobile.getText().toString();
        String passwordStr = pass.getText().toString();
        String repasswordStr = repass.getText().toString();
        String codeStr = code.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid email address.
        if (TextUtils.isEmpty(mobileStr)) {
            mobile.setError("手机号不能为空");
            focusView = mobile;
            cancel = true;
        }else{
            // Check for a valid password, if the user entered one.
            if(TextUtils.isEmpty(codeStr)){
                code.setError("验证码不能为空");
                focusView = code;
                cancel  = true;
            }else{
                if(TextUtils.isEmpty(passwordStr)){
                    pass.setError("密码不能为空");
                    focusView = pass;
                    cancel  = true;
                }else{
                    if(TextUtils.isEmpty(repasswordStr)){
                        repass.setError("重复密码不能为空");
                        focusView = repass;
                        cancel  = true;
                    }
                }
            }
        }
        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            /*调用接口--进行登录*/
            RequestParams params = new RequestParams(CONSTS.ForgetPassUrl);
            params.addQueryStringParameter("mobile",mobileStr);
            params.addQueryStringParameter("code", codeStr);
            params.addQueryStringParameter("pass", passwordStr);
            params.addQueryStringParameter("repass", repasswordStr);
            Log.i(TAG, "attemptLogin: " + params);
            x.http().get(params, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    Log.i(TAG, "onSuccess: " + result);
                    Gson gson = new Gson();

                    ResponseResult forgetPassInfo = gson.fromJson(result, ResponseResult.class);

                    Toast.makeText(getBaseContext(), forgetPassInfo.message, Toast.LENGTH_SHORT).show();
                    if (1 == forgetPassInfo.result) {
                        /*返回上一个activity*/
                        /*记录登录状态*/
//                        SharedUtils.setUserLoginStatus(getBaseContext(), true);
                        setResult(1, new Intent());
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
}
