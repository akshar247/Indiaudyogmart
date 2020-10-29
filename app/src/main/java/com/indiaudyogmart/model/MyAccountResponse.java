package com.indiaudyogmart.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MyAccountResponse {

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
        @SerializedName("my_reviews")
        public int myReviews;
        @Expose
        @SerializedName("my_tansactions")
        public int myTansactions;
        @Expose
        @SerializedName("my_lead")
        public int myLead;
        @Expose
        @SerializedName("my_orders")
        public int myOrders;
        @Expose
        @SerializedName("my_products")
        public int myProducts;
    }
}
