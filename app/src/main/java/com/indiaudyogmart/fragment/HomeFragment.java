package com.indiaudyogmart.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.Gson;
import com.indiaudyogmart.R;
import com.indiaudyogmart.activity.PostRequirments;
import com.indiaudyogmart.activity.QuotePopup;
import com.indiaudyogmart.adpter.HomePageFlipperAdapter;
import com.indiaudyogmart.adpter.HomeProductCategoryAdapter;
import com.indiaudyogmart.adpter.HomeProductDealAdapter;
import com.indiaudyogmart.adpter.HomeTreandingAdapter;
import com.indiaudyogmart.config.CommonFunctions;
import com.indiaudyogmart.config.Constants;
import com.indiaudyogmart.config.IndiaMartConfig;
import com.indiaudyogmart.dashboard.DashboardActivity;
import com.indiaudyogmart.model.HomeData;
import com.indiaudyogmart.model.IndustriesResponse;
import com.indiaudyogmart.model.LoginResponse;
import com.indiaudyogmart.utils.AutoScrollViewPager;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.gujun.android.taggroup.TagGroup;

public class HomeFragment extends Fragment {
    private static final int AUTO_SCROLL_THRESHOLD_IN_MILLI = 10000;

    View rootView;
    @BindView(R.id.home_top_ViewPager)
    AutoScrollViewPager home_top_ViewPager;
    @BindView(R.id.rv_trandingitem)
    RecyclerView rv_trandingitem;
    HomeData homeData;
    HomePageFlipperAdapter homePageFlipperAdapter;
    HomeTreandingAdapter homeTreandingAdapter;
    HomeProductDealAdapter homeProductDealAdapter;
    HomeProductDealAdapter suggegtforproduct;
    @BindView(R.id.tv_product_interest_title)
    TextView tvProductInterestTitle;
    @BindView(R.id.viewmore_product_interest)
    TextView viewmoreProductInterest;
    @BindView(R.id.tr1)
    TableRow tr1;
    @BindView(R.id.rv_product_interest)
    RecyclerView rvProductInterest;
    @BindView(R.id.fl_productdeal)
    FrameLayout fl_productdeal;
    @BindView(R.id.tv_product_premium_title)
    TextView tvProductPremiumTitle;
    @BindView(R.id.viewmore_product_premium)
    TextView viewmoreProductPremium;
    @BindView(R.id.tr2)
    TableRow tr2;
    @BindView(R.id.fl_premium)
    FrameLayout flPremium;
    @BindView(R.id.rv_product_premium)
    RecyclerView rvProductPremium;
    HomeProductCategoryAdapter homeProductCategoryAdapter;
    @BindView(R.id.text_view_show_more)
    TagGroup textViewShowMore;
    @BindView(R.id.ll_tag2)
    LinearLayout ll_tag2;
    @BindView(R.id.ll_tag1)
    LinearLayout ll_tag1;
    List<String> tagList = new ArrayList<>();
    @BindView(R.id.ns_main)
    NestedScrollView nsMain;
    @BindView(R.id.btn_postreqimnet)
    Button btnPostreqimnet;
    @BindView(R.id.tv_suggestforyou_title)
    TextView tvSuggestforyouTitle;
    @BindView(R.id.viewmore_suggestforyou)
    TextView viewmoreSuggestforyou;
    @BindView(R.id.tv_suggestforyou)
    TableRow tvSuggestforyou;
    @BindView(R.id.fl_suggestforyou)
    FrameLayout flSuggestforyou;
    @BindView(R.id.rv_product_related)
    RecyclerView rvProductRelated;
    @BindView(R.id.tv_view_all)
    TextView viewall;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_home, container, false);
        init();
        return rootView;


    }
    public boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(null)
                    .commit();
            return true;
        }
        return false;
    }

    private void init() {

        try {
            ButterKnife.bind(this, rootView);
            nsMain.setVisibility(View.INVISIBLE);
            if (homeData == null)
                gethomepagedata();

            else
                setdata();
            rv_trandingitem.setLayoutManager(new LinearLayoutManager(getActivity()));
            GridLayoutManager horizontalLayout = new GridLayoutManager(getActivity(), 2);
            GridLayoutManager horizontalLayout1 = new GridLayoutManager(getActivity(), 2);
            GridLayoutManager horizontalLayout2 = new GridLayoutManager(getActivity(), 2);
            rvProductInterest.setLayoutManager(horizontalLayout);
            rvProductPremium.setLayoutManager(horizontalLayout1);
            rvProductRelated.setLayoutManager(horizontalLayout2);

            nsMain.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
                @Override
                public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    if (scrollY > oldScrollY) {
                        btnPostreqimnet.setVisibility(View.VISIBLE);
                    } else {
                        btnPostreqimnet.setVisibility(View.GONE);
                    }
                }
            });
            viewall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadFragment(new IndustriesFragment());
                }
            });
            btnPostreqimnet.setVisibility(View.GONE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.btn_postreqimnet)
    public void sendpostreqiment() {
        try {
            if (CommonFunctions.getloginresponse(getActivity()).data.roleid == 1) {
                Intent re = new Intent(getActivity(), PostRequirments.class);
                startActivity(re);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void gethomepagedata() {
        try {
            if (CommonFunctions.checkConnection(getActivity())) {
                CommonFunctions.createProgressBar(getActivity(), getString(R.string.msg_please_wait));

                LoginResponse loginResponse = CommonFunctions.getloginresponse(getActivity());
                String url = IndiaMartConfig.WEBURL + IndiaMartConfig.get_home_page_data;
                Map<String, String> mParams = new HashMap<>();
                Log.e("url", url);
                Log.e("mParams", mParams.toString());
                AndroidNetworking.get(url)
                        .addHeaders(Constants.headerkey, loginResponse.data.encryptUserId)

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
                                    homeData = gson.fromJson(response.toString(), HomeData.class);
                                    if (loginResponse.status) {
                                        setdata();
                                        nsMain.setVisibility(View.VISIBLE);
                                    } else {
                                        Toast.makeText(getActivity(), loginResponse.message, Toast.LENGTH_SHORT).show();

                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onError(ANError error) {
                                try {
                                    CommonFunctions.destroyProgressBar();
                                    Toast.makeText(getActivity(), R.string.msg_server_error, Toast.LENGTH_SHORT).show();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
            }
        } catch (Exception e) {
            e.printStackTrace();
            CommonFunctions.destroyProgressBar();
        }
    }

    private void setdata() {
        try {
            if (homeData.data.banners != null) {
                homePageFlipperAdapter = new HomePageFlipperAdapter(getActivity(), homeData.data.banners, homeData);
                home_top_ViewPager.setAdapter(homePageFlipperAdapter);
                home_top_ViewPager.startAutoScroll();
                home_top_ViewPager.setInterval(AUTO_SCROLL_THRESHOLD_IN_MILLI);
                // enable recycling using true
                home_top_ViewPager.setCycle(true);
            }
            if (homeData.data.trendingCategory != null) {
                homeTreandingAdapter = new HomeTreandingAdapter(getActivity(), homeData.data.trendingCategory, homeData, new HomeTreandingAdapter.AdapterCallback() {
                    @Override
                    public void onSubcategoryClick(HomeData.TrendingCategoryEntity items) {
                        try {

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onSubcategoryViewAllClick(HomeData.TrendingCategoryEntity items) {
                        try {
                            getgategory(items.categoryId + "");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void subcategorychildclick(HomeData.ChildsEntity items) {
                        try {
                            getgategory(items.id + "");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }, "");
                rv_trandingitem.setAdapter(homeTreandingAdapter);
            }
            if (homeData.data.productDeals != null) {
                homeProductDealAdapter = new HomeProductDealAdapter(getActivity(), homeData.data.productDeals, homeData, new HomeProductDealAdapter.AdapterCallback() {
                    @Override
                    public void onSubcategoryClick(HomeData.ProductDealsEntity items) {
                        try {
                            Intent i = new Intent(getActivity(), QuotePopup.class);
                            i.putExtra(Constants.product_id, items.productId + "");
                            i.putExtra(Constants.prod_name, items.productName + "");
                            i.putExtra(Constants.prod_unit, items.unitName);
                            i.putExtra(Constants.pro_pic, items.image + "");
                            i.putExtra(Constants.price, items.productPrice + "");
                            startActivity(i);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onSubcategoryViewAllClick(HomeData.ProductDealsEntity items) {
                        try {
                            ProductDetailView productDetailView = new ProductDetailView();
                            Bundle bundle = new Bundle();
                            bundle.putString(Constants.product_id, items.productId + "");
                            productDetailView.setArguments(bundle);
                            ((DashboardActivity) getActivity()).addFragment(productDetailView);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, "");
                rvProductInterest.setAdapter(homeProductDealAdapter);
                fl_productdeal.setVisibility(View.VISIBLE);
            } else {
                rvProductInterest.setVisibility(View.GONE);
                fl_productdeal.setVisibility(View.GONE);
            }

            if (homeData.data.suggest_for_you != null) {
                suggegtforproduct = new HomeProductDealAdapter(getActivity(), homeData.data.suggest_for_you, homeData, new HomeProductDealAdapter.AdapterCallback() {
                    @Override
                    public void onSubcategoryClick(HomeData.ProductDealsEntity items) {
                        try {
                            Intent i = new Intent(getActivity(), QuotePopup.class);
                            i.putExtra(Constants.product_id, items.productId + "");
                            i.putExtra(Constants.prod_name, items.productName + "");
                            i.putExtra(Constants.prod_unit, items.unitName);
                            i.putExtra(Constants.pro_pic, items.image + "");
                            i.putExtra(Constants.price, items.productPrice + "");
                            startActivity(i);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onSubcategoryViewAllClick(HomeData.ProductDealsEntity items) {
                        try {
                            ProductDetailView productDetailView = new ProductDetailView();
                            Bundle bundle = new Bundle();
                            bundle.putString(Constants.product_id, items.productId + "");
                            productDetailView.setArguments(bundle);
                            ((DashboardActivity) getActivity()).addFragment(productDetailView);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, "");
                rvProductRelated.setAdapter(suggegtforproduct);
                flSuggestforyou.setVisibility(View.VISIBLE);
            } else {
                rvProductRelated.setVisibility(View.GONE);
                flSuggestforyou.setVisibility(View.GONE);
            }

            if (homeData.data.premiumCollection != null) {
                homeProductCategoryAdapter = new HomeProductCategoryAdapter(getActivity(), homeData.data.premiumCollection, homeData, new HomeProductCategoryAdapter.AdapterCallback() {
                    @Override
                    public void onSubcategoryClick(HomeData.PremiumCollectionEntity items) {
                        try {
                            getgategory(items.categoryId + "");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onSubcategoryViewAllClick(HomeData.PremiumCollectionEntity items) {

                    }
                }, "");
                rvProductPremium.setAdapter(homeProductCategoryAdapter);
                flPremium.setVisibility(View.VISIBLE);
            } else {
                rvProductPremium.setVisibility(View.GONE);
                flPremium.setVisibility(View.GONE);
            }
            if (homeData.data.importantSuppliers != null && homeData.data.importantSuppliers.size() > 0) {

                int count = 0;
                for (HomeData.ImportantSuppliersEntity importantSuppliersEntity : homeData.data.importantSuppliers) {
                    tagList.add(importantSuppliersEntity.category.name);
                    count++;
                }

                for (int i = 0; i < 7; i++) {
                    View specification = LayoutInflater.from(getActivity()).inflate(R.layout.item_tag, null, false);

                    TextView tv_title = specification.findViewById(R.id.tv_title);

                    tv_title.setText(homeData.data.importantSuppliers.get(i).category.name);
                    tv_title.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                String cateid = "";
                                if (homeData.data.importantSuppliers != null && homeData.data.importantSuppliers.size() > 0) {
                                    for (HomeData.ImportantSuppliersEntity importantSuppliersEntity : homeData.data.importantSuppliers) {
                                        if (importantSuppliersEntity.category.name.equalsIgnoreCase(tv_title.getText().toString())) {
                                            cateid = importantSuppliersEntity.categoryId + "";
                                            break;
                                        }

                                    }
                                }
                                getgategory(cateid);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    ll_tag1.addView(specification);

                }
                for (int i = 7; i < 13; i++) {
                    View specification = LayoutInflater.from(getActivity()).inflate(R.layout.item_tag, null, false);

                    TextView tv_title = specification.findViewById(R.id.tv_title);
                    if (i == 12) {
                        tv_title.setText(R.string.viewall);
                    } else
                        tv_title.setText(homeData.data.importantSuppliers.get(i).category.name);

                    tv_title.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                if (tv_title.getText().toString().equalsIgnoreCase(getString(R.string.viewall))) {
                                    if (CommonFunctions.getloginresponse(getActivity()).data.roleid == 1) {
                                        ((DashboardActivity) getActivity()).addFragment(new ImportantSupplier());
                                    }
                                } else {
                                    String cateid = "";
                                    if (homeData.data.importantSuppliers != null && homeData.data.importantSuppliers.size() > 0) {
                                        for (HomeData.ImportantSuppliersEntity importantSuppliersEntity : homeData.data.importantSuppliers) {
                                            if (importantSuppliersEntity.category.name.equalsIgnoreCase(tv_title.getText().toString())) {
                                                cateid = importantSuppliersEntity.categoryId + "";
                                                break;
                                            }

                                        }
                                    }
                                    getgategory(cateid);
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    ll_tag2.addView(specification);

                }


                //  textViewShowMore.setTags(tagList);
              /*  textViewShowMore.setOnTagClickListener(new TagGroup.OnTagClickListener() {
                    @Override
                    public void onTagClick(String tag) {
                        try {
                            try {
                                String cateid="";
                                if (homeData.data.importantSuppliers != null && homeData.data.importantSuppliers.size() > 0) {
                                    for (HomeData.ImportantSuppliersEntity importantSuppliersEntity : homeData.data.importantSuppliers) {
                                        if (importantSuppliersEntity.category.name.equalsIgnoreCase(tag)) {
                                            cateid = importantSuppliersEntity.categoryId + "";
                                            break;
                                        }

                                    }
                                }
                                getgategory(cateid);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });*/
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void getgategory(String cateid) {
        try {

            CommonFunctions.createProgressBar(getActivity(), getString(R.string.msg_please_wait));

            LoginResponse loginResponse = CommonFunctions.getloginresponse(getActivity());
            String url = IndiaMartConfig.WEBURL + IndiaMartConfig.getCategoryChild_Mart + cateid;
            Map<String, String> mParams = new HashMap<>();
            Log.e("url", url);
            Log.e("mParams", mParams.toString());

            AndroidNetworking.get(url)
                    .addHeaders(Constants.headerkey, loginResponse.data.encryptUserId)

                    .setTag(url)
                    .setPriority(Priority.HIGH)
                    .build().getAsJSONObject(new JSONObjectRequestListener() {
                @Override
                public void onResponse(JSONObject response) {

                    try {
                        CommonFunctions.destroyProgressBar();
                        Log.e("res", response.toString());
                        Gson gson = new Gson();
                        IndustriesResponse industriesResponse = gson.fromJson(response.toString(), IndustriesResponse.class);
                        if (industriesResponse.status) {
                            IndustriesChildFragment industriesChildFragment = new IndustriesChildFragment();
                            Bundle bundle = new Bundle();
                            bundle.putString(Constants.cat_id, cateid + "");
                            industriesChildFragment.setArguments(bundle);
                            ((DashboardActivity) getActivity()).addFragment(industriesChildFragment);
                        } else {
                            ProductDetailFragment productDetailFragment = new ProductDetailFragment();
                            Bundle bundle = new Bundle();
                            bundle.putString(Constants.cat_id, cateid + "");
                            productDetailFragment.setArguments(bundle);
                            ((DashboardActivity) getActivity()).addFragment(productDetailFragment);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onError(ANError anError) {
                    try {
                        CommonFunctions.destroyProgressBar();
                        Toast.makeText(getActivity(), R.string.msg_server_error, Toast.LENGTH_SHORT).show();
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

