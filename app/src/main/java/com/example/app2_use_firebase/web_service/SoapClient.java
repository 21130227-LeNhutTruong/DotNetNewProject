package com.example.app2_use_firebase.web_service;

import com.example.app2_use_firebase.Domain.SliderItems;
import com.example.app2_use_firebase.model.Banner;
import com.example.app2_use_firebase.model.ItemsPopular;
import com.example.app2_use_firebase.services.BannerService;
import com.example.app2_use_firebase.services.ItemsPopularService;
import com.example.app2_use_firebase.services.SliderItemsService;
import com.google.firebase.firestore.auth.User;

import java.util.List;

public class SoapClient {

    private static final String NAMESPACE = "http://tempuri.org/";
    
//    ngrok http 55685 --host-header="localhost:55685"
    private static final String URL = "https://5599-14-230-205-81.ngrok-free.app/Service1.svc";

    private static final String HELLO_METHOD_NAME = "hello";
    private static final String HELLO_SOAP_ACTION = "http://tempuri.org/IService1/hello";
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

    public List<SliderItems> getSliderItems() {
        return SliderItemsService.getInstance().getSliderItems(NAMESPACE, URL);
    }




}
