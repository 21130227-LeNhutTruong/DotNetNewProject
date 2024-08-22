
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

public class GetAllItemsService {
    private static GetAllItemsService instance;
    private static final String GET_AllItems_METHOD_NAME = "GetAllItems";
    private static final String GET_AllItems_SOAP_ACTION = "http://tempuri.org/IService1/"+GET_AllItems_METHOD_NAME;
    private static final String METHOD_NAME_SEARCH="SearchItems";
    private static final String   SOAP_ACTION_SEARCH="http://tempuri.org/IService1/"+METHOD_NAME_SEARCH;
    public static GetAllItemsService getInstance() {
        if (instance == null) instance = new GetAllItemsService();
        return instance;
    }

    public List<ItemsPopular> getAllItems(String NAMESPACE, String URL) {
        List<ItemsPopular> AllItemss = new ArrayList<>();

        try {
            SoapObject request = new SoapObject(NAMESPACE, GET_AllItems_METHOD_NAME);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.setOutputSoapObject(request);

            HttpTransportSE transport = new HttpTransportSE(URL);
            transport.call(GET_AllItems_SOAP_ACTION, envelope);



            Object objectResponse = envelope.bodyIn;
            if (objectResponse instanceof SoapFault) {
                SoapFault fault = (SoapFault) objectResponse;
                Log.e("SoapClient", "SOAP Fault: " + fault.getMessage());
                return AllItemss;
            }

            SoapObject response = (SoapObject) envelope.bodyIn;
            Log.d("SOAP Response", response.toString());

            SoapObject getResultAllItems = (SoapObject) response.getProperty("GetAllItemsResult");

            for (int i = 0; i < getResultAllItems.getPropertyCount(); i++) {
                SoapObject AllItemsObject = (SoapObject) getResultAllItems.getProperty(i);
                SoapObject idObject = (SoapObject) AllItemsObject.getProperty("_id");
                String _id = idObject.getProperty("_a").toString()+"*"+idObject.getProperty("_b").toString()+"*"+idObject.getProperty("_c").toString();
                String des = AllItemsObject.getProperty("des").toString();
                String description = AllItemsObject.getProperty("description").toString();
                double oldPrice = Double.parseDouble(AllItemsObject.getProperty("oldPrice").toString());

                ArrayList<String> picUrl = new ArrayList<>();
                SoapObject picUrlObject = (SoapObject) AllItemsObject.getProperty("picUrl");
                for (int j = 0; j < picUrlObject.getPropertyCount(); j++) {
                    picUrl.add(picUrlObject.getProperty(j).toString());
                }

                int price = Integer.parseInt(AllItemsObject.getProperty("price").toString());
                double rating = Double.parseDouble(AllItemsObject.getProperty("rating").toString());
                int review = Integer.parseInt(AllItemsObject.getProperty("review").toString());
                String title = AllItemsObject.getProperty("title").toString();

                ItemsPopular AllItems = new ItemsPopular(_id, description, oldPrice, picUrl, des, price, rating, review, title);

                AllItemss.add(AllItems);
            }
        }catch (SoapFault fault) {
            Log.e("SoapClient", "SOAP Fault: " + fault.getMessage(), fault);
        } catch (Exception e) {
            Log.e("SoapClient", "Error: " + e.getMessage(), e);
        }

        return AllItemss;
    }
    public List<ItemsPopular> searchItems(String searchQuery,String NAMESPACE, String URL) {
        List<ItemsPopular> filteredItems = new ArrayList<>();
        try {
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME_SEARCH);
            request.addProperty("searchQuery", searchQuery);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.setOutputSoapObject(request);

            HttpTransportSE transport = new HttpTransportSE(URL);
            transport.call(SOAP_ACTION_SEARCH, envelope);

            Object response = envelope.bodyIn;
            if (response instanceof SoapFault) {
                Log.e("SoapClient", "SOAP Fault: " + ((SoapFault) response).getMessage());
                return filteredItems;
            }

            SoapObject soapResponse = (SoapObject) envelope.bodyIn;
            SoapObject result = (SoapObject) soapResponse.getProperty("SearchItemsResult");

            for (int i = 0; i < result.getPropertyCount(); i++) {
                SoapObject AllItemsObject = (SoapObject) result.getProperty(i);
                SoapObject idObject = (SoapObject) AllItemsObject.getProperty("_id");
                String _id = idObject.getProperty("_a").toString()+"*"+idObject.getProperty("_b").toString()+"*"+idObject.getProperty("_c").toString();
                String des = AllItemsObject.getProperty("des").toString();
                String description = AllItemsObject.getProperty("description").toString();
                double oldPrice = Double.parseDouble(AllItemsObject.getProperty("oldPrice").toString());

                ArrayList<String> picUrl = new ArrayList<>();
                SoapObject picUrlObject = (SoapObject) AllItemsObject.getProperty("picUrl");
                for (int j = 0; j < picUrlObject.getPropertyCount(); j++) {
                    picUrl.add(picUrlObject.getProperty(j).toString());
                }

                int price = Integer.parseInt(AllItemsObject.getProperty("price").toString());
                double rating = Double.parseDouble(AllItemsObject.getProperty("rating").toString());
                int review = Integer.parseInt(AllItemsObject.getProperty("review").toString());
                String title = AllItemsObject.getProperty("title").toString();

                ItemsPopular AllItems = new ItemsPopular(_id, description, oldPrice, picUrl, des, price, rating, review, title);

                filteredItems.add(AllItems);
            }

        } catch (SoapFault fault) {
            Log.e("SoapClient", "SOAP Fault: " + fault.getMessage(), fault);
        } catch (Exception e) {
            Log.e("SoapClient", "Error: " + e.getMessage(), e);
        }
        return filteredItems;
    }
}

