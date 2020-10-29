package com.indiaudyogmart.login;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.android.material.dialog.InsetDialogOnTouchListener;
import com.google.gson.Gson;
import com.indiaudyogmart.R;
import com.indiaudyogmart.config.CommonFunctions;
import com.indiaudyogmart.config.Constants;
import com.indiaudyogmart.config.IndiaMartConfig;
import com.indiaudyogmart.dashboard.DashboardActivity;
import com.indiaudyogmart.model.LoginResponse;
import com.indiaudyogmart.utils.LocaleHelper;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.iv_1)
    ImageView iv1;
    @BindView(R.id.iv_2)
    ImageView iv2;
    @BindView(R.id.tv_mart)
    TextView tvMart;
    @BindView(R.id.tv_des)
    TextView tvDes;
    @BindView(R.id.tv_loginaccount)
    TextView tvLoginaccount;
    @BindView(R.id.iv3)
    ImageView iv3;
    @BindView(R.id.et_email)
    EditText etEmail;
    @BindView(R.id.fl_1)
    FrameLayout fl1;
    @BindView(R.id.iv_4)
    ImageView iv4;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.fl_2)
    FrameLayout fl2;
    @BindView(R.id.ck_remember)
    AppCompatCheckBox ckRemember;
    @BindView(R.id.fl_3)
    FrameLayout fl3;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.tv_forget)
    TextView tvForget;
    @BindView(R.id.ll_contain)
    LinearLayout llContain;
    @BindView(R.id.tv_donthaveaccount)
    TextView tvDonthaveaccount;
    @BindView(R.id.tv_createaccount)
    TextView tvCreateaccount;
    String lang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        lang = CommonFunctions.getPreference(this, Constants.defalt_languge, "en");
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());


        setContentView(R.layout.activity_login);

        init();
    }
    @Override
    protected void attachBaseContext(Context newBase) {
        //super.attachBaseContext(newBase);
        lang = CommonFunctions.getPreference(newBase, Constants.defalt_languge, "en");
        super.attachBaseContext(LocaleHelper.onAttach(newBase, lang));
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
    }
    private void init() {
        try {
            ButterKnife.bind(this);
            if(getSupportActionBar()!=null)
                getSupportActionBar().hide();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick({R.id.btn_login, R.id.tv_donthaveaccount, R.id.tv_createaccount,R.id.ll_signup,R.id.tv_forget})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                loginClick();
                break;
            case R.id.tv_donthaveaccount:
                Intent signup1=new Intent(LoginActivity.this,Signup_Normal.class);
                startActivity(signup1);
                break;
            case R.id.tv_createaccount:
                Intent signup2=new Intent(LoginActivity.this,Signup_Normal.class);
                startActivity(signup2);
                break;
            case R.id.ll_signup:
                Intent signup3=new Intent(LoginActivity.this,Signup_Normal.class);
                startActivity(signup3);
                break;
            case R.id.tv_forget:
                Intent forget=new Intent(LoginActivity.this,Forget_Password.class);
                startActivity(forget);
                break;
        }
    }
    public void loginClick() {
        try {
            String emailvalid = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
            if (etEmail.getText().toString().trim().length() == 0) {

                Toast.makeText(this, getString(R.string.err_email_mobil), Toast.LENGTH_SHORT).show();
            } else if (etPassword.getText().toString().trim().length() == 0) {

                Toast.makeText(this, getString(R.string.err_pwd), Toast.LENGTH_SHORT).show();
            } else {
                loginUser(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void loginUser(boolean b) {
        try {
            if (CommonFunctions.checkConnection(this)) {
                String url = IndiaMartConfig.WEBURL + IndiaMartConfig.login;
                Map<String, String> mParams = new HashMap<>();
                mParams.put(Constants.username, etEmail.getText().toString().trim());
                mParams.put(Constants.password, etPassword.getText().toString().trim());
                mParams.put(Constants.device_id, CommonFunctions.getDeviceUID(this));
                mParams.put(Constants.device_token, CommonFunctions.getPreference(this, Constants.device_token, "nofound"));
                mParams.put(Constants.device_type, Constants.android);
                mParams.put(Constants.device_name, CommonFunctions.getDeviceMenufacture());
                Log.e("url", url);
                Log.e("mParams", mParams.toString());

                CommonFunctions.createProgressBar(this, getString(R.string.msg_please_wait));
                AndroidNetworking.post(url)
                        .addBodyParameter(mParams)
                        .setTag(url)
                        .setPriority(Priority.HIGH)
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    CommonFunctions.destroyProgressBar();
                                    Log.e("res", response.toString());
                                    Gson gson = new Gson();
                                    LoginResponse loginResponse = gson.fromJson(response.toString(), LoginResponse.class);
                                    if (loginResponse.status ) {
                                        CommonFunctions.setPreference(LoginActivity.this, Constants.isLogin, true);
                                        CommonFunctions.setPreference(getApplicationContext(), Constants.userdata, gson.toJson(loginResponse));
                                        CommonFunctions.changeactivity(LoginActivity.this, DashboardActivity.class);
                                    } else {
                                        Toast.makeText(LoginActivity.this, loginResponse.message, Toast.LENGTH_LONG).show();
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onError(ANError error) {
                                try {
                                    CommonFunctions.destroyProgressBar();
                                    Toast.makeText(LoginActivity.this, R.string.msg_server_error, Toast.LENGTH_SHORT).show();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });

            }
        } catch (Exception e) {
            e.printStackTrace();
            CommonFunctions.destroyProgressBar();
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}