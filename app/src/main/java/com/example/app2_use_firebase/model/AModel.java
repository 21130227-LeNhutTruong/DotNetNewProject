package com.example.app2_use_firebase.model;

import java.util.ArrayList;

public abstract class AModel {
    private String _id;
    private String description;
    private double oldPrice;
    private ArrayList<String> picUrl;
    private String des;
    private int price;
    private double rating;
    private int review;
    private String title;

    public AModel(String _id, String description, double oldPrice, ArrayList<String> picUrl, String des, int price, double rating, int review, String title) {
        this._id = _id;
        this.description = description;
        this.oldPrice = oldPrice;
        this.picUrl = picUrl;
        this.des = des;
        this.price = price;
        this.rating = rating;
        this.review = review;
        this.title = title;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getOldPrice() {
        return oldPrice;
    }

    public void setOldPrice(double oldPrice) {
        this.oldPrice = oldPrice;
    }

    public ArrayList<String> getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(ArrayList<String> picUrl) {
        this.picUrl = picUrl;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getReview() {
        return review;
    }

    public void setReview(int review) {
        this.review = review;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "AModel{" +
                "_id='" + _id + '\'' +
                ", description='" + description + '\'' +
                ", oldPrice=" + oldPrice +
                ", picUrl=" + picUrl +
                ", des='" + des + '\'' +
                ", price=" + price +
                ", rating=" + rating +
                ", review=" + review +
                ", title='" + title + '\'' +
                '}';
    }
}
