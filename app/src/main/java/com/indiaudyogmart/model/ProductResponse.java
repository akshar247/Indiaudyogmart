package com.indiaudyogmart.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductResponse {


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
        @SerializedName("product_rating")
        public List<ProductRatingEntity> productRating;
        @Expose
        @SerializedName("product_variant_options")
        public List<ProductVariantOptionsEntity> productVariantOptions;
        @Expose
        @SerializedName("product_images")
        public List<ProductImagesEntity> productImages;
        @Expose
        @SerializedName("seller_last_login_time")
        public String sellerLastLoginTime;
        @Expose
        @SerializedName("seller_company_pincode")
        public String sellerCompanyPincode;
        @Expose
        @SerializedName("seller_company_address")
        public String sellerCompanyAddress;
        @Expose
        @SerializedName("seller_contactno")
        public String sellerContactno;
        @Expose
        @SerializedName("seller_company_location")
        public String sellerCompanyLocation;
        @Expose
        @SerializedName("seller_company_name")
        public String sellerCompanyName;
        @Expose
        @SerializedName("seller_name")
        public String sellerName;
        @Expose
        @SerializedName("seller_id")
        public int sellerId;
        @Expose
        @SerializedName("unit_name")
        public String unitName;
        @Expose
        @SerializedName("approved")
        public int approved;
        @Expose
        @SerializedName("gallery_image_id")
        public int galleryImageId;
        @Expose
        @SerializedName("created_by")
        public int createdBy;
        @Expose
        @SerializedName("updated_at")
        public String updatedAt;
        @Expose
        @SerializedName("created_at")
        public String createdAt;
        @Expose
        @SerializedName("status")
        public int status;
        @Expose
        @SerializedName("meta_description")
        public String metaDescription;
        @Expose
        @SerializedName("meta_tags")
        public String metaTags;
        @Expose
        @SerializedName("meta_url")
        public String metaUrl;
        @Expose
        @SerializedName("meta_title2")
        public String metaTitle2;
        @Expose
        @SerializedName("meta_title")
        public String metaTitle;
        @Expose
        @SerializedName("image")
        public String image;
        @Expose
        @SerializedName("detail")
        public String detail;
        @Expose
        @SerializedName("qty")
        public int qty;
        @Expose
        @SerializedName("original_price")
        public int originalPrice;
        @Expose
        @SerializedName("price")
        public int price;
        @Expose
        @SerializedName("sku_variant_id")
        public String skuVariantId;
        @Expose
        @SerializedName("sku_variant")
        public String skuVariant;
        @Expose
        @SerializedName("sku")
        public String sku;
        @Expose
        @SerializedName("unit_id")
        public int unitId;
        @Expose
        @SerializedName("brand_id")
        public int brandId;
        @Expose
        @SerializedName("category_id")
        public int categoryId;
        @Expose
        @SerializedName("product_id")
        public String productId;
        @Expose
        @SerializedName("name")
        public String name;
        @Expose
        @SerializedName("id")
        public int id;
    }

    public static class ProductRatingEntity {
        @Expose
        @SerializedName("name")
        public String name;
        @Expose
        @SerializedName("updated_at")
        public String updatedAt;
        @Expose
        @SerializedName("created_at")
        public String createdAt;
        @Expose
        @SerializedName("comment")
        public String comment;
        @Expose
        @SerializedName("rating")
        public int rating;
        @Expose
        @SerializedName("user_id")
        public int userId;
        @Expose
        @SerializedName("product_id")
        public int productId;
        @Expose
        @SerializedName("id")
        public int id;
    }

    public static class ProductVariantOptionsEntity {
        @Expose
        @SerializedName("variant_name")
        public String variantName;
        @Expose
        @SerializedName("created_by")
        public int createdBy;
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
        @SerializedName("name")
        public String name;
        @Expose
        @SerializedName("variant_value_id")
        public int variantValueId;
        @Expose
        @SerializedName("variant_id")
        public int variantId;
        @Expose
        @SerializedName("product_id")
        public int productId;
        @Expose
        @SerializedName("id")
        public int id;
    }

    public static class ProductImagesEntity {
        @Expose
        @SerializedName("created_at")
        public String createdAt;
        @Expose
        @SerializedName("file_type")
        public String fileType;
        @Expose
        @SerializedName("image")
        public String image;
        @Expose
        @SerializedName("product_id")
        public int productId;
        @Expose
        @SerializedName("name")
        public String name;
        @Expose
        @SerializedName("id")
        public int id;
    }
}
