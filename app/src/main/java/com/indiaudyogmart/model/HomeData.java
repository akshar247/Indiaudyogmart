package com.indiaudyogmart.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public  class HomeData {

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
        @SerializedName("product_deals_image_path")
        public String productDealsImagePath;
        @Expose
        @SerializedName("trending_category_image_path")
        public String trendingCategoryImagePath;
        @Expose
        @SerializedName("banner_image_path")
        public String bannerImagePath;
        @Expose
        @SerializedName("countries")
        public List<CountriesEntity> countries;
        @Expose
        @SerializedName("product_deals")
        public List<ProductDealsEntity> productDeals;
        @Expose
        @SerializedName("suggest_for_you")
        public List<ProductDealsEntity> suggest_for_you;
        @Expose
        @SerializedName("premium_collection")
        public List<PremiumCollectionEntity> premiumCollection;
        @Expose
        @SerializedName("trending_category")
        public List<TrendingCategoryEntity> trendingCategory;
        @Expose
        @SerializedName("deal_of_the_day_banner")
        public List<DealOfTheDayBannerEntity> dealOfTheDayBanner;
        @Expose
        @SerializedName("banners")
        public List<BannersEntity> banners;
        @Expose
        @SerializedName("important_suppliers")
        public List<ImportantSuppliersEntity> importantSuppliers;
    }

    public static class CountriesEntity {
        @Expose
        @SerializedName("created_at")
        public String createdAt;
        @Expose
        @SerializedName("status")
        public int status;
        @Expose
        @SerializedName("shortcode")
        public String shortcode;
        @Expose
        @SerializedName("name")
        public String name;
        @Expose
        @SerializedName("id")
        public int id;
    }

    public static class ProductDealsEntity {
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
        @SerializedName("unit_id")
        public int unitId;
        @Expose
        @SerializedName("product_price")
        public int productPrice;
        @Expose
        @SerializedName("product_name")
        public String productName;
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
        @SerializedName("image")
        public String image;
        @Expose
        @SerializedName("product_id")
        public int productId;
        @Expose
        @SerializedName("id")
        public int id;
    }

    public static class PremiumCollectionEntity {
        @Expose
        @SerializedName("category_name")
        public String categoryName;
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
        @SerializedName("image")
        public String image;
        @Expose
        @SerializedName("type")
        public String type;
        @Expose
        @SerializedName("category_id")
        public int categoryId;
        @Expose
        @SerializedName("id")
        public int id;
    }

    public static class TrendingCategoryEntity {
        @Expose
        @SerializedName("trendingcategory")
        public TrendingcategoryEntity1 trendingcategory;
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
        @SerializedName("image")
        public String image;
        @Expose
        @SerializedName("type")
        public String type;
        @Expose
        @SerializedName("category_id")
        public int categoryId;
        @Expose
        @SerializedName("id")
        public int id;
    }

    public static class TrendingcategoryEntity1 {
        @Expose
        @SerializedName("childs")
        public List<ChildsEntity> childs;
        @Expose
        @SerializedName("image_icon")
        public String imageIcon;
        @Expose
        @SerializedName("image")
        public String image;
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
        @SerializedName("slug")
        public String slug;
        @Expose
        @SerializedName("parent_id")
        public int parentId;
        @Expose
        @SerializedName("name")
        public String name;
        @Expose
        @SerializedName("id")
        public int id;
    }

    public static class ChildsEntity {
        @Expose
        @SerializedName("image_icon")
        public String imageIcon;
        @Expose
        @SerializedName("image")
        public String image;
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
        @SerializedName("slug")
        public String slug;
        @Expose
        @SerializedName("parent_id")
        public int parentId;
        @Expose
        @SerializedName("name")
        public String name;
        @Expose
        @SerializedName("id")
        public int id;
    }

    public static class DealOfTheDayBannerEntity {
        @Expose
        @SerializedName("type")
        public String type;
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
        @SerializedName("image")
        public String image;
        @Expose
        @SerializedName("btn_link")
        public String btnLink;
        @Expose
        @SerializedName("btn_name")
        public String btnName;
        @Expose
        @SerializedName("price")
        public String price;
        @Expose
        @SerializedName("description")
        public String description;
        @Expose
        @SerializedName("title")
        public String title;
        @Expose
        @SerializedName("id")
        public int id;
    }

    public static class BannersEntity {
        @Expose
        @SerializedName("type")
        public String type;
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
        @SerializedName("image")
        public String image;
        @Expose
        @SerializedName("btn_link")
        public String btnLink;
        @Expose
        @SerializedName("btn_name")
        public String btnName;
        @Expose
        @SerializedName("price")
        public String price;
        @Expose
        @SerializedName("description")
        public String description;
        @Expose
        @SerializedName("title")
        public String title;
        @Expose
        @SerializedName("id")
        public int id;
    }

    public static class ImportantSuppliersEntity {
        @Expose
        @SerializedName("category")
        public CategoryEntity category;
        @Expose
        @SerializedName("category_order")
        public int categoryOrder;
        @Expose
        @SerializedName("category_id")
        public int categoryId;
    }

    public static class CategoryEntity {
        @Expose
        @SerializedName("image_icon")
        public String imageIcon;
        @Expose
        @SerializedName("image")
        public String image;
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
        @SerializedName("slug")
        public String slug;
        @Expose
        @SerializedName("parent_id")
        public int parentId;
        @Expose
        @SerializedName("name")
        public String name;
        @Expose
        @SerializedName("id")
        public int id;
    }
}
