package com.indiaudyogmart.login;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;

import com.indiaudyogmart.R;
import com.indiaudyogmart.config.CommonFunctions;
import com.indiaudyogmart.config.Constants;
import com.indiaudyogmart.utils.LocaleHelper;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.indiaudyogmart.config.CommonFunctions.emailvalid;


public class Signup_Seller extends AppCompatActivity {


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
    @BindView(R.id.btn_next)
    Button btnNext;
    @BindView(R.id.ll_signup)
    LinearLayout llSignup;
    @BindView(R.id.btn_buyer)
    Button btnBuyer;
    @BindView(R.id.ll_buyer)
    LinearLayout llBuyer;
    @BindView(R.id.rl_signup)
    RelativeLayout rlSignup;
    @BindView(R.id.ll_contain)
    LinearLayout llContain;
    @BindView(R.id.ns_main)
    NestedScrollView nsMain;
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


        setContentView(R.layout.activity_signup_seller);

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

    @OnClick({R.id.btn_next, R.id.btn_buyer, R.id.tv_alreadyhaveaccount, R.id.tv_login, R.id.ll_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_next:
                singup();
                break;
            case R.id.btn_buyer:
                Intent signup4 = new Intent(this, Signup_Normal.class);
                startActivity(signup4);
                finish();
                break;
            case R.id.tv_alreadyhaveaccount:
                Intent signup1 = new Intent(this, LoginActivity.class);
                startActivity(signup1);
                finishAffinity();
                break;
            case R.id.tv_login:
                Intent signup2 = new Intent(this, LoginActivity.class);
                startActivity(signup2);
                finishAffinity();
                break;
            case R.id.ll_login:
                Intent signup3 = new Intent(this, LoginActivity.class);
                startActivity(signup3);
                finishAffinity();
                break;
        }
    }

    private void singup() {
        try {
            if (CommonFunctions.checkConnection(this)) {
                if (etFullname.getText().toString().length() == 0) {
                    Toast.makeText(this, R.string.err_name, Toast.LENGTH_SHORT).show();
                    etFullname.requestFocus();
                    return;
                } else if (etEmail.getText().toString().length() == 0) {
                    Toast.makeText(this, R.string.err_email, Toast.LENGTH_SHORT).show();
                    etEmail.requestFocus();
                    return;
                } else if (etMobile.getText().toString().length() == 0 || etMobile.getText().toString().length() < 10) {

                    Toast.makeText(this, R.string.err_mobil, Toast.LENGTH_SHORT).show();
                    etMobile.requestFocus();
                    return;
                } else if (etPassword.getText().toString().length() == 0) {

                    Toast.makeText(this, R.string.err_pwd, Toast.LENGTH_SHORT).show();
                    etPassword.requestFocus();
                    return;
                } else if (etPassword.getText().toString().length() < 6) {

                    Toast.makeText(this, R.string.err_pwd1, Toast.LENGTH_SHORT).show();
                    etPassword.requestFocus();
                    return;
                }
               /* else if (etConfirmPassword.getText().toString().length() == 0)
                    showAlertDialog(getString(R.string.app_name), getString(R.string.err_conf_pwd));
                else if (etConfirmPassword.getText().toString().length() < 6)
                    showAlertDialog(getString(R.string.app_name), getString(R.string.err_conf_pwd1));*/
                else if (etEmail.getText().toString().length() > 0 && !etEmail.getText().toString().trim().matches(emailvalid)) {

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
                else {
                    Intent otp = new Intent(Signup_Seller.this, Signup_Seller_Company.class);
                    otp.putExtra(Constants.phone, etMobile.getText().toString());
                    otp.putExtra(Constants.email, etEmail.getText().toString());
                    otp.putExtra(Constants.name, etFullname.getText().toString());
                    otp.putExtra(Constants.password, etPassword.getText().toString());
                    otp.putExtra(Constants.address, etLocation.getText().toString());
                    startActivity(otp);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}