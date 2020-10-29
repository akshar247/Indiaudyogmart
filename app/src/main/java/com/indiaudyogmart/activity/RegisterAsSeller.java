package com.indiaudyogmart.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.indiaudyogmart.R;
import com.indiaudyogmart.config.CommonFunctions;
import com.indiaudyogmart.config.Constants;
import com.indiaudyogmart.model.LoginResponse;
import com.indiaudyogmart.utils.LocaleHelper;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterAsSeller extends AppCompatActivity {
@BindView(R.id.btn_next)
Button next;
LoginResponse loginResponse1;
@BindView(R.id.et_number1)
    EditText pnumber;
    @BindView(R.id.et_number2)
    EditText snumber;
    @BindView(R.id.et_email1)
    EditText pemail;
    @BindView(R.id.et_emai2)
    EditText semail;
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


        setContentView(R.layout.activity_register_as_seller);
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
            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent seller=new Intent(RegisterAsSeller.this, CompanyInfo.class);
                    startActivity(seller);
                }
            });
            if(CommonFunctions.checkConnection(RegisterAsSeller.this));
            {
                setdata();
            }



        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void setdata() {
        try {
            pemail.setText(CommonFunctions.getloginresponse(this).data.email);
            pnumber.setText(CommonFunctions.getloginresponse(this).data.contactno);
            semail.setText(CommonFunctions.getloginresponse(this).data.email);
            snumber.setText(CommonFunctions.getloginresponse(this).data.contactno);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}