package com.indiaudyogmart.activity;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.indiaudyogmart.R;
import com.indiaudyogmart.config.CommonFunctions;
import com.indiaudyogmart.config.Constants;
import com.indiaudyogmart.utils.LocaleHelper;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CompanyInfo extends AppCompatActivity {
    @BindView(R.id.btn_finish)
    Button finish;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_gst)
    EditText etGst;
    @BindView(R.id.et_address)
    EditText etAddress;
    @BindView(R.id.et_contact)
    EditText etContact;
    @BindView(R.id.et_detail_cotact)
    EditText etDetailCotact;
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

        setContentView(R.layout.activity_company_info);
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
            finish.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
            if(CommonFunctions.checkConnection(CompanyInfo.this));
            {
                setdata();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void setdata() {
        try {
            etName.setText(CommonFunctions.getloginresponse(this).data.companyName);
           etGst.setText(CommonFunctions.getloginresponse(this).data.gstNo);
            etAddress.setText(CommonFunctions.getloginresponse(this).data.companyAddress);
           etContact.setText(CommonFunctions.getloginresponse(this).data.contactno);
            etDetailCotact.setText(CommonFunctions.getloginresponse(this).data.contactdetails);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}