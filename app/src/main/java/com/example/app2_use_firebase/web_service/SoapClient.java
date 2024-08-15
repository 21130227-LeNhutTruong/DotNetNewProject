package com.example.app2_use_firebase.web_service;

import android.util.Log;

import com.example.app2_use_firebase.model.Banner;
import com.example.app2_use_firebase.model.ItemsPopular;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;

public class SoapClient {

    private static final String NAMESPACE = "http://tempuri.org/";
//    ngrok http 55685 --host-header="localhost:55685"
    private static final String URL = "https://2dea-2001-ee0-51b8-8f60-e51b-e85b-a750-c2b1.ngrok-free.app/Service1.svc";

    private static final String HELLO_METHOD_NAME = "hello";
    private static final String HELLO_SOAP_ACTION = "http://tempuri.org/IService1/hello";
    private static final String GET_BANNERS_METHOD_NAME = "GetBanners";
    private static final String GET_BANNERS_SOAP_ACTION = "http://tempuri.org/IService1/GetBanners";

    private static final String GET_ItemsPopular_METHOD_NAME = "GetItemsPopulars";
    private static final String GET_ItemsPopular_SOAP_ACTION = "http://tempuri.org/IService1/GetItemsPopulars";

    public String callHelloService() {
        try {
            SoapObject request = new SoapObject(NAMESPACE, HELLO_METHOD_NAME);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.setOutputSoapObject(request);

            HttpTransportSE transport = new HttpTransportSE(URL);
            transport.call(HELLO_SOAP_ACTION, envelope);

            Object response = envelope.getResponse();
            if (response instanceof SoapObject) {
                SoapObject soapResponse = (SoapObject) response;
                return soapResponse.getProperty(0).toString();
            } else if (response instanceof SoapPrimitive) {
                SoapPrimitive soapResponse = (SoapPrimitive) response;
                return soapResponse.toString();
            } else {
                return "Unexpected response type: " + response.getClass().getName();
            }
        } catch (Exception e) {
            Log.e("SoapClient", "Error: " + e.getMessage(), e);
            return null;
        }
    }

    public List<Banner> callGetBannersService() {
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

    public List<ItemsPopular> getItemsPopular() {
        List<ItemsPopular> itemsPopulars = new ArrayList<>();

        try {
            SoapObject request = new SoapObject(NAMESPACE, GET_ItemsPopular_METHOD_NAME);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.setOutputSoapObject(request);

            HttpTransportSE transport = new HttpTransportSE(URL);
            transport.call(GET_ItemsPopular_SOAP_ACTION, envelope);



            Object objectResponse = envelope.bodyIn;
            if (objectResponse instanceof SoapFault) {
                SoapFault fault = (SoapFault) objectResponse;
                Log.e("SoapClient", "SOAP Fault: " + fault.getMessage());
                return itemsPopulars;
            }

            SoapObject response = (SoapObject) envelope.bodyIn;
            Log.d("SOAP Response", response.toString());

            SoapObject getResultItemsPopular = (SoapObject) response.getProperty("GetItemsPopularsResult");

            for (int i = 0; i < getResultItemsPopular.getPropertyCount(); i++) {
                SoapObject itemsPopularObject = (SoapObject) getResultItemsPopular.getProperty(i);
                SoapObject idObject = (SoapObject) itemsPopularObject.getProperty("_id");
                String _id = idObject.getProperty("_a").toString() + idObject.getProperty("_b").toString() + idObject.getProperty("_c").toString();
                String des = itemsPopularObject.getProperty("des").toString();
                String description = itemsPopularObject.getProperty("description").toString();
                String id = itemsPopularObject.getProperty("id").toString();
                double oldPrice = Double.parseDouble(itemsPopularObject.getProperty("oldPrice").toString());

                ArrayList<String> picUrl = new ArrayList<>();
                SoapObject picUrlObject = (SoapObject) itemsPopularObject.getProperty("picUrl");
                for (int j = 0; j < picUrlObject.getPropertyCount(); j++) {
                    picUrl.add(picUrlObject.getProperty(j).toString());
                }

                int price = Integer.parseInt(itemsPopularObject.getProperty("price").toString());
                double rating = Double.parseDouble(itemsPopularObject.getProperty("rating").toString());
                int review = Integer.parseInt(itemsPopularObject.getProperty("review").toString());
                String title = itemsPopularObject.getProperty("title").toString();

                ItemsPopular itemsPopular = new ItemsPopular(_id, id, description, oldPrice, picUrl, des, price, rating, review, title);

                itemsPopulars.add(itemsPopular);
            }
        }catch (SoapFault fault) {
            Log.e("SoapClient", "SOAP Fault: " + fault.getMessage(), fault);
        } catch (Exception e) {
            Log.e("SoapClient", "Error: " + e.getMessage(), e);
        }



        return itemsPopulars;
    }






}
