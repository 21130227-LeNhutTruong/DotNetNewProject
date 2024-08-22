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

public class ItemsQuanService {
    private static ItemsQuanService instance;
    private static final String GET_ItemsQuan_METHOD_NAME = "GetItemsQuan";
    private static final String GetItemsQuanById_METHOD_NAME = "GetItemsQuanById";
    private static final String GET_ItemsQuan_SOAP_ACTION = "http://tempuri.org/IService1/GetItemsQuan";

    private static final String GetItemsQuanById_SOAP_ACTION = "http://tempuri.org/IService1/"+GetItemsQuanById_METHOD_NAME;

    public static ItemsQuanService getInstance() {
        if (instance == null) instance = new ItemsQuanService();
        return instance;
    }

    public List<ItemsDomain> getItemsQuan(String NAMESPACE, String URL) {

        List<ItemsDomain> ItemsQuan = new ArrayList<>();

        try {
            SoapObject request = new SoapObject(NAMESPACE, GET_ItemsQuan_METHOD_NAME);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.setOutputSoapObject(request);

            HttpTransportSE transport = new HttpTransportSE(URL);
            transport.call(GET_ItemsQuan_SOAP_ACTION, envelope);



            Object objectResponse = envelope.bodyIn;
            if (objectResponse instanceof SoapFault) {
                SoapFault fault = (SoapFault) objectResponse;
                Log.e("SoapClient", "SOAP Fault: " + fault.getMessage());
                return ItemsQuan;
            }

            SoapObject response = (SoapObject) envelope.bodyIn;
            Log.d("SOAP Response", response.toString());

            SoapObject getResultItemsQuan = (SoapObject) response.getProperty("GetItemsQuanResult");

            for (int i = 0; i < getResultItemsQuan.getPropertyCount(); i++) {
                SoapObject ItemsQuanObject = (SoapObject) getResultItemsQuan.getProperty(i);
                SoapObject idObject = (SoapObject) ItemsQuanObject.getProperty("_id");
                String _id = idObject.getProperty("_a").toString()+"*" + idObject.getProperty("_b").toString() +"*"+ idObject.getProperty("_c").toString();
                String des = ItemsQuanObject.getProperty("des").toString();
                String description = ItemsQuanObject.getProperty("description").toString();
                double oldPrice = Double.parseDouble(ItemsQuanObject.getProperty("oldPrice").toString());
                ArrayList<String> picUrl = new ArrayList<>();
                SoapObject picUrlObject = (SoapObject) ItemsQuanObject.getProperty("picUrl");
                for (int j = 0; j < picUrlObject.getPropertyCount(); j++) {
                    picUrl.add(picUrlObject.getProperty(j).toString());
                }
                int price = Integer.parseInt(ItemsQuanObject.getProperty("price").toString());
                double rating = Double.parseDouble(ItemsQuanObject.getProperty("rating").toString());
                int review = Integer.parseInt(ItemsQuanObject.getProperty("review").toString());
                String title = ItemsQuanObject.getProperty("title").toString();

                ItemsDomain ItemQuan = new ItemsDomain(_id, title, description, picUrl, des, price, oldPrice, review, rating);

                ItemsQuan.add(ItemQuan);
            }
        }catch (SoapFault fault) {
            Log.e("SoapClient", "SOAP Fault: " + fault.getMessage(), fault);
        } catch (Exception e) {
            Log.e("SoapClient", "Error: " + e.getMessage(), e);
        }
        return ItemsQuan;
    }

    public ItemsDomain getItemsQuanById(String NAMESPACE, String URL, String id) {
        try {
            SoapObject request = new SoapObject(NAMESPACE, GetItemsQuanById_METHOD_NAME);
            request.addProperty("id", id);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.setOutputSoapObject(request);

            envelope.implicitTypes = true;
            envelope.dotNet = true;

            HttpTransportSE transport = new HttpTransportSE(URL);
            transport.call(GetItemsQuanById_SOAP_ACTION, envelope);



            Object objectResponse = envelope.bodyIn;
            if (objectResponse instanceof SoapFault) {
                SoapFault fault = (SoapFault) objectResponse;
                Log.e("SoapClient", "SOAP Fault: " + fault.getMessage());
                return null;
            }

            SoapObject response = (SoapObject) envelope.bodyIn;
            Log.d("SOAP Response", response.toString());

            SoapObject getItemsQuanByIdResult = (SoapObject) response.getProperty("GetItemsQuanByIdResult");

            if (getItemsQuanByIdResult == null) {
                Log.e("SoapClient", "Quan is null");
                return null;
            }


            SoapObject idObject = (SoapObject) getItemsQuanByIdResult.getProperty("_id");

            String _id = idObject.getPrimitivePropertyAsString("_a") +"*"+ idObject.getPrimitivePropertyAsString("_b") +"*" + idObject.getPrimitivePropertyAsString("_c");

            String des = getItemsQuanByIdResult.getProperty("des").toString();
            String description = getItemsQuanByIdResult.getProperty("description").toString();
            double oldPrice = Double.parseDouble(getItemsQuanByIdResult.getProperty("oldPrice").toString());

            ArrayList<String> picUrl = new ArrayList<>();
            SoapObject picUrlObject = (SoapObject) getItemsQuanByIdResult.getProperty("picUrl");
            for (int j = 0; j < picUrlObject.getPropertyCount(); j++) {
                picUrl.add(picUrlObject.getProperty(j).toString());
            }

            int price = Integer.parseInt(getItemsQuanByIdResult.getProperty("price").toString());
            double rating = Double.parseDouble(getItemsQuanByIdResult.getProperty("rating").toString());
            int review = Integer.parseInt(getItemsQuanByIdResult.getProperty("review").toString());
            String title = getItemsQuanByIdResult.getProperty("title").toString();

            ItemsDomain itemsQuan = new ItemsDomain(_id, title, description, picUrl, des, price,oldPrice, review, rating);

            return itemsQuan;

        }catch (SoapFault fault) {
            Log.e("SoapClient", "SOAP Fault: " + fault.getMessage(), fault);
            return null;
        } catch (Exception e) {
            Log.e("SoapClient", "Error: " + e.getMessage(), e);
            return null;
        }
    }
}
