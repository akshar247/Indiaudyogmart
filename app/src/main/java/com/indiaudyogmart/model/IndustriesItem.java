package com.indiaudyogmart.model;

public class IndustriesItem {
    private int Title;
    private int Image;

    public IndustriesItem(int title, int image) {

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
