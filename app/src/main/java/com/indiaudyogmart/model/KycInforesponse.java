package com.indiaudyogmart.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public  class KycInforesponse {


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
        /*@Expose
        @SerializedName("images")
        private List<String> images;*/
        @Expose
        @SerializedName("get_username")
        public GetUsernameEntity getUsername;

        @Expose
        @SerializedName("updated_at")
        public String updatedAt;
        @Expose
        @SerializedName("created_at")
        public String createdAt;
        @Expose
        @SerializedName("FromWeb")
        public int fromweb;
        @Expose
        @SerializedName("groupid")
        public String groupid;
        @Expose
        @SerializedName("type")
        public String type;
        @Expose
        @SerializedName("admincomment")
        public String admincomment;
        @Expose
        @SerializedName("verifiedby")
        public String verifiedby;
        @Expose
        @SerializedName("status")
        public String status;
        @Expose
        @SerializedName("newvalue")
        public String newvalue;
        @Expose
        @SerializedName("oldvalue")
        public String oldvalue;
        @Expose
        @SerializedName("fieldname")
        public String fieldname;
        @Expose
        @SerializedName("requesttype")
        public String requesttype;
        @Expose
        @SerializedName("userid")
        public int userid;
        @Expose
        @SerializedName("id")
        public int id;
    }

    public static class GetUsernameEntity {
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
        @SerializedName("seller_type")
        public int sellerType;
        @Expose
        @SerializedName("otp_time")
        public String otpTime;
        @Expose
        @SerializedName("otp")
        public int otp;
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
        @SerializedName("close_account_req")
        public int closeAccountReq;
        @Expose
        @SerializedName("shop_name")
        public String shopName;
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
        @SerializedName("profilepic")
        public String profilepic;
        @Expose
        @SerializedName("whatsappno")
        public String whatsappno;
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
        @SerializedName("two_factor_expires_at")
        public String twoFactorExpiresAt;
        @Expose
        @SerializedName("two_factor_code")
        public String twoFactorCode;
        @Expose
        @SerializedName("email_verified_at")
        public String emailVerifiedAt;
        @Expose
        @SerializedName("usertype")
        public String usertype;
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
