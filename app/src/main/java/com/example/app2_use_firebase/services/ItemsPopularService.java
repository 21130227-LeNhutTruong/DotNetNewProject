package com.example.app2_use_firebase.services;

import android.util.Log;

import com.example.app2_use_firebase.model.ItemsPopular;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;

public class ItemsPopularService {
    private static ItemsPopularService instance;
    private static final String GET_ItemsPopular_METHOD_NAME = "GetAllItemsPopulars";
    private static final String GetItemsPopularsById_METHOD_NAME = "GetItemsPopularById";
    private static final String GET_ItemsPopular_SOAP_ACTION = "http://tempuri.org/IService1/"+GET_ItemsPopular_METHOD_NAME;
    private static final String GetItemsPopularsById_SOAP_ACTION = "http://tempuri.org/IService1/"+GetItemsPopularsById_METHOD_NAME;

    public static ItemsPopularService getInstance() {
        if (instance == null) instance = new ItemsPopularService();
        return instance;
    }

    public List<ItemsPopular> getItemsPopular(String NAMESPACE, String URL) {
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

            SoapObject getResultItemsPopular = (SoapObject) response.getProperty("GetAllItemsPopularsResult");

            for (int i = 0; i < getResultItemsPopular.getPropertyCount(); i++) {
                SoapObject itemsPopularObject = (SoapObject) getResultItemsPopular.getProperty(i);
                SoapObject idObject = (SoapObject) itemsPopularObject.getProperty("_id");
                String _id = idObject.getProperty("_a").toString()+"*"+idObject.getProperty("_b").toString()+"*"+idObject.getProperty("_c").toString();
                String des = itemsPopularObject.getProperty("des").toString();
                String description = itemsPopularObject.getProperty("description").toString();
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

                ItemsPopular itemsPopular = new ItemsPopular(_id, description, oldPrice, picUrl, des, price, rating, review, title);

                itemsPopulars.add(itemsPopular);
            }
        }catch (SoapFault fault) {
            Log.e("SoapClient", "SOAP Fault: " + fault.getMessage(), fault);
        } catch (Exception e) {
            Log.e("SoapClient", "Error: " + e.getMessage(), e);
        }

        return itemsPopulars;
    }

    public ItemsPopular getItemsPopularsById(String NAMESPACE, String URL, String id) {
        try {
            SoapObject request = new SoapObject(NAMESPACE, GetItemsPopularsById_METHOD_NAME);
            request.addProperty("id", id);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.setOutputSoapObject(request);

            envelope.implicitTypes = true;
            envelope.dotNet = true;

            HttpTransportSE transport = new HttpTransportSE(URL);
            transport.call(GetItemsPopularsById_SOAP_ACTION, envelope);



            Object objectResponse = envelope.bodyIn;
            if (objectResponse instanceof SoapFault) {
                SoapFault fault = (SoapFault) objectResponse;
                Log.e("SoapClient", "SOAP Fault: " + fault.getMessage());
                return null;
            }

            SoapObject response = (SoapObject) envelope.bodyIn;
            Log.d("SOAP Response", response.toString());

            SoapObject getItemsPopularByIdResult = (SoapObject) response.getProperty("GetItemsPopularByIdResult");

            if (getItemsPopularByIdResult == null) {
                Log.e("SoapClient", "CheckLoginResult is null");
                return null;
            }


            SoapObject idObject = (SoapObject) getItemsPopularByIdResult.getProperty("_id");

            String _id = idObject.getPrimitivePropertyAsString("_a") +"*"+ idObject.getPrimitivePropertyAsString("_b") +"*" + idObject.getPrimitivePropertyAsString("_c");

            String des = getItemsPopularByIdResult.getProperty("des").toString();
            String description = getItemsPopularByIdResult.getProperty("description").toString();
            double oldPrice = Double.parseDouble(getItemsPopularByIdResult.getProperty("oldPrice").toString());

            ArrayList<String> picUrl = new ArrayList<>();
            SoapObject picUrlObject = (SoapObject) getItemsPopularByIdResult.getProperty("picUrl");
            for (int j = 0; j < picUrlObject.getPropertyCount(); j++) {
                picUrl.add(picUrlObject.getProperty(j).toString());
            }

            int price = Integer.parseInt(getItemsPopularByIdResult.getProperty("price").toString());
            double rating = Double.parseDouble(getItemsPopularByIdResult.getProperty("rating").toString());
            int review = Integer.parseInt(getItemsPopularByIdResult.getProperty("review").toString());
            String title = getItemsPopularByIdResult.getProperty("title").toString();

            ItemsPopular itemsPopular = new ItemsPopular(_id, description, oldPrice, picUrl, des, price, rating, review, title);

            return itemsPopular;

        }catch (SoapFault fault) {
            Log.e("SoapClient", "SOAP Fault: " + fault.getMessage(), fault);
            return null;
        } catch (Exception e) {
            Log.e("SoapClient", "Error: " + e.getMessage(), e);
            return null;
        }
    }
}
