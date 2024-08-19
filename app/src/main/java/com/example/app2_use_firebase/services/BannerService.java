package com.example.app2_use_firebase.services;

import android.util.Log;

import com.example.app2_use_firebase.model.Banner;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;

public class BannerService {
    private static BannerService instance;
    private static final String GET_BANNERS_METHOD_NAME = "GetAllBanners";
    private static final String GET_BANNERS_SOAP_ACTION = "http://tempuri.org/IService1/GetAllBanners";

    public static BannerService getInstance() {
        if (instance == null) instance = new BannerService();
        return instance;
    }

    public List<Banner> getAllBanners(String NAMESPACE, String URL) {
        List<Banner> banners = new ArrayList<>();
        try {
            SoapObject request = new SoapObject(NAMESPACE, GET_BANNERS_METHOD_NAME);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.setOutputSoapObject(request);

            HttpTransportSE transport = new HttpTransportSE(URL);
            transport.call(GET_BANNERS_SOAP_ACTION, envelope);

            SoapObject response = (SoapObject) envelope.bodyIn;
            Log.d("SOAP Response", response.toString());

            SoapObject getBannersResult = (SoapObject) response.getProperty("GetBannersResult");
            Log.d("GetBannersResult", getBannersResult.toString());

            for (int i = 0; i < getBannersResult.getPropertyCount(); i++) {
                SoapObject bannerObject = (SoapObject) getBannersResult.getProperty(i);
                SoapObject idObject = (SoapObject) bannerObject.getProperty("Id");
                String id = idObject.getProperty("_a").toString() + idObject.getProperty("_b").toString() + idObject.getProperty("_c").toString();
                String url = bannerObject.getProperty("url").toString();
                banners.add(new Banner(id, url));
            }

        } catch (Exception e) {
            Log.e("SoapClient", "Error: " + e.getMessage(), e);
        }
        return banners;
    }
}
