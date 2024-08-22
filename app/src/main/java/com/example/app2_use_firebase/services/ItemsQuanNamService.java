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

public class ItemsQuanNamService {
    private static ItemsQuanNamService instance;
    private static final String GET_ItemsQuanNam_METHOD_NAME = "GetItemsQuanNam";
    private static final String GetItemsQuanById_METHOD_NAME = "GetItemsQuanNamById";
    private static final String GET_ItemsQuanNam_SOAP_ACTION = "http://tempuri.org/IService1/GetItemsQuanNam";
    private static final String GetItemsQuanNamById_SOAP_ACTION = "http://tempuri.org/IService1/"+GetItemsQuanById_METHOD_NAME;

    public static ItemsQuanNamService getInstance() {
        if (instance == null) instance = new ItemsQuanNamService();
        return instance;
    }

    public List<ItemsDomain> getItemsQuanNam(String NAMESPACE, String URL) {

        List<ItemsDomain> ItemsQuanNam = new ArrayList<>();

        try {
            SoapObject request = new SoapObject(NAMESPACE, GET_ItemsQuanNam_METHOD_NAME);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.setOutputSoapObject(request);

            HttpTransportSE transport = new HttpTransportSE(URL);
            transport.call(GET_ItemsQuanNam_SOAP_ACTION, envelope);



            Object objectResponse = envelope.bodyIn;
            if (objectResponse instanceof SoapFault) {
                SoapFault fault = (SoapFault) objectResponse;
                Log.e("SoapClient", "SOAP Fault: " + fault.getMessage());
                return ItemsQuanNam;
            }

            SoapObject response = (SoapObject) envelope.bodyIn;
            Log.d("SOAP Response", response.toString());

            SoapObject getResultItemsQuanNam = (SoapObject) response.getProperty("GetItemsQuanNamResult");

            for (int i = 0; i < getResultItemsQuanNam.getPropertyCount(); i++) {
                SoapObject ItemsQuanNamObject = (SoapObject) getResultItemsQuanNam.getProperty(i);
                SoapObject idObject = (SoapObject) ItemsQuanNamObject.getProperty("_id");
                String _id = idObject.getProperty("_a").toString()+"*" + idObject.getProperty("_b").toString() +"*"+ idObject.getProperty("_c").toString();
                String des = ItemsQuanNamObject.getProperty("des").toString();
                String description = ItemsQuanNamObject.getProperty("description").toString();
                double oldPrice = Double.parseDouble(ItemsQuanNamObject.getProperty("oldPrice").toString());
                ArrayList<String> picUrl = new ArrayList<>();
                SoapObject picUrlObject = (SoapObject) ItemsQuanNamObject.getProperty("picUrl");
                for (int j = 0; j < picUrlObject.getPropertyCount(); j++) {
                    picUrl.add(picUrlObject.getProperty(j).toString());
                }
                int price = Integer.parseInt(ItemsQuanNamObject.getProperty("price").toString());
                double rating = Double.parseDouble(ItemsQuanNamObject.getProperty("rating").toString());
                int review = Integer.parseInt(ItemsQuanNamObject.getProperty("review").toString());
                String title = ItemsQuanNamObject.getProperty("title").toString();

                ItemsDomain ItemQuanNam = new ItemsDomain(_id, title, description, picUrl, des, price, oldPrice, review, rating);

                ItemsQuanNam.add(ItemQuanNam);
            }
        }catch (SoapFault fault) {
            Log.e("SoapClient", "SOAP Fault: " + fault.getMessage(), fault);
        } catch (Exception e) {
            Log.e("SoapClient", "Error: " + e.getMessage(), e);
        }
        return ItemsQuanNam;
    }

    public ItemsDomain getItemsQuanNamById(String NAMESPACE, String URL, String id) {
        try {
            SoapObject request = new SoapObject(NAMESPACE, GetItemsQuanById_METHOD_NAME);
            request.addProperty("id", id);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.setOutputSoapObject(request);

            envelope.implicitTypes = true;
            envelope.dotNet = true;

            HttpTransportSE transport = new HttpTransportSE(URL);
            transport.call(GetItemsQuanNamById_SOAP_ACTION, envelope);



            Object objectResponse = envelope.bodyIn;
            if (objectResponse instanceof SoapFault) {
                SoapFault fault = (SoapFault) objectResponse;
                Log.e("SoapClient", "SOAP Fault: " + fault.getMessage());
                return null;
            }

            SoapObject response = (SoapObject) envelope.bodyIn;
            Log.d("SOAP Response", response.toString());

            SoapObject getItemsQuanNamByIdResult = (SoapObject) response.getProperty("GetItemsQuanNamByIdResult");

            if (getItemsQuanNamByIdResult == null) {
                Log.e("SoapClient", "QUnaNam is null");
                return null;
            }


            SoapObject idObject = (SoapObject) getItemsQuanNamByIdResult.getProperty("_id");

            String _id = idObject.getPrimitivePropertyAsString("_a") +"*"+ idObject.getPrimitivePropertyAsString("_b") +"*" + idObject.getPrimitivePropertyAsString("_c");

            String des = getItemsQuanNamByIdResult.getProperty("des").toString();
            String description = getItemsQuanNamByIdResult.getProperty("description").toString();
            double oldPrice = Double.parseDouble(getItemsQuanNamByIdResult.getProperty("oldPrice").toString());

            ArrayList<String> picUrl = new ArrayList<>();
            SoapObject picUrlObject = (SoapObject) getItemsQuanNamByIdResult.getProperty("picUrl");
            for (int j = 0; j < picUrlObject.getPropertyCount(); j++) {
                picUrl.add(picUrlObject.getProperty(j).toString());
            }

            int price = Integer.parseInt(getItemsQuanNamByIdResult.getProperty("price").toString());
            double rating = Double.parseDouble(getItemsQuanNamByIdResult.getProperty("rating").toString());
            int review = Integer.parseInt(getItemsQuanNamByIdResult.getProperty("review").toString());
            String title = getItemsQuanNamByIdResult.getProperty("title").toString();

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
