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
            case "ItemsAo":
                return SoapClient.getInstance().getItemsAoById(id);
            case "ItemsAoNam":
                return SoapClient.getInstance().getItemsAoNamById(id);
            case "ItemsAoNu":
                return SoapClient.getInstance().getItemsAoNuById(id);
            case "ItemsTuiXach":
                return SoapClient.getInstance().getItemsTuiXachById(id);
            case "ItemsBag":
                return SoapClient.getInstance().getItemsBagById(id);
            case "ItemsGiay":
                return SoapClient.getInstance().getItemsGiayById(id);
            case "ItemsClothes":
                return SoapClient.getInstance().getItemsClothesById(id);

            default:
                return null;
        }

    }
}
