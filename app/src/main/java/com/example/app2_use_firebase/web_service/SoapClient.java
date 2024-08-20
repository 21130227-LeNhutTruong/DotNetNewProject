package com.example.app2_use_firebase.web_service;

import com.example.app2_use_firebase.model.Banner;
import com.example.app2_use_firebase.model.ItemsPopular;
import com.example.app2_use_firebase.model.User;
import com.example.app2_use_firebase.services.BannerService;
import com.example.app2_use_firebase.services.ItemsPopularService;
import com.example.app2_use_firebase.services.UserService;

import java.util.List;

public class SoapClient {

    private static final String NAMESPACE = "http://tempuri.org/";
    
//    ngrok http 55685 --host-header="localhost:55685"
    private static final String URL = "https://abb4-2001-ee0-51b8-8f60-2cb6-8982-f8c9-ea09.ngrok-free.app/Service1.svc";

    private static SoapClient instance;

    public static SoapClient getInstance() {
        if (instance == null) instance = new SoapClient();
        return instance;
    }

    public List<Banner> getBanners() {
        return BannerService.getInstance().getAllBanners(NAMESPACE, URL);
    }

    public List<ItemsPopular> getAllItemsPopular() {
        return ItemsPopularService.getInstance().getItemsPopular(NAMESPACE, URL);
    }

    public boolean addUser(User user) {
        return UserService.getInstance().addUser(NAMESPACE, URL, user);
    }

    public boolean register(User user) {
        return UserService.getInstance().register(NAMESPACE, URL, user);
    }

    public User checkLogin(String email, String pass) {
        return UserService.getInstance().checkLogin(NAMESPACE, URL, email, pass);
    }

    public User getUserById(String id) {
        return UserService.getInstance().getUserById(NAMESPACE, URL, id);
    }

    public boolean isExistUser(String email) {
        return UserService.getInstance().isExistUser(NAMESPACE, URL, email);
    }

}
