package com.indiaudyogmart.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.indiaudyogmart.R;
import com.indiaudyogmart.adpter.RequirementSupplierAdapter;
import com.indiaudyogmart.config.CommonFunctions;
import com.indiaudyogmart.config.Constants;
import com.indiaudyogmart.model.RequirementSupplierItem;
import com.indiaudyogmart.utils.LocaleHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RequirementDetails extends AppCompatActivity {
@BindView(R.id.tv_product_name)
    TextView companyname;
    @BindView(R.id.tv_post_date)
    TextView postdate;

    @BindView(R.id.requirement_details)
    RecyclerView requirementdetails;
    List<RequirementSupplierItem> supplieritem;
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


        setContentView(R.layout.activity_requirement_details);
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
            Intent intent = getIntent();
            String str = intent.getStringExtra("company");
            String str1 = intent.getStringExtra("date");
            companyname.setText(str);
            postdate.setText(str1);



            supplieritem= new ArrayList<>();
            supplieritem.add(new RequirementSupplierItem(R.string.companyname, R.string.addressadd, R.string.emailaddress, R.string.websitegoogle, R.string.profilename, R.string.department));
            supplieritem.add(new RequirementSupplierItem(R.string.companyname, R.string.addressadd, R.string.emailaddress, R.string.websitegoogle, R.string.profilename, R.string.department));
            supplieritem.add(new RequirementSupplierItem(R.string.companyname, R.string.addressadd, R.string.emailaddress, R.string.websitegoogle, R.string.profilename, R.string.department));
            supplieritem.add(new RequirementSupplierItem(R.string.companyname, R.string.addressadd, R.string.emailaddress, R.string.websitegoogle, R.string.profilename, R.string.department));
            supplieritem.add(new RequirementSupplierItem(R.string.companyname, R.string.addressadd, R.string.emailaddress, R.string.websitegoogle, R.string.profilename, R.string.department));



            RequirementSupplierAdapter myAdapter = new RequirementSupplierAdapter(RequirementDetails.this,supplieritem);
            requirementdetails.setLayoutManager(new GridLayoutManager(RequirementDetails.this, 1));
            requirementdetails.setAdapter(myAdapter);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}