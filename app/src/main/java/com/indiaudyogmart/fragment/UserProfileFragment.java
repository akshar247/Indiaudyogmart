package com.indiaudyogmart.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.indiaudyogmart.R;
import com.indiaudyogmart.config.CommonFunctions;
import com.indiaudyogmart.config.Constants;
import com.indiaudyogmart.config.IndiaMartConfig;
import com.indiaudyogmart.dashboard.DashboardActivity;
import com.indiaudyogmart.model.IndustriesResponse;
import com.indiaudyogmart.model.LoginResponse;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;


public class UserProfileFragment extends Fragment {
    View rootView;
    @BindView(R.id.iv_profile)
    CircleImageView ivProfile;
    @BindView(R.id.btn_changeimage)
    Button btnChangeimage;
    @BindView(R.id.tv_name)
    EditText tvName;
    @BindView(R.id.tv_number)
    EditText tvNumber;
    @BindView(R.id.tv_email)
    EditText tvEmail;
    @BindView(R.id.tv_address)
    EditText tvAddress;
    @BindView(R.id.btn_edit_profile)
    Button btnEditProfile;
    private Activity activity;
    private ArrayList<Uri> photoPaths;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_user_profile_fragment, container, false);
        init();
        return rootView;


    }

    private void init() {
        try {
            ButterKnife.bind(this, rootView);
            if(CommonFunctions.checkConnection(getActivity()))
            getprofile();
            btnChangeimage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            btnEditProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                       if(btnEditProfile.getText().toString().equalsIgnoreCase(getString(R.string.profile_label2)))
                       {
                           tvName.setEnabled(true);
                         //  tvNumber.setEnabled(true);
                        //   tvEmail.setEnabled(true);
                           tvAddress.setEnabled(true);
                           btnEditProfile.setText(R.string.saveprofile);
                       }
                       else
                       {
                         saveuserdata();
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

    private void saveuserdata() {
        try {
            if (tvName.getText().toString().length() == 0)
            {
                Toast.makeText(getActivity(), R.string.err_name, Toast.LENGTH_SHORT).show();
                tvName.requestFocus();
                return;
            }

            else if (tvEmail.getText().toString().length() == 0)
            {
                Toast.makeText(getActivity(), R.string.err_email, Toast.LENGTH_SHORT).show();
                tvEmail.requestFocus();
                return;
            }
            else if (tvNumber.getText().toString().length() == 0 || tvNumber.getText().toString().length() < 10)
            {

                Toast.makeText(getActivity(), R.string.err_mobil, Toast.LENGTH_SHORT).show();
                tvNumber.requestFocus();
                return;
            }
            else
            {
                CommonFunctions.createProgressBar(getActivity(), getString(R.string.msg_please_wait));

                LoginResponse loginResponse = CommonFunctions.getloginresponse(getActivity());
                String url = IndiaMartConfig.WEBURL + IndiaMartConfig.update_user_details;
                Map<String, String> mParams = new HashMap<>();
             //   mParams.put(Constants.contactno, tvNumber.getText().toString().trim());
            //    mParams.put(Constants.email, tvEmail.getText().toString().trim());
                mParams.put(Constants.name, tvName.getText().toString().trim());
                mParams.put(Constants.address, tvAddress.getText().toString().trim());
                Log.e("url", url);
                Log.e("mParams", mParams.toString());

                AndroidNetworking.post(url)
                        .addHeaders(Constants.headerkey, loginResponse.data.encryptUserId)
                        .addBodyParameter(mParams)
                        .setTag(url)
                        .setPriority(Priority.HIGH)
                        .build().getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            CommonFunctions.destroyProgressBar();
                            Log.e("res", response.toString());
                            getprofile();
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

    private void getprofile() {
        try {
            CommonFunctions.createProgressBar(getActivity(), getString(R.string.msg_please_wait));

            LoginResponse loginResponse = CommonFunctions.getloginresponse(getActivity());
            String url = IndiaMartConfig.WEBURL + IndiaMartConfig.get_user_data;
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
                        LoginResponse loginResponse1 = gson.fromJson(response.toString(), LoginResponse.class);
                        if (loginResponse1.status)
                        {
                            CommonFunctions.setPreference(getActivity(), Constants.userdata, gson.toJson(loginResponse1));
                            setdata();
                        }
                        else
                        {
                          setdata();
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
            tvName.setText(CommonFunctions.getloginresponse(getActivity()).data.name + "");
            tvNumber.setText(CommonFunctions.getloginresponse(getActivity()).data.contactno + "");
            tvEmail.setText(CommonFunctions.getloginresponse(getActivity()).data.email + "");
            if(CommonFunctions.getloginresponse(getActivity()).data.address!=null)
                tvAddress.setText(CommonFunctions.getloginresponse(getActivity()).data.address + "");
            else
                tvAddress.setText("");
            tvName.setEnabled(false);
            tvNumber.setEnabled(false);
            tvEmail.setEnabled(false);
            tvAddress.setEnabled(false);
            btnEditProfile.setText(R.string.profile_label2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}