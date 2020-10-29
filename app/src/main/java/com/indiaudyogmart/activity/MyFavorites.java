package com.indiaudyogmart.activity;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.indiaudyogmart.R;
import com.indiaudyogmart.adpter.MyFavoritesAdapter;
import com.indiaudyogmart.config.CommonFunctions;
import com.indiaudyogmart.config.Constants;
import com.indiaudyogmart.model.MyFavoritesItem;
import com.indiaudyogmart.utils.LocaleHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyFavorites extends AppCompatActivity {

    List<MyFavoritesItem> item;
    @BindView(R.id.favorites_item)
    RecyclerView favoritesitem;
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


        setContentView(R.layout.activity_my_favorites);
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


            item = new ArrayList<>();
            item.add(new MyFavoritesItem(R.string.sanitizer, R.drawable.headphone));
            item.add(new MyFavoritesItem(R.string.sanitizer, R.drawable.favorites1));
            item.add(new MyFavoritesItem(R.string.sanitizer, R.drawable.headphone));
            item.add(new MyFavoritesItem(R.string.sanitizer, R.drawable.favorites1));
            item.add(new MyFavoritesItem(R.string.sanitizer, R.drawable.headphone));
            item.add(new MyFavoritesItem(R.string.sanitizer, R.drawable.favorites1));
            item.add(new MyFavoritesItem(R.string.sanitizer, R.drawable.headphone));
            item.add(new MyFavoritesItem(R.string.sanitizer, R.drawable.favorites1));


            MyFavoritesAdapter myAdapter = new MyFavoritesAdapter(MyFavorites.this,item);
            favoritesitem.setLayoutManager(new GridLayoutManager(MyFavorites.this, 2));
            favoritesitem.setAdapter(myAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
