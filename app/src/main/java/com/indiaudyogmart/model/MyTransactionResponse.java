package com.indiaudyogmart.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MyTransactionResponse {

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
        @SerializedName("st_date")
        public String stDate;
        @Expose
        @SerializedName("st_lead_limit")
        public int stLeadLimit;
        @Expose
        @SerializedName("st_product_limit")
        public int stProductLimit;
        @Expose
        @SerializedName("updated_at")
        public String updatedAt;
        @Expose
        @SerializedName("created_at")
        public String createdAt;
        @Expose
        @SerializedName("st_expire_date")
        public String stExpireDate;
        @Expose
        @SerializedName("st_start_date")
        public String stStartDate;
        @Expose
        @SerializedName("st_seller_id")
        public int stSellerId;
        @Expose
        @SerializedName("st_amount")
        public int stAmount;
        @Expose
        @SerializedName("st_package_name")
        public String stPackageName;
        @Expose
        @SerializedName("st_package_id")
        public int stPackageId;
        @Expose
        @SerializedName("st_type")
        public String stType;
        @Expose
        @SerializedName("st_id")
        public int stId;
    }
}
