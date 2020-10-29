package com.indiaudyogmart.fragment;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.Gson;
import com.indiaudyogmart.R;
import com.indiaudyogmart.activity.QuotePopup;
import com.indiaudyogmart.adpter.AllBrandAdapter;
import com.indiaudyogmart.adpter.ProductDetailAdapter;
import com.indiaudyogmart.adpter.ProductDetailListAdapter;
import com.indiaudyogmart.adpter.SellerTypeAdapter;
import com.indiaudyogmart.config.CommonFunctions;
import com.indiaudyogmart.config.Constants;
import com.indiaudyogmart.config.IndiaMartConfig;
import com.indiaudyogmart.dashboard.DashboardActivity;
import com.indiaudyogmart.model.AllBrandResp;
import com.indiaudyogmart.model.LoginResponse;
import com.indiaudyogmart.model.ProductDetailResponse;
import com.indiaudyogmart.model.SellerTypeResp;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SearchFragment extends Fragment {
    ProductDetailResponse productDetailResponse;
    ProductDetailAdapter productDetailAdapter;
    ProductDetailListAdapter productDetailListAdapter;
    @BindView(R.id.product_item)
    RecyclerView recyclerview;
    @BindView(R.id.rl_dtlView)
    RelativeLayout dtlRelativelayout;
    @BindView(R.id.rl_lstView)
    RelativeLayout lstRelativelayout;
    @BindView(R.id.rl1)
    RelativeLayout rl1;
    @BindView(R.id.tv_empty)
    TextView tv_empty;
    String search_term;
    View rootView;
    AllBrandAdapter allBrandAdapter;
    SellerTypeAdapter sellerTypeAdapter;
    String cate_id;
    String brandid="",sellertype="5",city="india";
    AllBrandResp allBrandResp;
    SellerTypeResp sellerTypeResp;
    @BindView(R.id.sp_sellerType)
    Spinner spSellerType;
    @BindView(R.id.sp_category)
    Spinner spCategory;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.product_detail_recycler, container, false);
        init();
        return rootView;

    }

    private void init() {
        try {
            ButterKnife.bind(this, rootView);
            if (CommonFunctions.checkConnection(getActivity())) {
                if(getArguments()!= null && getArguments().containsKey(Constants.search_term)){
                    search_term=getArguments().getString(Constants.search_term);
                    getCategory(search_term);

                    getallbrand();
                    getsellertype();

                }


            }

            recyclerview.setLayoutManager(new GridLayoutManager(getActivity(), 1));
            dtlRelativelayout.setTag(1);


            dtlRelativelayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if ((int)view.getTag() == 1) {
                        recyclerview.setAdapter(productDetailListAdapter);
                        dtlRelativelayout.setTag(2);

//txtdtlview.setText("List View");
//proimg.setImageResource(R.drawable.ic_baseline_list);
                        dtlRelativelayout.setVisibility(View.GONE);
                        lstRelativelayout.setVisibility(view.VISIBLE);

                    }
                    else {

                        recyclerview.setAdapter(productDetailAdapter);
                        dtlRelativelayout.setTag(1);
//txtdtlview.setText("Detail View");
//proimg.setImageResource(R.drawable.detailview);

                    }
                }
            });


            lstRelativelayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    recyclerview.setAdapter(productDetailAdapter);
                    lstRelativelayout.setVisibility(View.GONE);
                    dtlRelativelayout.setVisibility(View.VISIBLE);
                    dtlRelativelayout.setTag(1);
                }
            });
         //   rl1.setVisibility(View.GONE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void getallbrand() {
        try {
            if (CommonFunctions.checkConnection(getActivity())) {
                CommonFunctions.createProgressBar(getActivity(), getString(R.string.msg_please_wait));

                LoginResponse loginResponse = CommonFunctions.getloginresponse(getActivity());
                String url = IndiaMartConfig.WEBURL + IndiaMartConfig.get_all_brand;
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
                            allBrandResp = gson.fromJson(response.toString(), AllBrandResp.class);
                            if (allBrandResp.status) {
                                setbrandadapter();
                            } else {
                                Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT).show();
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
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setbrandadapter() {
        try {
            AllBrandResp.DataEntity alltemp=new AllBrandResp.DataEntity();
            alltemp.id=-1;
            alltemp.name=getString(R.string.brandtype);
            allBrandResp.data.add(0,alltemp);
            allBrandAdapter=new AllBrandAdapter(getActivity(), allBrandResp.data, new AllBrandAdapter.AdapterCallback() {
                @Override
                public void onSubcategoryClick(AllBrandResp.DataEntity dataEntitytemp) {
                    try {
                        brandid=dataEntitytemp.id+"";
                        getCategory(search_term);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            spCategory.setAdapter(allBrandAdapter);
            spCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    try {
                        if(position>0)
                        {
                            brandid=allBrandResp.data.get(position).id+"";
                            getCategory(search_term);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getsellertype() {
        try {
            if (CommonFunctions.checkConnection(getActivity())) {
                CommonFunctions.createProgressBar(getActivity(), getString(R.string.msg_please_wait));

                LoginResponse loginResponse = CommonFunctions.getloginresponse(getActivity());
                String url = IndiaMartConfig.WEBURL + IndiaMartConfig.get_all_seller_type;
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
                            sellerTypeResp = gson.fromJson(response.toString(), SellerTypeResp.class);
                            if (sellerTypeResp.status) {
                                setselleradapter();
                            } else {
                                Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT).show();
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
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setselleradapter() {
        try {
            SellerTypeResp.DataEntity alltemp=new SellerTypeResp.DataEntity();
            alltemp.id=-1;
            alltemp.name=getString(R.string.sellertype);
            sellerTypeResp.data.add(0,alltemp);
            sellerTypeAdapter=new SellerTypeAdapter(getActivity(), sellerTypeResp.data, new SellerTypeAdapter.AdapterCallback() {
                @Override
                public void onSubcategoryClick(SellerTypeResp.DataEntity dataEntitytemp) {
                    try {
                        sellertype=dataEntitytemp.id+"";
                        getCategory(search_term);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            spSellerType.setAdapter(sellerTypeAdapter);
            spSellerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    try {
                        if(position>0)
                        {
                            sellertype=sellerTypeResp.data.get(position).id+"";
                            getCategory(search_term);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getCategory(String search_term) {
        try {

            CommonFunctions.createProgressBar(getActivity(), getString(R.string.msg_please_wait));

            LoginResponse loginResponse = CommonFunctions.getloginresponse(getActivity());
            String url = IndiaMartConfig.WEBURL + IndiaMartConfig.search_product+search_term;
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
                        productDetailResponse = gson.fromJson(response.toString(), ProductDetailResponse.class);
                        if (productDetailResponse.status) {
                            setdata();
                            tv_empty.setVisibility(View.GONE);
                        } else {
                            Toast.makeText(getActivity(), productDetailResponse.message, Toast.LENGTH_SHORT).show();
                            tv_empty.setVisibility(View.VISIBLE);
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

    private void setdata() {
        try {
            if (productDetailResponse.data != null) {

                productDetailAdapter=new ProductDetailAdapter(getActivity(), productDetailResponse.data, new ProductDetailAdapter.AdapterCallback() {
                    @Override
                    public void onSubcategoryClick(ProductDetailResponse.DataEntity items) {

                    }

                    @Override
                    public void call(ProductDetailResponse.DataEntity items) {
                        try {
                            Dexter.withContext(getActivity())
                                    .withPermission(Manifest.permission.CALL_PHONE)
                                    .withListener(new PermissionListener() {


                                        @Override
                                        public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                                            try {
                                                Intent callIntent = new Intent(Intent.ACTION_CALL);
                                                callIntent.setData(Uri.parse("tel:"+items.sellerContactno));
                                                startActivity(callIntent);
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
                    public void submit(ProductDetailResponse.DataEntity items) {
                        try {
                            Intent i=new Intent(getActivity(), QuotePopup.class);
                            i.putExtra(Constants.product_id,items.productId+"");
                            i.putExtra(Constants.prod_name,items.name+"");
                            i.putExtra(Constants.prod_unit,items.unitName+"");
                            i.putExtra(Constants.pro_pic,items.productImages.get(0).image+"");
                            i.putExtra(Constants.price,items.price+"");
                            startActivity(i);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void Detail(ProductDetailResponse.DataEntity items) {
                        try {
                            ProductDetailView productDetailView=new ProductDetailView();
                            Bundle bundle=new Bundle();
                            if(items.productId!=null)
                            bundle.putString(Constants.product_id,items.productId);
                            else
                            bundle.putString(Constants.product_id,items.id+"");
                            productDetailView.setArguments(bundle);
                            ((DashboardActivity)getActivity()).addFragment(productDetailView);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onSubcategoryViewAllClick(ProductDetailResponse.DataEntity items) {

                    }


                },"");

                recyclerview.setAdapter(productDetailAdapter);
                productDetailListAdapter = new ProductDetailListAdapter(getActivity(), productDetailResponse.data, new ProductDetailListAdapter.AdapterCallback() {


                    @Override
                    public void onSubcategoryClick(ProductDetailResponse.DataEntity items) {

                    }

                    @Override
                    public void oncall(ProductDetailResponse.DataEntity items) {
                        try {
                            calltoseller(items);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void submit(ProductDetailResponse.DataEntity items) {
                        try {
                            Intent i=new Intent(getActivity(), QuotePopup.class);
                            i.putExtra(Constants.product_id,items.productId+"");
                            i.putExtra(Constants.prod_name,items.name+"");
                            i.putExtra(Constants.prod_unit,items.unitName);
                            i.putExtra(Constants.pro_pic,items.image+"");
                            i.putExtra(Constants.price,items.price+"");
                            startActivity(i);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void detail(ProductDetailResponse.DataEntity items) {
                        try {
                            ProductDetailView productDetailView=new ProductDetailView();
                            Bundle bundle=new Bundle();
                            if(items.productId!=null)
                            bundle.putString(Constants.product_id,items.productId);
                            else
                                bundle.putString(Constants.product_id,items.id+"");
                            productDetailView.setArguments(bundle);
                            ((DashboardActivity)getActivity()).addFragment(productDetailView);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onSubcategoryViewAllClick(ProductDetailResponse.DataEntity items) {

                    }
                },"");



            }
        }

        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void calltoseller(ProductDetailResponse.DataEntity items) {
        try {
            Dexter.withContext(getActivity())
                    .withPermission(Manifest.permission.CALL_PHONE)
                    .withListener(new PermissionListener() {


                        @Override
                        public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                            try {
                                Intent callIntent = new Intent(Intent.ACTION_CALL);
                                callIntent.setData(Uri.parse("tel:"+items.sellerContactno));
                                startActivity(callIntent);
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
}
