package com.indiaudyogmart.login;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.TextUtils;
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


public class Signup_Seller_Company extends AppCompatActivity {

    @BindView(R.id.iv_1)
    ImageView iv1;
    @BindView(R.id.iv_2)
    ImageView iv2;
    @BindView(R.id.tv_mart)
    TextView tvMart;
    @BindView(R.id.tv_des)
    TextView tvDes;
    @BindView(R.id.tv_companydetails)
    TextView tvCompanydetails;
    @BindView(R.id.iv_3)
    ImageView iv3;
    @BindView(R.id.et_companyname)
    EditText etCompanyname;
    @BindView(R.id.rl_1)
    RelativeLayout rl1;
    @BindView(R.id.iv_4)
    ImageView iv4;
    @BindView(R.id.et_companylocation)
    EditText etCompanylocation;
    @BindView(R.id.rl_2)
    RelativeLayout rl2;
    @BindView(R.id.iv_5)
    ImageView iv5;
    @BindView(R.id.et_companywebsite)
    EditText etCompanywebsite;
    @BindView(R.id.rl_3)
    RelativeLayout rl3;
    @BindView(R.id.btn_back)
    Button btnBack;
    @BindView(R.id.ll_signup)
    LinearLayout llSignup;
    @BindView(R.id.btn_signup_seller)
    Button btnSignupSeller;
    @BindView(R.id.ll_buyer)
    LinearLayout llBuyer;
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
    String name = "", phone, password,location,email;
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


        setContentView(R.layout.activity_signup__seller__company);

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
            if (getSupportActionBar() != null)
                getSupportActionBar().hide();

            if (getIntent().getExtras() != null && getIntent().getExtras().containsKey(Constants.name))
                name = getIntent().getExtras().getString(Constants.name);

            if (getIntent().getExtras() != null && getIntent().getExtras().containsKey(Constants.email))
                email = getIntent().getExtras().getString(Constants.name);

            if (getIntent().getExtras() != null && getIntent().getExtras().containsKey(Constants.phone))
                phone = getIntent().getExtras().getString(Constants.phone);

            if (getIntent().getExtras() != null && getIntent().getExtras().containsKey(Constants.password))
                password = getIntent().getExtras().getString(Constants.phone);

            if (getIntent().getExtras() != null && getIntent().getExtras().containsKey(Constants.address))
                location = getIntent().getExtras().getString(Constants.address);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick({R.id.btn_back, R.id.btn_signup_seller, R.id.ll_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_signup_seller:
                register();
                break;
            case R.id.ll_login:
                Intent signup3 = new Intent(this, LoginActivity.class);
                startActivity(signup3);
                finishAffinity();
                break;
        }
    }

    private void register() {
        try {
            if (TextUtils.isEmpty(etCompanyname.getText().toString())) {
                Toast.makeText(this, R.string.register_error8, Toast.LENGTH_SHORT).show();
                etCompanyname.requestFocus();
                return;
            }
          else if (TextUtils.isEmpty(etCompanylocation.getText().toString())) {
                Toast.makeText(this, R.string.register_error9, Toast.LENGTH_SHORT).show();
                etCompanyname.requestFocus();
                return;
            }
          else
            {
                signuptoserver();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void signuptoserver() {
        try {
            String url = IndiaMartConfig.WEBURL + IndiaMartConfig.signup_seller;
            Map<String, String> mParams = new HashMap<>();
            mParams.put(Constants.name, name);
            mParams.put(Constants.contactno, phone);
            mParams.put(Constants.email, email);
            mParams.put(Constants.password, password);
            mParams.put(Constants.locality, location);
            mParams.put(Constants.company_name, etCompanyname.getText().toString().trim()+"");
            mParams.put(Constants.company_url, etCompanywebsite.getText().toString().trim()+"");
            mParams.put(Constants.company_location, etCompanylocation.getText().toString().trim()+"");
            
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
                                    CommonFunctions.setPreference(Signup_Seller_Company.this, Constants.isLogin, true);
                                    CommonFunctions.setPreference(getApplicationContext(), Constants.userdata, gson.toJson(loginResponse));
                                    CommonFunctions.changeactivity(Signup_Seller_Company.this, DashboardActivity.class);
                                    finishAffinity();
                                } else {
                                    Toast.makeText(Signup_Seller_Company.this, loginResponse.message, Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onError(ANError error) {
                            try {
                                CommonFunctions.destroyProgressBar();
                                Toast.makeText(Signup_Seller_Company.this, R.string.msg_server_error, Toast.LENGTH_SHORT).show();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}