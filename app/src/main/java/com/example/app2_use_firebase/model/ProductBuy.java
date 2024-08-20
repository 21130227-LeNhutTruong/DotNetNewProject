package com.example.app2_use_firebase.model;

public class ProductBuy {
    private String _id;
    private int quantity;
    private String type;

    public ProductBuy(String _id, int quantity, String type) {
        this._id = _id;
        this.quantity = quantity;
        this.type = type;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "ProductBuy{" +
                "_id='" + _id + '\'' +
                ", quantity=" + quantity +
                ", type='" + type + '\'' +
                '}';
    }
}
