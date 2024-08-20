package com.example.app2_use_firebase.web_service;

import com.example.app2_use_firebase.Domain.SliderItems;
import com.example.app2_use_firebase.model.Banner;
import com.example.app2_use_firebase.model.ItemsPopular;
import com.example.app2_use_firebase.services.BannerService;
import com.example.app2_use_firebase.services.ItemsPopularService;
import com.example.app2_use_firebase.services.SliderItemsService;

import java.util.List;

public class SoapClient {

    private static final String NAMESPACE = "http://tempuri.org/";
    
//    ngrok http 55685 --host-header="localhost:55685"
    private static final String URL = "https://5599-14-230-205-81.ngrok-free.app/Service1.svc";

    private static final String HELLO_METHOD_NAME = "hello";
    private static final String HELLO_SOAP_ACTION = "http://tempuri.org/IService1/hello";



    public List<Banner> getBanners() {
        return BannerService.getInstance().getAllBanners(NAMESPACE, URL);
    }

    public List<ItemsPopular> getAllItemsPopular() {
        return ItemsPopularService.getInstance().getItemsPopular(NAMESPACE, URL);
    }

    public List<SliderItems> getSliderItems() {
        return SliderItemsService.getInstance().getSliderItems(NAMESPACE, URL);
    }




}
