package com.indiaudyogmart.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BuyLeadResponse {


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
        @SerializedName("packages")
        public List<PackagesEntity> packages;
        @Expose
        @SerializedName("package_data")
        public PackageDataEntity packageData;
    }

    public static class PackagesEntity {
        @Expose
        @SerializedName("image_limit")
        public int imageLimit;
        @Expose
        @SerializedName("type")
        public String type;
        @Expose
        @SerializedName("base_no")
        public int baseNo;
        @Expose
        @SerializedName("base")
        public String base;
        @Expose
        @SerializedName("lead_limit")
        public int leadLimit;
        @Expose
        @SerializedName("product_limit")
        public int productLimit;
        @Expose
        @SerializedName("updated_at")
        public String updatedAt;
        @Expose
        @SerializedName("created_at")
        public String createdAt;
        @Expose
        @SerializedName("created_by")
        public int createdBy;
        @Expose
        @SerializedName("status")
        public int status;
        @Expose
        @SerializedName("details")
        public String details;
        @Expose
        @SerializedName("price")
        public int price;
        @Expose
        @SerializedName("package_name")
        public String packageName;
        @Expose
        @SerializedName("id")
        public int id;
    }

    public static class PackageDataEntity {
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

