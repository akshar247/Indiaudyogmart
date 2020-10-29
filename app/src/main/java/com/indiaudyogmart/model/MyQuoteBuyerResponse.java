package com.indiaudyogmart.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public  class MyQuoteBuyerResponse {


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
        @SerializedName("product_unit_name")
        public String productUnitName;
        @Expose
        @SerializedName("product_unit_id")
        public int productUnitId;
        @Expose
        @SerializedName("rrffqq_id")
        public int rrffqqId;
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
        @Expose
        @SerializedName("send_quote_request_id ")
        public int send_quote_request_id ;
    }
}
