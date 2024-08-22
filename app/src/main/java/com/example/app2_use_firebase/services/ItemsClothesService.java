package com.example.app2_use_firebase.services;

import android.util.Log;

import com.example.app2_use_firebase.Domain.ItemsDomain;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;

public class ItemsClothesService {
    private static ItemsClothesService instance;
    private static final String GET_ItemsClothes_METHOD_NAME = "GetAllItemsClothes";
    private static final String GetItemsClothesById_METHOD_NAME = "GetItemsClothesById";

    private static final String GET_ItemsClothes_SOAP_ACTION = "http://tempuri.org/IService1/"+GET_ItemsClothes_METHOD_NAME;
    private static final String GetItemsClothesById_SOAP_ACTION = "http://tempuri.org/IService1/"+GetItemsClothesById_METHOD_NAME;

    public static ItemsClothesService getInstance() {
        if (instance == null) instance = new ItemsClothesService();
        return instance;
    }

    public List<ItemsDomain> getItemsClothes(String NAMESPACE, String URL) {
        List<ItemsDomain> itemsClothess = new ArrayList<>();

        try {
            SoapObject request = new SoapObject(NAMESPACE, GET_ItemsClothes_METHOD_NAME);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.setOutputSoapObject(request);

            HttpTransportSE transport = new HttpTransportSE(URL);
            transport.call(GET_ItemsClothes_SOAP_ACTION, envelope);



            Object objectResponse = envelope.bodyIn;
            if (objectResponse instanceof SoapFault) {
                SoapFault fault = (SoapFault) objectResponse;
                Log.e("SoapClient", "SOAP Fault: " + fault.getMessage());
                return itemsClothess;
            }

            SoapObject response = (SoapObject) envelope.bodyIn;
            Log.d("SOAP Response", response.toString());

            SoapObject getResultItemsClothes = (SoapObject) response.getProperty("GetAllItemsClothesResult");

            for (int i = 0; i < getResultItemsClothes.getPropertyCount(); i++) {
                SoapObject itemsClothesObject = (SoapObject) getResultItemsClothes.getProperty(i);
                SoapObject idObject = (SoapObject) itemsClothesObject.getProperty("_id");
                String _id = idObject.getProperty("_a").toString()+"*" + idObject.getProperty("_b").toString()+"*" + idObject.getProperty("_c").toString();
                String des = itemsClothesObject.getProperty("des").toString();
                String description = itemsClothesObject.getProperty("description").toString();
                double oldPrice = Double.parseDouble(itemsClothesObject.getProperty("oldPrice").toString());

                ArrayList<String> picUrl = new ArrayList<>();
                SoapObject picUrlObject = (SoapObject) itemsClothesObject.getProperty("picUrl");
                for (int j = 0; j < picUrlObject.getPropertyCount(); j++) {
                    picUrl.add(picUrlObject.getProperty(j).toString());
                }

                int price = Integer.parseInt(itemsClothesObject.getProperty("price").toString());
                double rating = Double.parseDouble(itemsClothesObject.getProperty("rating").toString());
                int review = Integer.parseInt(itemsClothesObject.getProperty("review").toString());
                String title = itemsClothesObject.getProperty("title").toString();

//                ItemsPopular itemsClothes = new ItemsPopular(_id, description, oldPrice, picUrl, des, price, rating, review, title);
                ItemsDomain itemsDomain = new ItemsDomain(_id, title, description, picUrl, des, price, oldPrice, review, rating);

                itemsClothess.add(itemsDomain);

            }
        }catch (SoapFault fault) {
            Log.e("SoapClient", "SOAP Fault: " + fault.getMessage(), fault);
        } catch (Exception e) {
            Log.e("SoapClient", "Error: " + e.getMessage(), e);
        }
        return itemsClothess;

    }
    public ItemsDomain getItemsClothesById(String NAMESPACE, String URL, String id) {
        try {
            SoapObject request = new SoapObject(NAMESPACE, GetItemsClothesById_METHOD_NAME);
            request.addProperty("id", id);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.setOutputSoapObject(request);

            envelope.implicitTypes = true;
            envelope.dotNet = true;

            HttpTransportSE transport = new HttpTransportSE(URL);
            transport.call(GetItemsClothesById_SOAP_ACTION, envelope);



            Object objectResponse = envelope.bodyIn;
            if (objectResponse instanceof SoapFault) {
                SoapFault fault = (SoapFault) objectResponse;
                Log.e("SoapClient", "SOAP Fault: " + fault.getMessage());
                return null;
            }

            SoapObject response = (SoapObject) envelope.bodyIn;
            Log.d("SOAP Response", response.toString());

            SoapObject getItemsClothesByIdResult = (SoapObject) response.getProperty("GetItemsClothesByIdResult");

            if (getItemsClothesByIdResult == null) {
                Log.e("SoapClient", "CheckLoginResult is null");
                return null;
            }


            SoapObject idObject = (SoapObject) getItemsClothesByIdResult.getProperty("_id");

            String _id = idObject.getPrimitivePropertyAsString("_a") +"*"+ idObject.getPrimitivePropertyAsString("_b") +"*" + idObject.getPrimitivePropertyAsString("_c");

            String des = getItemsClothesByIdResult.getProperty("des").toString();
            String description = getItemsClothesByIdResult.getProperty("description").toString();
            double oldPrice = Double.parseDouble(getItemsClothesByIdResult.getProperty("oldPrice").toString());

            ArrayList<String> picUrl = new ArrayList<>();
            SoapObject picUrlObject = (SoapObject) getItemsClothesByIdResult.getProperty("picUrl");
            for (int j = 0; j < picUrlObject.getPropertyCount(); j++) {
                picUrl.add(picUrlObject.getProperty(j).toString());
            }

            int price = Integer.parseInt(getItemsClothesByIdResult.getProperty("price").toString());
            double rating = Double.parseDouble(getItemsClothesByIdResult.getProperty("rating").toString());
            int review = Integer.parseInt(getItemsClothesByIdResult.getProperty("review").toString());
            String title = getItemsClothesByIdResult.getProperty("title").toString();

//            ItemsDomain itemsPopular = new ItemsDomain(_id, description, oldPrice, picUrl, des, price, rating, review, title);
            ItemsDomain itemsDomain = new ItemsDomain(_id, title, description, picUrl, des, price, oldPrice, review, rating);

            return itemsDomain;

        }catch (SoapFault fault) {
            Log.e("SoapClient", "SOAP Fault: " + fault.getMessage(), fault);
            return null;
        } catch (Exception e) {
            Log.e("SoapClient", "Error: " + e.getMessage(), e);
            return null;
        }
    }

}
