package com.indiaudyogmart.login;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
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


public class Reset_Password extends AppCompatActivity {

    @BindView(R.id.iv_1)
    ImageView iv1;
    @BindView(R.id.iv_2)
    ImageView iv2;
    @BindView(R.id.tv_mart)
    TextView tvMart;
    @BindView(R.id.tv_des)
    TextView tvDes;
    @BindView(R.id.tv_enterpassword)
    TextView tvEnterpassword;
    @BindView(R.id.iv3)
    ImageView iv3;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.fl_1)
    FrameLayout fl1;
    @BindView(R.id.iv_4)
    ImageView iv4;
    @BindView(R.id.et_repassword)
    EditText etRepassword;
    @BindView(R.id.fl_2)
    FrameLayout fl2;
    @BindView(R.id.btn_sumbit)
    Button btnSumbit;
    @BindView(R.id.ll_contain)
    LinearLayout llContain;
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


        setContentView(R.layout.activity_reset_password);
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

            if(getIntent().getExtras()!=null && getIntent().getExtras().containsKey(Constants.email))
                email=getIntent().getExtras().getString(Constants.email);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @OnClick(R.id.btn_sumbit)
    public void onViewClicked() {
        try {
            if(CommonFunctions.checkConnection(this))
            {
                if (etPassword.getText().toString().length() == 0)
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
                else if (etRepassword.getText().toString().length() == 0)
                {
                    // showAlertDialog(getString(R.string.app_name), getString(R.string.err_conf_pwd));
                    Toast.makeText(this, R.string.err_conf_pwd, Toast.LENGTH_SHORT).show();
                    etRepassword.requestFocus();
                    return;
                }
                else if (etRepassword.getText().toString().length() < 6) {
                    //  showAlertDialog(getString(R.string.app_name), getString(R.string.err_conf_pwd1));
                    Toast.makeText(this, R.string.err_conf_pwd1, Toast.LENGTH_SHORT).show();
                    etRepassword.requestFocus();
                    return;
                }
                else if (!(etPassword.getText().toString()).equals(etRepassword.getText().toString()))
                {
                    //showAlertDialog(getString(R.string.app_name), getString(R.string.err_mismatch));
                    Toast.makeText(this, R.string.err_mismatch, Toast.LENGTH_SHORT).show();
                    etRepassword.requestFocus();
                    return;
                }
                else
                {
                    if (CommonFunctions.checkConnection(this)) {
                        String url = IndiaMartConfig.WEBURL + IndiaMartConfig.forgot_password_change_password;
                        Map<String, String> mParams = new HashMap<>();
                        mParams.put(Constants.email, email);
                        mParams.put(Constants.password, etPassword.getText().toString());
                        mParams.put(Constants.confirm_password, etPassword.getText().toString());
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
                                                Toast.makeText(Reset_Password.this, response.getString(Constants.message), Toast.LENGTH_SHORT).show();
                                                Intent otp=new Intent(Reset_Password.this,LoginActivity.class);
                                                startActivity(otp);
                                                finishAffinity();
                                            } else {
                                                Toast.makeText(Reset_Password.this, response.getString(Constants.message), Toast.LENGTH_SHORT).show();
                                            }

                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }

                                    @Override
                                    public void onError(ANError error) {
                                        try {
                                            CommonFunctions.destroyProgressBar();
                                            Toast.makeText(Reset_Password.this, R.string.msg_server_error, Toast.LENGTH_SHORT).show();
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}