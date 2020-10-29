package com.indiaudyogmart.login;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
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

import static com.indiaudyogmart.config.CommonFunctions.emailvalid;


public class Signup_Normal extends AppCompatActivity {

    @BindView(R.id.iv_1)
    ImageView iv1;
    @BindView(R.id.iv_2)
    ImageView iv2;
    @BindView(R.id.tv_mart)
    TextView tvMart;
    @BindView(R.id.tv_des)
    TextView tvDes;
    @BindView(R.id.tv_createaccount)
    TextView tvCreateaccount;
    @BindView(R.id.iv_3)
    ImageView iv3;
    @BindView(R.id.et_fullname)
    EditText etFullname;
    @BindView(R.id.rl_1)
    RelativeLayout rl1;
    @BindView(R.id.iv_4)
    ImageView iv4;
    @BindView(R.id.et_email)
    EditText etEmail;
    @BindView(R.id.rl_2)
    RelativeLayout rl2;
    @BindView(R.id.iv_5)
    ImageView iv5;
    @BindView(R.id.et_mobile)
    EditText etMobile;
    @BindView(R.id.rl_3)
    RelativeLayout rl3;
    @BindView(R.id.iv_7)
    ImageView iv7;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.rl_5)
    RelativeLayout rl5;
    @BindView(R.id.iv_6)
    ImageView iv6;
    @BindView(R.id.et_location)
    EditText etLocation;
    @BindView(R.id.rl_4)
    RelativeLayout rl4;
    @BindView(R.id.btn_signup)
    Button btnSignup;
    @BindView(R.id.ll_signup)
    LinearLayout llSignup;
    @BindView(R.id.btn_seller)
    Button btnSeller;
    @BindView(R.id.ll_seller)
    LinearLayout llSeller;
    @BindView(R.id.rl_signup)
    RelativeLayout rlSignup;
    @BindView(R.id.ll_contain)
    LinearLayout llContain;
    @BindView(R.id.tv_alreadyhaveaccount)
    TextView tvAlreadyhaveaccount;
    @BindView(R.id.tv_login)
    TextView tvLogin;
    @BindView(R.id.ll_login)
    LinearLayout llLogin;
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


        setContentView(R.layout.activity_signup_normal);
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @OnClick({R.id.btn_signup, R.id.btn_seller, R.id.ll_login,R.id.tv_alreadyhaveaccount,R.id.tv_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_signup:
                singup();
                break;
            case R.id.btn_seller:
                Intent signup=new Intent(Signup_Normal.this,Signup_Seller.class);
                startActivity(signup);
                finish();
                break;
            case R.id.ll_login:
                finish();
                break;
            case R.id.tv_alreadyhaveaccount:
                finish();
                break;
            case R.id.tv_login:
                finish();
                break;
        }
    }

    private void singup() {
        try {
            if(CommonFunctions.checkConnection(this))
            {
                if (etFullname.getText().toString().length() == 0)
                {
                        Toast.makeText(this, R.string.err_name, Toast.LENGTH_SHORT).show();
                        etFullname.requestFocus();
                        return;
                }

                else if (etEmail.getText().toString().length() == 0)
                   {
                       Toast.makeText(this, R.string.err_email, Toast.LENGTH_SHORT).show();
                       etEmail.requestFocus();
                       return;
                   }
                else if (etMobile.getText().toString().length() == 0 || etMobile.getText().toString().length() < 10)
                {

                    Toast.makeText(this, R.string.err_mobil, Toast.LENGTH_SHORT).show();
                    etMobile.requestFocus();
                    return;
                }
                else if (etPassword.getText().toString().length() == 0)
                {

                    Toast.makeText(this, R.string.err_pwd, Toast.LENGTH_SHORT).show();
                    etPassword.requestFocus();
                    return;
                }
                else if (etPassword.getText().toString().length() < 6)
                {

                    Toast.makeText(this, R.string.err_pwd1, Toast.LENGTH_SHORT).show();
                    etPassword.requestFocus();
                    return;
                }
               /* else if (etConfirmPassword.getText().toString().length() == 0)
                    showAlertDialog(getString(R.string.app_name), getString(R.string.err_conf_pwd));
                else if (etConfirmPassword.getText().toString().length() < 6)
                    showAlertDialog(getString(R.string.app_name), getString(R.string.err_conf_pwd1));*/
                else if (etEmail.getText().toString().length() > 0 && !etEmail.getText().toString().trim().matches(emailvalid))
                {

                    Toast.makeText(this, R.string.err_valid_email, Toast.LENGTH_SHORT).show();
                    etEmail.requestFocus();
                    return;
                }
             /*   else if (!(etPassword.getText().toString()).equals(etConfirmPassword.getText().toString()))
                    showAlertDialog(getString(R.string.app_name), getString(R.string.err_mismatch));
                else if(!ckTermCondition.isChecked())
                {
                    showAlertDialog(getString(R.string.app_name), getString(R.string.err_accept_term));
                }*/
                else
                {
                    registeruser();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void registeruser() {
        try {
            if (CommonFunctions.checkConnection(this)) {
                String url = IndiaMartConfig.WEBURL + IndiaMartConfig.signup_user;
                Map<String, String> mParams = new HashMap<>();
                mParams.put(Constants.name, etFullname.getText().toString().trim());
                mParams.put(Constants.contactno, etMobile.getText().toString().trim());
                mParams.put(Constants.email, etEmail.getText().toString().trim());
                mParams.put(Constants.password, etPassword.getText().toString().trim());
                mParams.put(Constants.locality, etLocation.getText().toString().trim());
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
                                    if (loginResponse.status) {
                                        CommonFunctions.setPreference(Signup_Normal.this, Constants.isLogin, true);
                                        CommonFunctions.setPreference(getApplicationContext(), Constants.userdata, gson.toJson(loginResponse));
                                        CommonFunctions.changeactivity(Signup_Normal.this, DashboardActivity.class);
                                        finishAffinity();
                                    } else {
                                        Toast.makeText(Signup_Normal.this, loginResponse.message, Toast.LENGTH_SHORT).show();
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onError(ANError error) {
                                try {
                                    CommonFunctions.destroyProgressBar();
                                    Toast.makeText(Signup_Normal.this, R.string.msg_server_error, Toast.LENGTH_SHORT).show();
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
}