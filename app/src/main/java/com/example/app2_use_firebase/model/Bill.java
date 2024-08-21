package com.example.app2_use_firebase.model;

public class Bill {
    private String _id;
    private String date;
    private String address;
    private String fullName;
    private String payment;
    private String status;
    private String phone;
    private int totalAmount;
    private String userId;

    public Bill(String _id, String date, String address, String fullName, String payment, String status, String phone, int totalAmount, String userId) {
        this._id = _id;
        this.date = date;
        this.address = address;
        this.fullName = fullName;
        this.payment = payment;
        this.status = status;
        this.phone = phone;
        this.totalAmount = totalAmount;
        this.userId = userId;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Bill{" +
                "_id='" + _id + '\'' +
                ", date=" + date +
                ", address='" + address + '\'' +
                ", fullName='" + fullName + '\'' +
                ", payment='" + payment + '\'' +
                ", status='" + status + '\'' +
                ", phone='" + phone + '\'' +
                ", totalAmount=" + totalAmount +
                ", userId='" + userId + '\'' +
                '}';
    }
}
