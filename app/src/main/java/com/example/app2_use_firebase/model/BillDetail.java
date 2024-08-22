package com.example.app2_use_firebase.model;

import java.util.List;

public class BillDetail {
    private String _id;
    private String billId;
    private List<ProductBuy> products;

    public BillDetail(String _id, String billId, List<ProductBuy> products) {
        this._id = _id;
        this.billId = billId;
        this.products = products;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getBillId() {
        return billId;
    }

    public void setBillId(String billId) {
        this.billId = billId;
    }

    public List<ProductBuy> getProducts() {
        return products;
    }

    public void setProducts(List<ProductBuy> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "BillDetail{" +
                "_id='" + _id + '\'' +
                ", billId='" + billId + '\'' +
                ", products=" + products +
                '}';
    }
}
