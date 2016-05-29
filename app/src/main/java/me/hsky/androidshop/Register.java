package me.hsky.androidshop;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import me.hsky.androidshop.data.ResponseResult;

public class Register extends Activity {

    private static final String TAG = "tag";
    @ViewInject(R.id.forgetpass_back)
    private ImageView forgetPassBack;

    @ViewInject(R.id.register_name)
    private EditText name;
    @ViewInject(R.id.register_address)
    private EditText address;
    @ViewInject(R.id.register_email)
    private EditText email;
    @ViewInject(R.id.register_mobile)
    private EditText mobile;
    @ViewInject(R.id.register_pass)
    private EditText pass;
    @ViewInject(R.id.forgetpass_repass)
    private EditText repass;
    @ViewInject(R.id.company)
    private EditText company;
    @ViewInject(R.id.icard)
    private EditText icard;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        x.view().inject(this);
    }

    @Event(R.id.register_action_btn)
    private void onRegisterClick(View v){
        register();
    }

    private void register() {
        // Reset errors.
        name.setError(null);
        address.setError(null);
        email.setError(null);
        mobile.setError(null);
        pass.setError(null);
        repass.setError(null);
        company.setError(null);
        icard.setError(null);

        // Store values at the time of the login attempt.
        String nameStr = name.getText().toString();
        String addressStr = address.getText().toString();
        String emailStr = email.getText().toString();
        String mobileStr = mobile.getText().toString();
        String passStr = pass.getText().toString();
        String repassStr = repass.getText().toString();
        String companyStr = company.getText().toString();
        String icardStr = icard.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid email address.
        if (TextUtils.isEmpty(nameStr)) {
            name.setError("姓名不能为空");
            focusView = name;
            cancel = true;
        }else{
            // Check for a valid password, if the user entered one.
            if(TextUtils.isEmpty(addressStr)){
                address.setError("地址不能为空");
                focusView = address;
                cancel  = true;
            }else{
                if(TextUtils.isEmpty(emailStr)){
                    email.setError("邮箱不能为空");
                    focusView = email;
                    cancel  = true;
                }else{
                    if(TextUtils.isEmpty(mobileStr)){
                        mobile.setError("手机号不能为空");
                        focusView = mobile;
                        cancel  = true;
                    }else{
                        if(TextUtils.isEmpty(passStr)){
                            pass.setError("密码不能为空");
                            focusView = pass;
                            cancel  = true;
                        }else{
                            if(TextUtils.isEmpty(repassStr)){
                                repass.setError("重复密码不能为空");
                                focusView = repass;
                                cancel  = true;
                            }else{
                                if(TextUtils.isEmpty(companyStr)){
                                    company.setError("营业执照号不能为空");
                                    focusView = company;
                                    cancel  = true;
                                }else{
                                    if(TextUtils.isEmpty(icardStr)){
                                        icard.setError("身份证不能为空");
                                        focusView = icard;
                                        cancel  = true;
                                    }
                                }
                            }
                        }
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
            RequestParams params = new RequestParams(CONSTS.RegisterUrl);
            params.addQueryStringParameter("name", nameStr);
            params.addQueryStringParameter("address", addressStr);
            params.addQueryStringParameter("email", emailStr);
            params.addQueryStringParameter("mobile", mobileStr);
            params.addQueryStringParameter("pass", passStr);
            params.addQueryStringParameter("repass", repassStr);
            params.addQueryStringParameter("company", companyStr);
            params.addQueryStringParameter("icard", icardStr);
            Log.i(TAG, "attemptLogin: " + params);
            x.http().get(params, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    Log.i(TAG, "onSuccess: " + result);
                    Gson gson = new Gson();

                    ResponseResult registerInfo = gson.fromJson(result, ResponseResult.class);

                    Toast.makeText(getBaseContext(), registerInfo.message, Toast.LENGTH_SHORT).show();
                    if (1 == registerInfo.result) {
                        /*返回上一个activity*/
                        /*记录登录状态*/
//                        SharedUtils.setUserLoginStatus(getBaseContext(), true);
                        setResult(1, new Intent());
//                        startActivity(new Intent(getBaseContext(), UserLogin.class));
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
