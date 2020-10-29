package com.indiaudyogmart.fragment;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.indiaudyogmart.R;
import com.indiaudyogmart.config.CommonFunctions;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class SellerProfileFragment extends Fragment {
    View rootView;
    @BindView(R.id.iv_profile)
    CircleImageView ivProfile;
    @BindView(R.id.btn_changeimage)
    Button btnChangeimage;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_number)
    TextView tvNumber;
    @BindView(R.id.tv_email)
    TextView tvEmail;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.btn_edit_profile)
    Button btnEditProfile;
    private Activity activity;
    private ArrayList<Uri> photoPaths;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.seller_profile_fragment, container, false);
        init();
        return rootView;


    }

    private void init() {
        try {
            ButterKnife.bind(this, rootView);
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
                        /*Intent editprofile = new Intent(getActivity(), EditProfileActivity.class);
                        startActivity(editprofile);*/
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getprofile() {
        try {
            tvName.setText(CommonFunctions.getloginresponse(getActivity()).data.name + "");
            tvNumber.setText(CommonFunctions.getloginresponse(getActivity()).data.contactno + "");
            tvEmail.setText(CommonFunctions.getloginresponse(getActivity()).data.email + "");
            if(CommonFunctions.getloginresponse(getActivity()).data.address!=null)
            tvAddress.setText(CommonFunctions.getloginresponse(getActivity()).data.address + "");
            else
                tvAddress.setText("");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}