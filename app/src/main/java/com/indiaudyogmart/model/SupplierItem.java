package com.indiaudyogmart.model;

public class SupplierItem {
    private int Title;
    private int Image;

    public SupplierItem(int title, int image) {

        Title= title;

        Image = image;
    }

    public int getTitle() {
        return Title;
    }

    public void setTitle(int title) {
        Title = title;
    }

    public int getImage() {
        return Image;
    }

    public void setImage(int image) {
        Image = image;
    }
}







