package com.indiaudyogmart.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RequestResponse {

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
        @SerializedName("requirement_time")
        public String requirementTime;
        @Expose
        @SerializedName("location")
        public String location;
        @Expose
        @SerializedName("y_location")
        public String yLocation;
        @Expose
        @SerializedName("qty_unit")
        public String qtyUnit;
        @Expose
        @SerializedName("qty")
        public String qty;
        @Expose
        @SerializedName("message")
        public String message;
        @Expose
        @SerializedName("product_price")
        public String productPrice;
        @Expose
        @SerializedName("updated_at")
        public String updatedAt;
        @Expose
        @SerializedName("created_at")
        public String createdAt;
        @Expose
        @SerializedName("seller_id")
        public int sellerId;
        @Expose
        @SerializedName("details")
        public String details;
        @Expose
        @SerializedName("product_name")
        public String productName;
        @Expose
        @SerializedName("mobile")
        public String mobile;
        @Expose
        @SerializedName("email")
        public String email;
        @Expose
        @SerializedName("id")
        public int id;
    }
}
