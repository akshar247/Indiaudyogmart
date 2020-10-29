package com.indiaudyogmart.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public  class MyLeadSellerResponse {


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
        @SerializedName("send_quote_request_id")
        public int sendQuoteRequestId;
        @Expose
        @SerializedName("cust_contactno")
        public String custContactno;
        @Expose
        @SerializedName("cust_email")
        public String custEmail;
        @Expose
        @SerializedName("cust_name")
        public String custName;
        @Expose
        @SerializedName("rfqp_unit")
        public int rfqpUnit;
        @Expose
        @SerializedName("customer_id")
        public int customerId;
        @Expose
        @SerializedName("rfqp_status")
        public int rfqpStatus;
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
        @SerializedName("sqr_active_close")
        public String sqrActiveClose;
        @Expose
        @SerializedName("sqr_customer_id")
        public int sqrCustomerId;
        @Expose
        @SerializedName("updated_at")
        public String updatedAt;
        @Expose
        @SerializedName("created_at")
        public String createdAt;
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
        @SerializedName("rfq_id")
        public int rfqId;
        @Expose
        @SerializedName("id")
        public int id;
    }
}
