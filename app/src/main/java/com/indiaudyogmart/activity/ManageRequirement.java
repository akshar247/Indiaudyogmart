package com.indiaudyogmart.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.indiaudyogmart.R;
import com.indiaudyogmart.config.CommonFunctions;
import com.indiaudyogmart.config.Constants;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ManageRequirement extends AppCompatActivity {

    @BindView(R.id.tv_company)
    TextView tvcompany;
    @BindView(R.id.tv_date)
    TextView tvdate;

@BindView(R.id.rl_1)
    RelativeLayout relativeLayout;

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


        setContentView(R.layout.activity_manage_requirement);
        init();

    }

    private void init() {
        ButterKnife.bind(this);





        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str = tvcompany.getText().toString();
                String str1 = tvdate.getText().toString();
                Intent intent = new Intent(getApplicationContext(), RequirementDetails.class);
                intent.putExtra("company", str);
                intent.putExtra("date", str1);
                startActivity(intent);
            }
        });




    }
}