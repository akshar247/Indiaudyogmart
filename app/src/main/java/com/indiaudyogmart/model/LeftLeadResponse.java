package com.indiaudyogmart.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public  class LeftLeadResponse {


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
        @SerializedName("left_days")
        public int leftDays;
        @Expose
        @SerializedName("sp_image_limit")
        public int spImageLimit;
        @Expose
        @SerializedName("sp_lead_limit")
        public int spLeadLimit;
        @Expose
        @SerializedName("sp_product_limit")
        public int spProductLimit;
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
        @SerializedName("expire_date")
        public String expireDate;
        @Expose
        @SerializedName("start_date")
        public String startDate;
        @Expose
        @SerializedName("package_name")
        public String packageName;
        @Expose
        @SerializedName("package_id")
        public int packageId;
        @Expose
        @SerializedName("id")
        public int id;
    }
}

