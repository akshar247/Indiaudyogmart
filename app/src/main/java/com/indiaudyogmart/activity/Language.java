package com.indiaudyogmart.activity;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.indiaudyogmart.R;
import com.indiaudyogmart.config.CommonFunctions;
import com.indiaudyogmart.config.Constants;
import com.indiaudyogmart.dashboard.DashboardActivity;
import com.indiaudyogmart.utils.LocaleHelper;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Language extends AppCompatActivity {
    @BindView(R.id.iv_back)
    ImageView back;
    @BindView(R.id.rl_back)
    RelativeLayout rlBack;
    @BindView(R.id.transactions)
    TextView transactions;
    @BindView(R.id.rl_actionbar)
    RelativeLayout rlActionbar;
    @BindView(R.id.et_hindi)
    TextView etHindi;
    @BindView(R.id.switch_hindi)
    Switch switchHindi;
    @BindView(R.id.fl_1)
    FrameLayout fl1;
    @BindView(R.id.et_gujarati)
    TextView etGujarati;
    @BindView(R.id.switch_gujarati)
    Switch switchGujarati;
    @BindView(R.id.fl_2)
    FrameLayout fl2;
    @BindView(R.id.et_english)
    TextView etEnglish;
    @BindView(R.id.switch_english)
    Switch switchEnglish;
    @BindView(R.id.fl_3)
    FrameLayout fl3;
    @BindView(R.id.iv_3)
    ImageView iv3;
    @BindView(R.id.rl_4)
    RelativeLayout rl4;
    String language;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        language = CommonFunctions.getPreference(this, Constants.defalt_languge, "en");
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());


        setContentView(R.layout.activity_language);

        init();
    }
    @Override
    protected void attachBaseContext(Context newBase) {
        //super.attachBaseContext(newBase);
        language = CommonFunctions.getPreference(newBase, Constants.defalt_languge, "en");
        super.attachBaseContext(LocaleHelper.onAttach(newBase, language));
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
    }
    private void init() {
        try {
            ButterKnife.bind(this);
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
            language= CommonFunctions.getPreference(this, Constants.defalt_languge,"en");
            if(language.equalsIgnoreCase(Constants.en))
            {
                switchEnglish.setChecked(true);
                switchGujarati.setChecked(false);
                switchHindi.setChecked(false);
            }
            if(language.equalsIgnoreCase(Constants.gu))
            {
                switchEnglish.setChecked(false);
                switchGujarati.setChecked(true);
                switchHindi.setChecked(false);
            }
            if(language.equalsIgnoreCase(Constants.hi))
            {
                switchEnglish.setChecked(false);
                switchGujarati.setChecked(false);
                switchHindi.setChecked(true);
            }
            switchEnglish.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    try {
                        if(isChecked)
                        {
                            CommonFunctions.setPreference(Language.this, Constants.defalt_languge,Constants.en);
                            switchGujarati.setChecked(false);
                            switchHindi.setChecked(false);
                            CommonFunctions.changeactivity(Language.this, DashboardActivity.class);
                            finishAffinity();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            switchGujarati.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    try {
                        if(isChecked)
                        {
                            CommonFunctions.setPreference(Language.this, Constants.defalt_languge,Constants.gu);

                            switchEnglish.setChecked(false);

                            switchHindi.setChecked(false);
                            CommonFunctions.changeactivity(Language.this, DashboardActivity.class);
                            finishAffinity();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            switchHindi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    try {
                        if(isChecked)
                        {
                            CommonFunctions.setPreference(Language.this, Constants.defalt_languge,Constants.hi);
                            switchEnglish.setChecked(false);
                            switchGujarati.setChecked(false);

                            CommonFunctions.changeactivity(Language.this, DashboardActivity.class);
                            finishAffinity();

                        }
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