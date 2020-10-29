package com.indiaudyogmart.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ChatListDetailResponse {

    @Expose
    @SerializedName("data")
    public List<DataEntity> data;
    @Expose
    @SerializedName("message")
    public String message;
    @Expose
    @SerializedName("status")
    public boolean status;

    public static class DataEntity {
        @Expose
        @SerializedName("receiver")
        public ReceiverEntity receiver;
        @Expose
        @SerializedName("sender")
        public SenderEntity sender;
        @Expose
        @SerializedName("updated_at")
        public String updatedAt;
        @Expose
        @SerializedName("created_at")
        public String createdAt;
        @Expose
        @SerializedName("is_read")
        public int isRead;
        @Expose
        @SerializedName("message")
        public String message;
        @Expose
        @SerializedName("to")
        public int to;
        @Expose
        @SerializedName("from")
        public int from;
        @Expose
        @SerializedName("id")
        public int id;

    }

    public static class ReceiverEntity {
        @Expose
        @SerializedName("last_login_time")
        public String lastLoginTime;
        @Expose
        @SerializedName("updated_at")
        public String updatedAt;
        @Expose
        @SerializedName("created_at")
        public String createdAt;
        @Expose
        @SerializedName("gst_no")
        public String gstNo;
        @Expose
        @SerializedName("pan_no")
        public String panNo;
        @Expose
        @SerializedName("company_pincode")
        public String companyPincode;
        @Expose
        @SerializedName("company_address")
        public String companyAddress;
        @Expose
        @SerializedName("company_location")
        public String companyLocation;
        @Expose
        @SerializedName("company_url")
        public String companyUrl;
        @Expose
        @SerializedName("company_name")
        public String companyName;
        @Expose
        @SerializedName("profile_pic")
        public String profilepic;
        @Expose
        @SerializedName("close_account_req")
        public int closeAccountReq;
        @Expose
        @SerializedName("pincode")
        public String pincode;
        @Expose
        @SerializedName("address")
        public String address;
        @Expose
        @SerializedName("gender")
        public String gender;
        @Expose
        @SerializedName("dob")
        public String dob;
        @Expose
        @SerializedName("locality")
        public String locality;
        @Expose
        @SerializedName("city")
        public int city;
        @Expose
        @SerializedName("state")
        public int state;
        @Expose
        @SerializedName("country")
        public int country;
        @Expose
        @SerializedName("contactno")
        public String contactno;
        @Expose
        @SerializedName("lastname")
        public String lastname;
        @Expose
        @SerializedName("firstname")
        public String firstname;
        @Expose
        @SerializedName("parentid")
        public int parentid;
        @Expose
        @SerializedName("status")
        public int status;
        @Expose
        @SerializedName("roleid")
        public int roleid;
        @Expose
        @SerializedName("email")
        public String email;
        @Expose
        @SerializedName("name")
        public String name;
        @Expose
        @SerializedName("id")
        public int id;
    }

    public static class SenderEntity {
        @Expose
        @SerializedName("last_login_time")
        public String lastLoginTime;
        @Expose
        @SerializedName("updated_at")
        public String updatedAt;
        @Expose
        @SerializedName("created_at")
        public String createdAt;
        @Expose
        @SerializedName("gst_no")
        public String gstNo;
        @Expose
        @SerializedName("pan_no")
        public String panNo;
        @Expose
        @SerializedName("company_pincode")
        public String companyPincode;
        @Expose
        @SerializedName("company_address")
        public String companyAddress;
        @Expose
        @SerializedName("profile_pic")
        public String profilepic;
        @Expose
        @SerializedName("company_location")
        public String companyLocation;
        @Expose
        @SerializedName("company_name")
        public String companyName;
        @Expose
        @SerializedName("close_account_req")
        public int closeAccountReq;
        @Expose
        @SerializedName("locality")
        public String locality;
        @Expose
        @SerializedName("city")
        public int city;
        @Expose
        @SerializedName("state")
        public int state;
        @Expose
        @SerializedName("country")
        public int country;
        @Expose
        @SerializedName("contactno")
        public String contactno;
        @Expose
        @SerializedName("parentid")
        public int parentid;
        @Expose
        @SerializedName("status")
        public int status;
        @Expose
        @SerializedName("two_factor_code")
        public String twoFactorCode;
        @Expose
        @SerializedName("roleid")
        public int roleid;
        @Expose
        @SerializedName("email")
        public String email;
        @Expose
        @SerializedName("name")
        public String name;
        @Expose
        @SerializedName("id")
        public int id;
    }
}
