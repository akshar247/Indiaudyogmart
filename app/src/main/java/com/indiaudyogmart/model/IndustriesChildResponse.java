package com.indiaudyogmart.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public  class IndustriesChildResponse {

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
