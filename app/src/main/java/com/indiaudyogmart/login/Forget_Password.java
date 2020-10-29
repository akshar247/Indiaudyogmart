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


public class Forget_Password extends AppCompatActivity {

    @BindView(R.id.iv_1)
    ImageView iv1;
    @BindView(R.id.iv_2)
    ImageView iv2;
    @BindView(R.id.tv_mart)
    TextView tvMart;
    @BindView(R.id.tv_des)
    TextView tvDes;
    @BindView(R.id.tv_forgetpassword)
    TextView tvForgetpassword;
    @BindView(R.id.iv3)
    ImageView iv3;
    @BindView(R.id.et_email)
    EditText etEmail;
    @BindView(R.id.fl_1)
    FrameLayout fl1;
    @BindView(R.id.btn_forgotpassword)
    Button btnForgotpassword;
    @BindView(R.id.ll_contain)
    LinearLayout llContain;
    @BindView(R.id.tv_donthaveaccount)
    TextView tvDonthaveaccount;
    @BindView(R.id.tv_createaccount)
    TextView tvCreateaccount;
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


        setContentView(R.layout.activity_forget__password);

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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick({R.id.btn_forgotpassword, R.id.tv_donthaveaccount, R.id.tv_createaccount, R.id.ll_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_forgotpassword:
                forgotpassword();
                break;
            case R.id.tv_donthaveaccount:
                finish();
                break;
            case R.id.tv_createaccount:
                finish();
                break;
            case R.id.ll_login:
                finish();
                break;
        }
    }

    private void forgotpassword() {
        try {
            if (etEmail.getText().toString().trim().length() == 0) {

                Toast.makeText(this, getString(R.string.err_email_mobil), Toast.LENGTH_SHORT).show();
            }
            else
            {
                if (CommonFunctions.checkConnection(this)) {
                    String url = IndiaMartConfig.WEBURL + IndiaMartConfig.forgot_password;
                    Map<String, String> mParams = new HashMap<>();
                    mParams.put(Constants.email, etEmail.getText().toString().trim());
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
                                        if(response.getBoolean(Constants.status))
                                        {
                                            Toast.makeText(Forget_Password.this, response.getString(Constants.message), Toast.LENGTH_SHORT).show();
                                            Intent otp=new Intent(Forget_Password.this,OtpAcitivity.class);
                                            otp.putExtra(Constants.email,etEmail.getText().toString());
                                            startActivity(otp);
                                        }
                                        else
                                        {
                                            Toast.makeText(Forget_Password.this, response.getString(Constants.message), Toast.LENGTH_SHORT).show();
                                        }

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }

                                @Override
                                public void onError(ANError error) {
                                    try {
                                        CommonFunctions.destroyProgressBar();
                                        Toast.makeText(Forget_Password.this, R.string.msg_server_error, Toast.LENGTH_SHORT).show();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            CommonFunctions.destroyProgressBar();
        }
    }
}