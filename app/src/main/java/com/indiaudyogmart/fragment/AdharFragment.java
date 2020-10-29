package com.indiaudyogmart.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.UploadProgressListener;
import com.indiaudyogmart.R;
import com.indiaudyogmart.adpter.ImageAdapter;
import com.indiaudyogmart.config.CommonFunctions;
import com.indiaudyogmart.config.Constants;
import com.indiaudyogmart.config.IndiaMartConfig;
import com.indiaudyogmart.model.LoginResponse;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import org.json.JSONObject;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import droidninja.filepicker.FilePickerBuilder;
import droidninja.filepicker.FilePickerConst;
import droidninja.filepicker.utils.ContentUriUtils;

import static droidninja.filepicker.FilePickerConst.REQUEST_CODE_PHOTO;

public class AdharFragment extends Fragment {
    View rootView;
    @BindView(R.id.tv_ifsc)
    TextView tvIfsc;
    @BindView(R.id.et_adharname)
    EditText etAdharname;
    @BindView(R.id.tv_cheque)
    TextView tvCheque;
    @BindView(R.id.et_adharnumber)
    EditText etAdharnumber;
    @BindView(R.id.btn_upload_video)
    TextView btnUploadVideo;
    @BindView(R.id.adhar_info)
    RelativeLayout adharInfo;
    @BindView(R.id.rv_panimage)
    RecyclerView rvPanimage;
    @BindView(R.id.btn_sendreq)
    Button btnSendreq;
    private ArrayList<Uri> photoPaths = new ArrayList<>();
    ImageAdapter imageAdapter_image;
    private static final int MAX_ATTACHMENT_COUNT = 3;
    public AdharFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.activity_adhar, container, false);
        init();
        return rootView;

    }

    private void init() {
        try {
            ButterKnife.bind(this, rootView);

            StaggeredGridLayoutManager layoutManager1 =
                    new StaggeredGridLayoutManager(3, OrientationHelper.VERTICAL);
            layoutManager1.setGapStrategy(
                    StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
            rvPanimage.setLayoutManager(layoutManager1);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick({R.id.btn_upload_video, R.id.btn_sendreq})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_upload_video:
                checkPermission(1);
                break;
            case R.id.btn_sendreq:
                sendpanreq();
                break;
        }
    }
    private void sendpanreq() {
        try {
            try {
                if (CommonFunctions.checkConnection(getActivity())) {
                    if (TextUtils.isEmpty(etAdharname.getText().toString())) {
                        Toast.makeText(getActivity(), R.string.error_adharname, Toast.LENGTH_SHORT).show();
                        etAdharname.requestFocus();
                        return;
                    } else if (TextUtils.isEmpty(etAdharnumber.getText().toString())) {
                        Toast.makeText(getActivity(), R.string.error_adhar2, Toast.LENGTH_SHORT).show();
                        etAdharnumber.requestFocus();
                        return;
                    }

                    else {
                        senddetails();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void senddetails() {
        try {
            if (CommonFunctions.checkConnection(getActivity())) {
                try {
                    CommonFunctions.createProgressBar(getActivity(), getString(R.string.msg_please_wait));
                    LoginResponse loginResponse = CommonFunctions.getloginresponse(getActivity());
                    String url = IndiaMartConfig.WEBURL + IndiaMartConfig.sendkycrequest;

                    Map<String, String> mParams = new HashMap<>();
                    mParams.put(Constants.aadhar_name, etAdharname.getText().toString().trim());
                    mParams.put(Constants.aadhar_number, etAdharnumber.getText().toString());


                    Map<String, File> multiPartFileMap = new HashMap<>();
                    List<File> files = new ArrayList<>();
                    if (photoPaths.size() > 0) {

                        for (int i = 0; i < photoPaths.size(); i++) {
                            //ContentUriUtils.INSTANCE.getFilePath(PostQuery.this, docPaths.get(j));
                            File file1 = new File(ContentUriUtils.INSTANCE.getFilePath(getActivity(),photoPaths.get(i)));
                            files.add(file1);
                        }

                    }
                    AndroidNetworking.upload(url)
                            .addMultipartFileList(Constants.adhar_document_key, files)
                            .addHeaders(Constants.headerkey,loginResponse.data.encryptUserId)
                            .addMultipartParameter(mParams)
                            .setTag("uploadTest")
                            .build()
                            .setUploadProgressListener(new UploadProgressListener() {
                                @Override
                                public void onProgress(long bytesUploaded, long totalBytes) {
                                    // do anything with progress
                                    Log.e("bytesUploaded-->"+bytesUploaded,"totalBytes---->"+totalBytes);
                                }
                            })
                            .getAsJSONObject(new JSONObjectRequestListener() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    // do anything with response
                                    CommonFunctions.destroyProgressBar();
                                    try {


                                        if (response.getBoolean(Constants.status)) {
                                            Toast.makeText(getActivity(), response.getString(Constants.message), Toast.LENGTH_SHORT).show();




                                        } else {
                                            Toast.makeText(getActivity(), R.string.msg_server_error, Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                                @Override
                                public void onError(ANError error) {
                                    // handle error
                                    CommonFunctions.destroyProgressBar();
                                    Log.e("error-->",error.getErrorDetail());
                                    try {

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            });


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void checkPermission(int from) {
        try {


            Dexter.withContext(getActivity())
                    .withPermissions(
                            Manifest.permission.CAMERA,
                            Manifest.permission.RECORD_AUDIO,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE
                    ).withListener(new MultiplePermissionsListener() {
                @Override
                public void onPermissionsChecked(MultiplePermissionsReport report) {


                    openphoto();


                }

                @Override
                public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                    permissionToken.continuePermissionRequest();


                }


            }).check();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void openphoto() {
        try {
            FilePickerBuilder.getInstance()
                    .setMaxCount(MAX_ATTACHMENT_COUNT) //optional

                    .enableImagePicker(true)
                    .setSelectedFiles(photoPaths) //optional
                    .enableVideoPicker(false)
                    .pickPhoto(this);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_PHOTO:
                if (resultCode == Activity.RESULT_OK && data != null) {

                    ArrayList<Uri> dataList = data.getParcelableArrayListExtra(FilePickerConst.KEY_SELECTED_MEDIA);
                    if (dataList != null) {

                        photoPaths.clear();
                        photoPaths.addAll(dataList);

                        imageAdapter_image = new ImageAdapter(getActivity(), photoPaths, new ImageAdapter.ImageAdapterListener() {
                            @Override
                            public void onItemClick(Uri uri) {
                                try {
                                    //make sure to use this getFilePath method from worker thread
                                    String path = ContentUriUtils.INSTANCE.getFilePath(rvPanimage.getContext(), uri);
                                    if (path != null) {
                                        Toast.makeText(rvPanimage.getContext(), path, Toast.LENGTH_SHORT).show();
                                    }
                                } catch (URISyntaxException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        rvPanimage.setAdapter(imageAdapter_image);


                    }
                }


        }

    }
}
