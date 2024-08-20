package com.example.app2_use_firebase.Domain;

import java.io.Serializable;
import java.util.ArrayList;

public class SliderItems implements Serializable {
    private String description;
    private ArrayList<String> picUrl;
    private double price;
    private String title;

    public SliderItems(String description, ArrayList<String> picUrl, double price, String title) {
        this.description = description;
        this.picUrl = picUrl;
        this.price = price;
        this.title = title;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<String> getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(ArrayList<String> picUrl) {
        this.picUrl = picUrl;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
