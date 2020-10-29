package com.indiaudyogmart.login;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.CountDownTimer;
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
import com.indiaudyogmart.utils.LocaleHelper;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class OtpAcitivity extends AppCompatActivity {

    @BindView(R.id.iv_1)
    ImageView iv1;
    @BindView(R.id.iv_2)
    ImageView iv2;
    @BindView(R.id.tv_mart)
    TextView tvMart;
    @BindView(R.id.tv_des)
    TextView tvDes;
    @BindView(R.id.tv_otp)
    TextView tvOtp;
    @BindView(R.id.iv3)
    ImageView iv3;
    @BindView(R.id.et_otp)
    EditText etOtp;
    @BindView(R.id.text_view_countdown)
    TextView textViewCountdown;
    @BindView(R.id.fl_1)
    FrameLayout fl1;
    @BindView(R.id.btn_verifyotp)
    Button btnVerifyotp;
    @BindView(R.id.ll_contain)
    LinearLayout llContain;
    @BindView(R.id.tv_otprecive)
    TextView tvOtprecive;
    @BindView(R.id.tv_resend)
    TextView tvResend;
    @BindView(R.id.ll_resendotp)
    LinearLayout llResendotp;
    String email;
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


        setContentView(R.layout.activity_otp);
        ButterKnife.bind(this);
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
            tvOtprecive.setVisibility(View.GONE);
            tvResend.setVisibility(View.GONE);
            llResendotp.setVisibility(View.GONE);
            new CountDownTimer(60000, 1000) {

                public void onTick(long millisUntilFinished) {
                    textViewCountdown.setText("00:" + millisUntilFinished / 1000);
                }

                public void onFinish() {
                    tvOtprecive.setVisibility(View.VISIBLE);
                    tvResend.setVisibility(View.VISIBLE);
                    llResendotp.setVisibility(View.VISIBLE);
                }
            }.start();
            if(getIntent().getExtras()!=null && getIntent().getExtras().containsKey(Constants.email))
            email=getIntent().getExtras().getString(Constants.email);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick({R.id.btn_verifyotp, R.id.tv_otprecive, R.id.tv_resend, R.id.ll_resendotp})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_verifyotp:
                verifyotp();
                break;
            case R.id.tv_otprecive:
                resendotp();
                break;
            case R.id.tv_resend:
                resendotp();
                break;
            case R.id.ll_resendotp:
                resendotp();
                break;
        }
    }

    private void resendotp() {
        try {
            if (CommonFunctions.checkConnection(this)) {
                String url = IndiaMartConfig.WEBURL + IndiaMartConfig.resend_forgot_password;
                Map<String, String> mParams = new HashMap<>();
                mParams.put(Constants.email, email);
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
                                    if (response.getBoolean(Constants.status)) {
                                        tvOtprecive.setVisibility(View.GONE);
                                        tvResend.setVisibility(View.GONE);
                                        llResendotp.setVisibility(View.GONE);
                                        new CountDownTimer(60000, 1000) {

                                            public void onTick(long millisUntilFinished) {
                                                textViewCountdown.setText("00:" + millisUntilFinished / 1000);
                                            }

                                            public void onFinish() {
                                                tvOtprecive.setVisibility(View.VISIBLE);
                                                tvResend.setVisibility(View.VISIBLE);
                                                llResendotp.setVisibility(View.VISIBLE);
                                            }
                                        }.start();
                                    } else {
                                        Toast.makeText(OtpAcitivity.this, response.getString(Constants.message), Toast.LENGTH_SHORT).show();
                                    }

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onError(ANError error) {
                                try {
                                    CommonFunctions.destroyProgressBar();
                                    Toast.makeText(OtpAcitivity.this, R.string.msg_server_error, Toast.LENGTH_SHORT).show();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void verifyotp() {
        try {
            if(etOtp.getText().toString().trim().length()==0)
            {
                Toast.makeText(this, R.string.err_invalid_otp, Toast.LENGTH_SHORT).show();
                return;
            }
            if (CommonFunctions.checkConnection(this)) {
                String url = IndiaMartConfig.WEBURL + IndiaMartConfig.forgot_password_validate_otp;
                Map<String, String> mParams = new HashMap<>();
                mParams.put(Constants.email, email);
                mParams.put(Constants.otp, etOtp.getText().toString());
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
                                    if (response.getBoolean(Constants.status)) {
                                        Toast.makeText(OtpAcitivity.this, response.getString(Constants.message), Toast.LENGTH_SHORT).show();
                                        Intent otp=new Intent(OtpAcitivity.this,Reset_Password.class);
                                        otp.putExtra(Constants.email,email);
                                        startActivity(otp);
                                    } else {
                                        Toast.makeText(OtpAcitivity.this, response.getString(Constants.message), Toast.LENGTH_SHORT).show();
                                    }

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onError(ANError error) {
                                try {
                                    CommonFunctions.destroyProgressBar();
                                    Toast.makeText(OtpAcitivity.this, R.string.msg_server_error, Toast.LENGTH_SHORT).show();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}