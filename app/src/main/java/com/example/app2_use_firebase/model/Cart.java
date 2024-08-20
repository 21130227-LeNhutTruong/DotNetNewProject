package com.example.app2_use_firebase.model;

import java.util.List;

public class Cart {
    private String _id;
    private String userId;
    private List<ProductBuy> products;

    public Cart(String _id, String userId, List<ProductBuy> products) {
        this._id = _id;
        this.userId = userId;
        this.products = products;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<ProductBuy> getProducts() {
        return products;
    }

    public void setProducts(List<ProductBuy> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "_id='" + _id + '\'' +
                ", userId='" + userId + '\'' +
                ", products=" + products +
                '}';
    }
}
