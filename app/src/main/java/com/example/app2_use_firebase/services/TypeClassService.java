package com.example.app2_use_firebase.services;

import com.example.app2_use_firebase.Domain.ItemsDomain;
import com.example.app2_use_firebase.web_service.SoapClient;

public class TypeClassService {
    private static TypeClassService instance;

    public static TypeClassService getInstance() {
        if (instance == null) instance = new TypeClassService();
        return instance;
    }

    public ItemsDomain selectType(String type, String id) {
        switch (type) {
            case "ItemsPopular":
                return SoapClient.getInstance().getItemsPopularsById(id);

            default:
                return null;
        }

    }
}
