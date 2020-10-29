package com.indiaudyogmart.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public  class QuoyeDetailResp {


    @Expose
    @SerializedName("data")
    public DataEntity data;
    @Expose
    @SerializedName("message")
    public String message;
    @Expose
    @SerializedName("status")
    public boolean status;

    public static class DataEntity {
        @Expose
        @SerializedName("messages")
        public List<MessagesEntity> messages;
        @Expose
        @SerializedName("seller_data")
        public SellerDataEntity sellerData;
        @Expose
        @SerializedName("quote_data")
        public List<QuoteDataEntity> quoteData;
        @Expose
        @SerializedName("history_send_quote_request_id")
        public int historySendQuoteRequestId;
    }

    public static class MessagesEntity {
        @Expose
        @SerializedName("receiver")
        public ReceiverEntity receiver;
        @Expose
        @SerializedName("sender")
        public SenderEntity sender;
        @Expose
        @SerializedName("hsqr_customer_id")
        public int hsqrCustomerId;
        @Expose
        @SerializedName("hsqr_status")
        public String hsqrStatus;
        @Expose
        @SerializedName("hnote_for_customer")
        public String hnoteForCustomer;
        @Expose
        @SerializedName("hshipping_time")
        public String hshippingTime;
        @Expose
        @SerializedName("htotal_price")
        public int htotalPrice;
        @Expose
        @SerializedName("hno_of_sample")
        public int hnoOfSample;
        @Expose
        @SerializedName("hsamaple_charge_per_unit")
        public int hsamapleChargePerUnit;
        @Expose
        @SerializedName("hsample_security_charge")
        public String hsampleSecurityCharge;
        @Expose
        @SerializedName("hhassample")
        public String hhassample;
        @Expose
        @SerializedName("hquote_price_per_unit")
        public int hquotePricePerUnit;
        @Expose
        @SerializedName("hquote_qty")
        public int hquoteQty;
        @Expose
        @SerializedName("hseller_id")
        public int hsellerId;
        @Expose
        @SerializedName("hrfq_product_id")
        public int hrfqProductId;
        @Expose
        @SerializedName("hrfq_id")
        public int hrfqId;
        @Expose
        @SerializedName("hsend_quote_request_id")
        public int hsendQuoteRequestId;
        @Expose
        @SerializedName("updated_at")
        public String updatedAt;
        @Expose
        @SerializedName("created_at")
        public String createdAt;
        @Expose
        @SerializedName("date")
        public String date;
        @Expose
        @SerializedName("status")
        public String status;
        @Expose
        @SerializedName("receiver_id")
        public int receiverId;
        @Expose
        @SerializedName("sender_id")
        public int senderId;
        @Expose
        @SerializedName("message")
        public String message;
        @Expose
        @SerializedName("history_send_quote_request_id")
        public int historySendQuoteRequestId;
        @Expose
        @SerializedName("send_quote_request_id")
        public int sendQuoteRequestId;
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

    public static class SellerDataEntity {
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
        @SerializedName("remember_token")
        public String rememberToken;
        @Expose
        @SerializedName("password")
        public String password;
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

    public static class QuoteDataEntity {
        @Expose
        @SerializedName("send_quote_request_id")
        public int sendQuoteRequestId;
        @Expose
        @SerializedName("product_unit_name")
        public String productUnitName;
        @Expose
        @SerializedName("product_unit_id")
        public int productUnitId;
        @Expose
        @SerializedName("sqr_active_close")
        public String sqrActiveClose;
        @Expose
        @SerializedName("sqr_customer_id")
        public int sqrCustomerId;
        @Expose
        @SerializedName("sqr_status")
        public String sqrStatus;
        @Expose
        @SerializedName("note_for_customer")
        public String noteForCustomer;
        @Expose
        @SerializedName("shipping_time")
        public String shippingTime;
        @Expose
        @SerializedName("total_price")
        public int totalPrice;
        @Expose
        @SerializedName("no_of_sample")
        public int noOfSample;
        @Expose
        @SerializedName("samaple_charge_per_unit")
        public int samapleChargePerUnit;
        @Expose
        @SerializedName("sample_security_charge")
        public String sampleSecurityCharge;
        @Expose
        @SerializedName("hassample")
        public String hassample;
        @Expose
        @SerializedName("quote_price_per_unit")
        public int quotePricePerUnit;
        @Expose
        @SerializedName("quote_qty")
        public int quoteQty;
        @Expose
        @SerializedName("seller_id")
        public int sellerId;
        @Expose
        @SerializedName("rfq_product_id")
        public int rfqProductId;
        @Expose
        @SerializedName("rfqp_unit")
        public int rfqpUnit;
        @Expose
        @SerializedName("rfqp_status")
        public int rfqpStatus;
        @Expose
        @SerializedName("rfq_id")
        public int rfqId;
        @Expose
        @SerializedName("rfqp_issample")
        public String rfqpIssample;
        @Expose
        @SerializedName("rfqp_product_image")
        public String rfqpProductImage;
        @Expose
        @SerializedName("rfqp_priceperqty")
        public int rfqpPriceperqty;
        @Expose
        @SerializedName("rfqp_qty")
        public int rfqpQty;
        @Expose
        @SerializedName("rfqp_description")
        public String rfqpDescription;
        @Expose
        @SerializedName("rfqp_name")
        public String rfqpName;
        @Expose
        @SerializedName("product_id")
        public int productId;
        @Expose
        @SerializedName("product_is_avaliable")
        public String productIsAvaliable;
        @Expose
        @SerializedName("category_id")
        public int categoryId;
        @Expose
        @SerializedName("updated_at")
        public String updatedAt;
        @Expose
        @SerializedName("created_at")
        public String createdAt;
        @Expose
        @SerializedName("status")
        public String status;
        @Expose
        @SerializedName("customer_id")
        public int customerId;
        @Expose
        @SerializedName("quote_email_id")
        public String quoteEmailId;
        @Expose
        @SerializedName("quote_conatct_no")
        public String quoteConatctNo;
        @Expose
        @SerializedName("quote_address")
        public String quoteAddress;
        @Expose
        @SerializedName("quote_company_name")
        public String quoteCompanyName;
        @Expose
        @SerializedName("quote_customer_name")
        public String quoteCustomerName;
        @Expose
        @SerializedName("quote_desc")
        public String quoteDesc;
        @Expose
        @SerializedName("quote_title")
        public String quoteTitle;
        @Expose
        @SerializedName("id")
        public int id;
    }
}
