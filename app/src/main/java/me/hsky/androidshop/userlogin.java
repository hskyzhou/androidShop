package me.hsky.androidshop;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

public class UserLogin extends AppCompatActivity {
    /*ui interface*/
    @ViewInject(R.id.username)
    private AutoCompleteTextView username;
    @ViewInject(R.id.pwd)
    private TextView pwd;
    @ViewInject(R.id.btnLogin)
    private Button btnLogin;

    private UserLoginTask mAuthTask = null;

    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userlogin);

        /*引入注解*/
        x.view().inject(this);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        username.setError(null);
        pwd.setError(null);

        // Store values at the time of the login attempt.
        String email = username.getText().toString();
        String password = pwd.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            pwd.setError(getString(R.string.error_invalid_password));
            focusView = pwd;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            username.setError(getString(R.string.error_field_required));
            focusView = username;
            cancel = true;
        } else if (!isEmailValid(email)) {
            username.setError(getString(R.string.error_invalid_email));
            focusView = username;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
//            mAuthTask = new UserLoginTask(email, password);
//            mAuthTask.execute((Void) null);
            /*调用接口--进行登录*/

        }
    }

    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String loginName;
        private final String mPassword;

        UserLoginTask(String email, String password) {
            loginName = email;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            try {
                // Simulate network access.
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return false;
            }

            for (String credential : DUMMY_CREDENTIALS) {
                String[] pieces = credential.split(":");
                if (pieces[0].equals(loginName)) {
                    // Account exists, return true if the password matches.
                    return pieces[1].equals(mPassword);
                }
            }

            // TODO: register the new account here.
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;

            if (success) {
                finish();
            } else {
                pwd.setError(getString(R.string.error_incorrect_password));
                pwd.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
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
