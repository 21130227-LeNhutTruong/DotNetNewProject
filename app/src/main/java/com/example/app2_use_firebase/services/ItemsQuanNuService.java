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

public class ItemsQuanNuService {
    private static ItemsQuanNuService instance;
    private static final String GET_ItemsQuanNu_METHOD_NAME = "GetItemsQuanNu";
    private static final String GET_ItemsQuanNu_SOAP_ACTION = "http://tempuri.org/IService1/GetItemsQuanNu";
    private static final String GetItemsQuanNuById_SOAP_ACTION = "http://tempuri.org/IService1/"+GET_ItemsQuanNu_METHOD_NAME;

    public static ItemsQuanNuService getInstance() {
        if (instance == null) instance = new ItemsQuanNuService();
        return instance;
    }

    public List<ItemsDomain> getItemsQuanNu(String NAMESPACE, String URL) {

        List<ItemsDomain> ItemsQuanNu = new ArrayList<>();

        try {
            SoapObject request = new SoapObject(NAMESPACE, GET_ItemsQuanNu_METHOD_NAME);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.setOutputSoapObject(request);

            HttpTransportSE transport = new HttpTransportSE(URL);
            transport.call(GET_ItemsQuanNu_SOAP_ACTION, envelope);



            Object objectResponse = envelope.bodyIn;
            if (objectResponse instanceof SoapFault) {
                SoapFault fault = (SoapFault) objectResponse;
                Log.e("SoapClient", "SOAP Fault: " + fault.getMessage());
                return ItemsQuanNu;
            }

            SoapObject response = (SoapObject) envelope.bodyIn;
            Log.d("SOAP Response", response.toString());

            SoapObject getResultItemsQuanNu = (SoapObject) response.getProperty("GetItemsQuanNuResult");

            for (int i = 0; i < getResultItemsQuanNu.getPropertyCount(); i++) {
                SoapObject ItemsQuanNuObject = (SoapObject) getResultItemsQuanNu.getProperty(i);
                SoapObject idObject = (SoapObject) ItemsQuanNuObject.getProperty("_id");
                String _id = idObject.getProperty("_a").toString() + idObject.getProperty("_b").toString() + idObject.getProperty("_c").toString();
                String des = ItemsQuanNuObject.getProperty("des").toString();
                String description = ItemsQuanNuObject.getProperty("description").toString();
                double oldPrice = Double.parseDouble(ItemsQuanNuObject.getProperty("oldPrice").toString());
                ArrayList<String> picUrl = new ArrayList<>();
                SoapObject picUrlObject = (SoapObject) ItemsQuanNuObject.getProperty("picUrl");
                for (int j = 0; j < picUrlObject.getPropertyCount(); j++) {
                    picUrl.add(picUrlObject.getProperty(j).toString());
                }
                int price = Integer.parseInt(ItemsQuanNuObject.getProperty("price").toString());
                double rating = Double.parseDouble(ItemsQuanNuObject.getProperty("rating").toString());
                int review = Integer.parseInt(ItemsQuanNuObject.getProperty("review").toString());
                String title = ItemsQuanNuObject.getProperty("title").toString();

                ItemsDomain ItemQuanNu = new ItemsDomain(_id, title, description, picUrl, des, price, oldPrice, review, rating);

                ItemsQuanNu.add(ItemQuanNu);
            }
        }catch (SoapFault fault) {
            Log.e("SoapClient", "SOAP Fault: " + fault.getMessage(), fault);
        } catch (Exception e) {
            Log.e("SoapClient", "Error: " + e.getMessage(), e);
        }
        return ItemsQuanNu;
    }
    public ItemsDomain getItemsQuanNuById(String NAMESPACE, String URL, String id) {
        try {
            SoapObject request = new SoapObject(NAMESPACE, GET_ItemsQuanNu_METHOD_NAME);
            request.addProperty("id", id);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.setOutputSoapObject(request);

            envelope.implicitTypes = true;
            envelope.dotNet = true;

            HttpTransportSE transport = new HttpTransportSE(URL);
            transport.call(GetItemsQuanNuById_SOAP_ACTION, envelope);



            Object objectResponse = envelope.bodyIn;
            if (objectResponse instanceof SoapFault) {
                SoapFault fault = (SoapFault) objectResponse;
                Log.e("SoapClient", "SOAP Fault: " + fault.getMessage());
                return null;
            }

            SoapObject response = (SoapObject) envelope.bodyIn;
            Log.d("SOAP Response", response.toString());

            SoapObject getItemsQuanNuByIdResult = (SoapObject) response.getProperty("GetItemsQuanNuByIdResult");

            if (getItemsQuanNuByIdResult == null) {
                Log.e("SoapClient", "CheckLoginResult is null");
                return null;
            }


            SoapObject idObject = (SoapObject) getItemsQuanNuByIdResult.getProperty("_id");

            String _id = idObject.getPrimitivePropertyAsString("_a") +"*"+ idObject.getPrimitivePropertyAsString("_b") +"*" + idObject.getPrimitivePropertyAsString("_c");

            String des = getItemsQuanNuByIdResult.getProperty("des").toString();
            String description = getItemsQuanNuByIdResult.getProperty("description").toString();
            double oldPrice = Double.parseDouble(getItemsQuanNuByIdResult.getProperty("oldPrice").toString());

            ArrayList<String> picUrl = new ArrayList<>();
            SoapObject picUrlObject = (SoapObject) getItemsQuanNuByIdResult.getProperty("picUrl");
            for (int j = 0; j < picUrlObject.getPropertyCount(); j++) {
                picUrl.add(picUrlObject.getProperty(j).toString());
            }

            int price = Integer.parseInt(getItemsQuanNuByIdResult.getProperty("price").toString());
            double rating = Double.parseDouble(getItemsQuanNuByIdResult.getProperty("rating").toString());
            int review = Integer.parseInt(getItemsQuanNuByIdResult.getProperty("review").toString());
            String title = getItemsQuanNuByIdResult.getProperty("title").toString();

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
