package com.indiaudyogmart.dashboard;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.core.widget.NestedScrollView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.google.android.material.navigation.NavigationView;
import com.indiaudyogmart.BuildConfig;
import com.indiaudyogmart.R;
import com.indiaudyogmart.activity.AboutIndiaudhyogmart;
import com.indiaudyogmart.activity.AllCityActivity;
import com.indiaudyogmart.activity.BuyleadActivity;
import com.indiaudyogmart.activity.HelpAndSupport;
import com.indiaudyogmart.activity.Kyc;
import com.indiaudyogmart.activity.KycStatus;
import com.indiaudyogmart.activity.Language;
import com.indiaudyogmart.activity.ManageRequirement;
import com.indiaudyogmart.activity.MyFavorites;
import com.indiaudyogmart.activity.MyProduct;
import com.indiaudyogmart.activity.PayWithIndiaUdyogMart;
import com.indiaudyogmart.activity.PostRequirments;
import com.indiaudyogmart.activity.RegisterAsSeller;
import com.indiaudyogmart.activity.Settings;
import com.indiaudyogmart.config.CommonFunctions;
import com.indiaudyogmart.config.Constants;
import com.indiaudyogmart.config.IndiaMartConfig;
import com.indiaudyogmart.config.Logger;
import com.indiaudyogmart.fragment.BusinessInfoFragment;
import com.indiaudyogmart.fragment.HomeFragment;
import com.indiaudyogmart.fragment.ImportantSupplier;
import com.indiaudyogmart.fragment.IndustriesFragment;
import com.indiaudyogmart.fragment.MessageFragment;
import com.indiaudyogmart.fragment.MyAccount;
import com.indiaudyogmart.fragment.MyLeadSellerFragment;
import com.indiaudyogmart.fragment.MyQuoteBuyerFragment;
import com.indiaudyogmart.fragment.RequestFragment;
import com.indiaudyogmart.fragment.SearchFragment;
import com.indiaudyogmart.fragment.UserProfileFragment;
import com.indiaudyogmart.login.LoginActivity;
import com.indiaudyogmart.model.LoginResponse;
import com.indiaudyogmart.utils.LocaleHelper;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class DashboardActivity extends AppCompatActivity {


    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.ib_drawer)
    ImageView ib;
    @BindView(R.id.iv_profileimage)
    CircleImageView iv_profileimage;
    @BindView(R.id.iv_cart)
    ImageView iv_cart;
    @BindView(R.id.btn_postreqimnet)
    Button btnPostreqimnet;
    @BindView(R.id.nav_view)
    NavigationView nvView;
    @BindView(R.id.navbottom)
    MeowBottomNavigation meo;
    @BindView(R.id.tv_user)
    TextView username;
    @BindView(R.id.ll_actionbar2)
    RelativeLayout ll_actionbar2;
    @BindView(R.id.fl_1)
    FrameLayout fl_1;
    @BindView(R.id.tv_cityname)
    TextView tv_cityname;
    @BindView(R.id.ns_main)
    NestedScrollView frag;
    ImageView iv_image;
    TextView tv_name;
    TextView tv_number;
    TextView tv_buyer;
    TextView tv_edit;
    boolean doubleBackToExitPressedOnce = false;
    @BindView(R.id.et_search)
    EditText et_search;
    @BindView(R.id.iv_mic)
    ImageView iv_mic;
    private final static int ID_HOME = 1;
    private final static int ID_MESSAGE = 2;
    private final static int ID_PAYNOW = 3;
    String lang;
    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        lang = CommonFunctions.getPreference(this, Constants.defalt_languge, "en");
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());



        setContentView(R.layout.activity_dashboard);
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
        ButterKnife.bind(this);
        btnPostreqimnet.setVisibility(View.GONE);
        frag.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY > oldScrollY) {
                    meo.setVisibility(View.GONE);
                    btnPostreqimnet.setVisibility(View.VISIBLE);
                } else {
                    meo.setVisibility(View.VISIBLE);
                    btnPostreqimnet.setVisibility(View.GONE);
                }
            }
        });
        if (CommonFunctions.getloginresponse(DashboardActivity.this).data.roleid == 1)
        {
            nvView.inflateMenu(R.menu.buyer_drawer_menu);
        }
        else
        {
            nvView.inflateMenu(R.menu.seller_drawer_menu);
        }
        setupDrawerContent(nvView);


        iv_image = nvView.getHeaderView(0).findViewById(R.id.iv_image);
        tv_name = nvView.getHeaderView(0).findViewById(R.id.tv_name);
        tv_number = nvView.getHeaderView(0).findViewById(R.id.tv_number);
        tv_buyer = nvView.getHeaderView(0).findViewById(R.id.tv_buyer);
        tv_edit = nvView.getHeaderView(0).findViewById(R.id.tv_edit);

        // loadFragment(new HomeFragment());

        meo.add(new MeowBottomNavigation.Model(ID_HOME, R.drawable.ic_home_bottom));
        meo.add(new MeowBottomNavigation.Model(ID_MESSAGE, R.drawable.ic_postrequirement_4));
        meo.add(new MeowBottomNavigation.Model(ID_PAYNOW, R.drawable.ic_bid));
        /*meo.setCount(ID_MESSAGE, "115");*/

        meo.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {
                /*  Toast.makeText(DashboardActivity.this, "clicked item : " + item.getId(), Toast.LENGTH_SHORT).show();*/

                Class fragmentClass = null;
                Fragment fragment = null;
                switch (item.getId()) {
                    case ID_HOME:
                        if (CommonFunctions.getloginresponse(DashboardActivity.this).data.roleid == 1)
                            fragmentClass = HomeFragment.class;
                        else
                            fragmentClass = MyAccount.class;
                        break;
                    case ID_MESSAGE:
                        if (CommonFunctions.getloginresponse(DashboardActivity.this).data.roleid == 1)
                            fragmentClass = PostRequirments.class;
                        else
                            fragmentClass = PostRequirments.class;

                        break;
                    case ID_PAYNOW:
                        if (CommonFunctions.getloginresponse(DashboardActivity.this).data.roleid == 1)
                            fragmentClass = MyQuoteBuyerFragment.class;
                        else
                            fragmentClass = MyLeadSellerFragment.class;
                        break;

                }

                try {
                    if (fragmentClass != null) {
                        fragment = (Fragment) fragmentClass.newInstance();
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit();

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        meo.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {


            }
        });

        meo.setOnReselectListener(new MeowBottomNavigation.ReselectListener() {
            @Override
            public void onReselectItem(MeowBottomNavigation.Model item) {
                //Toast.makeText(DashboardActivity.this, "reselected item : " + item.getId(), Toast.LENGTH_SHORT).show();
                try {
                    Class fragmentClass = null;
                    Fragment fragment = null;
                    switch (item.getId()) {
                        case ID_HOME:
                            if (CommonFunctions.getloginresponse(DashboardActivity.this).data.roleid == 1)
                                fragmentClass = HomeFragment.class;
                            else
                                fragmentClass = MyAccount.class;
                            break;
                        case ID_MESSAGE:
                            if (CommonFunctions.getloginresponse(DashboardActivity.this).data.roleid == 1)
                                fragmentClass = PostRequirments.class;
                            else
                                fragmentClass = PostRequirments.class;
                            break;
                        case ID_PAYNOW:
                            if (CommonFunctions.getloginresponse(DashboardActivity.this).data.roleid == 1)
                                fragmentClass = MyQuoteBuyerFragment.class;
                            else
                                fragmentClass = MyLeadSellerFragment.class;
                            break;

                    }

                    try {
                        if (fragmentClass != null) {
                            fragment = (Fragment) fragmentClass.newInstance();
                            FragmentManager fragmentManager = getSupportFragmentManager();
                            fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit();

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        meo.show(ID_HOME, true);
        if (CommonFunctions.getloginresponse(DashboardActivity.this).data.roleid == 1)
        {
            loadFragment(new HomeFragment());
            tv_buyer.setText(R.string.buyer);
        }
        else{
            loadFragment(new MyAccount());
            fl_1.setVisibility(View.GONE);
            ll_actionbar2.setVisibility(View.GONE);
            tv_buyer.setText(R.string.seller);
        }

        /* meo.setCount(ID_MESSAGE, "115");*/
        /* meo.show(ID_MESSAGE,true);*/
     /*   if(getSupportActionBar()!=null)
            getSupportActionBar().hide();*/
        setuserdata();
        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.openDrawer(GravityCompat.START);
                btnPostreqimnet.setVisibility(View.GONE);

            }
        });
        tv_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    loadFragment(new UserProfileFragment());
                    meo.show(ID_MESSAGE, true);
                    drawer.closeDrawers();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        et_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    Log.i("", "Here you can write the code");
                    try {
                        if(v.getText()!=null && v.getText().length()>0)
                        {
                            SearchFragment searchFragment=new SearchFragment();
                            Bundle bundle=new Bundle();
                            bundle.putString(Constants.search_term,v.getText().toString());
                            searchFragment.setArguments(bundle);
                            addFragment(searchFragment);
                            et_search.setText("");
                            CommonFunctions.hideSoftKeyboard(DashboardActivity.this,et_search);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return true;
                }
                return false;
            }
        });
        iv_mic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    openmic();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        ll_actionbar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent allcity=new Intent(DashboardActivity.this, AllCityActivity.class);
                    startActivity(allcity);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            tv_cityname.setText(CommonFunctions.getPreference(DashboardActivity.this,Constants.selectedcity,"SURAT"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openmic() {
        try {
            Dexter.withContext(this)
                    .withPermission(Manifest.permission.RECORD_AUDIO)
                    .withListener(new PermissionListener() {


                        @Override
                        public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                            try {


                                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                                startActivityForResult(intent, Constants.RECOGNIZER_REQ_CODE);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }

                        @Override
                        public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                        }

                        @Override
                        public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {

                        }

                    }).check();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case Constants.RECOGNIZER_REQ_CODE:

                if (resultCode == Activity.RESULT_OK && null != data) {


                    try {
                        String search_term = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS).get(0);
                        SearchFragment searchFragment=new SearchFragment();
                        Bundle bundle=new Bundle();
                        bundle.putString(Constants.search_term,search_term);
                        searchFragment.setArguments(bundle);
                        addFragment(searchFragment);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }
                break;

        }
    }

    private void setuserdata() {
        try {
            LoginResponse loginResponse = CommonFunctions.getloginresponse(this);
            tv_name.setText(loginResponse.data.name);
            tv_number.setText(loginResponse.data.contactno);
            RequestOptions requestOptions = new RequestOptions()
                    .apply(RequestOptions.errorOf(R.drawable.no_image))
                    .apply(RequestOptions.placeholderOf(R.drawable.no_image));
            Glide.with(this)
                    .load(loginResponse.data.profilepic)
                    .apply(requestOptions)
                    .into(iv_profileimage);
            Glide.with(this)
                    .load(loginResponse.data.profilepic)
                    .apply(requestOptions)
                    .into(iv_image);
            tv_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        openprofile();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            username.setText(loginResponse.data.name);




            iv_profileimage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        openprofile();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openprofile() {
        try {
            loadFragment(new UserProfileFragment());
            meo.show(ID_MESSAGE, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                selectDrawerItem(menuItem);
                return true;
            }
        });
    }

    private void selectDrawerItem(MenuItem menuItem) {
        Fragment fragment = null;
        Class fragmentClass = null;
        MenuItem item = null;
        drawer.closeDrawers();
        CommonFunctions.hideSoftKeyboard(DashboardActivity.this,et_search);
        switch (menuItem.getItemId()) {
            case R.id.nav_home:
                //fragmentClass = HomeFragment.class;
                /* item = bottomNav.getMenu().findItem(R.id.nav_home_fragment);*/
                /*item.setChecked(true);*/
                if (CommonFunctions.getloginresponse(DashboardActivity.this).data.roleid == 1) {
                    loadFragment(new HomeFragment());
                }
                else
                {
                    loadFragment(new MyAccount());
                }
                /* fragmentClass = HomeFragment.class;*/
                break;

            case R.id.nav_supplier:
                if (CommonFunctions.getloginresponse(DashboardActivity.this).data.roleid == 1) {
                    addFragment(new ImportantSupplier());
                }
                return;
            case R.id.nav_kyc:
                Intent post = new Intent(DashboardActivity.this, Kyc.class);
                startActivity(post);
                break;

            case R.id.nav_industries:
                /*loadFragment(new IndustriesFragment());*/
                if (CommonFunctions.getloginresponse(DashboardActivity.this).data.roleid == 1)
                fragmentClass = IndustriesFragment.class;
                break;
            case R.id.nav_requirements:
                fragmentClass = PostRequirments.class;
                break;
        /*    case R.id.nav_requirements:
                *//*loadFragment(new IndustriesFragment());*//*
                if (CommonFunctions.getloginresponse(DashboardActivity.this).data.roleid == 1) {
                    Intent post = new Intent(DashboardActivity.this, PostRequirments.class);
                    startActivity(post);
                }
                break;*/

            case R.id.nav_message:
                /*loadFragment(new IndustriesFragment());*/

                fragmentClass = MessageFragment.class;
                break;
            case R.id.nav_account:
                /*loadFragment(new IndustriesFragment());*/
                loadFragment(new MyAccount());
                meo.show(ID_HOME, true);


                break;
            case R.id.nav_profile:
                /*loadFragment(new IndustriesFragment());*/
                loadFragment(new UserProfileFragment());
                meo.show(ID_MESSAGE, true);


                break;
            case R.id.nav_lead:
                /*loadFragment(new IndustriesFragment());*/
                loadFragment(new MyLeadSellerFragment());
                meo.show(ID_PAYNOW, true);


                break;
            case R.id.nav_product:
                addFragment(new MyProduct());
                break;
            case R.id.nav_buylead:
                /*loadFragment(new IndustriesFragment());*/


                Intent pay1 = new Intent(DashboardActivity.this, BuyleadActivity.class);
                startActivity(pay1);
                break;
            case R.id.nav_request:
                /*loadFragment(new IndustriesFragment());*/
                loadFragment(new RequestFragment());
                break;
            case R.id.nav_kycinfo:
                /*loadFragment(new IndustriesFragment());*/
                Intent kycstatus = new Intent(DashboardActivity.this, KycStatus.class);
                startActivity(kycstatus);

                break;

            case R.id.nav_indiaudyogmart:
                /*loadFragment(new IndustriesFragment());*/
                Intent pay = new Intent(DashboardActivity.this, PayWithIndiaUdyogMart.class);
                startActivity(pay);
                break;
            case R.id.nav_manage:
                /*loadFragment(new IndustriesFragment());*/
                Intent manage = new Intent(DashboardActivity.this, ManageRequirement.class);
                startActivity(manage);
                break;
            case R.id.nav_favorities:
                /*loadFragment(new IndustriesFragment());*/
                Intent fav = new Intent(DashboardActivity.this, MyFavorites.class);
                startActivity(fav);
                break;
            case R.id.nav_tools:
                /*loadFragment(new IndustriesFragment());*/
                Intent seller = new Intent(DashboardActivity.this, RegisterAsSeller.class);
                startActivity(seller);
                break;

            case R.id.nav_feedback:
                /*loadFragment(new IndustriesFragment());*/
                /*fragmentClass = FeedbackDialogFragment.class;*/
                openfeedbackdiologue();
                break;
            case R.id.nav_Help:
                /*loadFragment(new IndustriesFragment());*/
                Intent help = new Intent(DashboardActivity.this, HelpAndSupport.class);
                startActivity(help);
                break;
            case R.id.nav_language:
                /*loadFragment(new IndustriesFragment());*/
                Intent language = new Intent(DashboardActivity.this, Language.class);
                startActivity(language);
                break;
            case R.id.nav_share:
                /*loadFragment(new IndustriesFragment());*/
                /*Intent share = new Intent(DashboardActivity.this, ShareApp.class);
                startActivity(share);*/
                shareapp();
                break;
            case R.id.nav_about:
                /*loadFragment(new IndustriesFragment());*/
                Intent about = new Intent(DashboardActivity.this, AboutIndiaudhyogmart.class);
                startActivity(about);
                break;
            case R.id.nav_settings:
                /*loadFragment(new IndustriesFragment());*/
                Intent settings = new Intent(DashboardActivity.this, Settings.class);
                startActivity(settings);
                break;
            case R.id.nav_businessinfo:
                addFragment(new BusinessInfoFragment());
                break;
            case R.id.nav_logout:
                /*loadFragment(new IndustriesFragment());*/
                CommonFunctions.setPreference(DashboardActivity.this, Constants.isLogin, false);
                CommonFunctions.setPreference(DashboardActivity.this, Constants.userdata, "");
                CommonFunctions.changeactivity(DashboardActivity.this, LoginActivity.class);
                break;
          /*  case R.id.nav_share:
                shareapp();
                break;*/
            default:
                fragmentClass = HomeFragment.class;

        }

        try {
            if (fragmentClass != null) {
                fragment = (Fragment) fragmentClass.newInstance();
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        menuItem.setChecked(true);

        setTitle(menuItem.getTitle());

        drawer.closeDrawers();
    }

    private void openfeedbackdiologue() {

        try {
            final Dialog dialog = new Dialog(DashboardActivity.this, R.style.Dialog_No_Border);
            dialog.setContentView(R.layout.feedback_dialog);
            final ImageView iv_cloase = dialog.findViewById(R.id.close);
            final Button finish = dialog.findViewById(R.id.btn_finish);
            final EditText edtcomment = dialog.findViewById(R.id.edt_feedback);
            dialog.setCancelable(true);
            dialog.setCanceledOnTouchOutside(false);
            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            lp.copyFrom(dialog.getWindow().getAttributes());
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
            int width = displayMetrics.widthPixels;
            int height = displayMetrics.heightPixels;
            dialog.getWindow().setAttributes(lp);
            iv_cloase.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        dialog.cancel();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            finish.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        if (edtcomment.getText().toString().trim().length() == 0) {
                            Toast.makeText(DashboardActivity.this, getString(R.string.err_message), Toast.LENGTH_SHORT).show();
                        } else {
                            sendcomment(edtcomment.getText().toString().trim());
                            CommonFunctions.hideSoftKeyboard(DashboardActivity.this,edtcomment);
                            dialog.cancel();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendcomment(String feedback) {
        try {
            if (CommonFunctions.checkConnection(DashboardActivity.this)) {
                CommonFunctions.createProgressBar(DashboardActivity.this, getString(R.string.msg_please_wait));
                LoginResponse loginResponse = CommonFunctions.getloginresponse(DashboardActivity.this);
                String url = IndiaMartConfig.WEBURL + IndiaMartConfig.feedback;
                Map<String, String> mParams = new HashMap<>();
                mParams.put(Constants.message, feedback);
                mParams.put(Constants.comment, feedback);
                Log.e("url", url);
                Log.e("mParams", mParams.toString());
                AndroidNetworking.post(url)
                        .addHeaders(Constants.headerkey, loginResponse.data.encryptUserId)
                        .addBodyParameter(mParams)
                        .setTag(url)
                        .setPriority(Priority.HIGH)
                        .addPathParameter(mParams)
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    CommonFunctions.destroyProgressBar();
                                    Log.e("res", response.toString());
                                    if (response.getBoolean(Constants.status)) {
                                        Toast.makeText(DashboardActivity.this, response.getString(Constants.message), Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(DashboardActivity.this, response.getString(Constants.message), Toast.LENGTH_SHORT).show();
                                    }

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onError(ANError anError) {
                                try {
                                    CommonFunctions.destroyProgressBar();
                                    Toast.makeText(DashboardActivity.this, R.string.msg_server_error, Toast.LENGTH_SHORT).show();
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

    private void shareapp() {
        try {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT,
                    "Hey check out my app at: https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID);
            sendIntent.setType("text/plain");
            startActivity(sendIntent);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(null)
                    .commit();
            return true;
        }
        return false;
    }

    public boolean addFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .addToBackStack(null)
                    .add(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }


    @Override
    public void onBackPressed() {
        Fragment f = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (f != null && (f instanceof HomeFragment || f instanceof MyAccount)) {


            if (doubleBackToExitPressedOnce) {
                finishAffinity();
                return;
            }
            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, getString(R.string.msg_press_back_twice), Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        } else {
            if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
                if (f != null && !((f instanceof HomeFragment || f instanceof MyAccount))) {
                  /*  Intent intent = new Intent("onresume");
                    intent.putExtra(Constants.from, getCurrentFragment(f));
                    LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
                    f.onResume();*/

                }
                if (CommonFunctions.getloginresponse(DashboardActivity.this).data.roleid == 1)
                    loadFragment(new HomeFragment());
                else
                    loadFragment(new MyAccount());
            }
            gotoHome();
        }
    }

    public void gotoHome() {
        try {
            getSupportFragmentManager().popBackStack();
            Fragment f = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
            f.onResume();
        } catch (Exception e) {
            Logger.debugE(e.toString());
        }
    }
 /*   private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.nav_home:
                    //   toolbar.setTitle("Shop");
                    loadFragment(new HomeFragment());
                    return true;
                case R.id.nav_message:
                    //  toolbar.setTitle("My Gifts");
                    Intent i=new Intent(DashboardActivity.this, ImportantSupplier.class);
                    startActivity(i);
                    return true;
                case R.id.nav_paynow:
                    //   toolbar.setTitle("Cart");
                    return true;
                case R.id.nav_post:
                    //   toolbar.setTitle("Profile");
                    return true;
                case R.id.nav_search:
                    //   toolbar.setTitle("Profile");
                    return true;
            }
            return false;
        }
    };*/
 @OnClick(R.id.btn_postreqimnet)
 public void sendpostreqiment() {
     try {
                 if (CommonFunctions.getloginresponse(DashboardActivity.this).data.roleid == 1) {
                 loadFragment(new PostRequirments());
                 }

         } catch(Exception e){
             e.printStackTrace();
         }
     }

}