package com.example.app2_use_firebase.model;

public class Banner {
    private String id;
    private String url;

    public Banner(String id, String url) {
        this.id = id;
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }
}
