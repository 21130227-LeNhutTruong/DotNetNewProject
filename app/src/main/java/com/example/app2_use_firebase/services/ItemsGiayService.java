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

public class ItemsGiayService {
        private static com.example.app2_use_firebase.services.ItemsGiayService instance;
        private static final String GET_ItemsGiay_METHOD_NAME = "GetAllItemsGiay";
    private static final String GetItemsGiayById_METHOD_NAME = "GetItemsGiayById";

    private static final String GET_ItemsGiay_SOAP_ACTION = "http://tempuri.org/IService1/"+GET_ItemsGiay_METHOD_NAME;
    private static final String GetItemsGiayById_SOAP_ACTION = "http://tempuri.org/IService1/"+GetItemsGiayById_METHOD_NAME;

        public static com.example.app2_use_firebase.services.ItemsGiayService getInstance() {
            if (instance == null) instance = new com.example.app2_use_firebase.services.ItemsGiayService();
            return instance;
        }

        public List<ItemsDomain> getItemsGiayService(String NAMESPACE, String URL) {
            List<ItemsDomain> itemsGiay = new ArrayList<>();

            try {
                SoapObject request = new SoapObject(NAMESPACE, GET_ItemsGiay_METHOD_NAME);

                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.setOutputSoapObject(request);

                HttpTransportSE transport = new HttpTransportSE(URL);
                transport.call(GET_ItemsGiay_SOAP_ACTION, envelope);



                Object objectResponse = envelope.bodyIn;
                if (objectResponse instanceof SoapFault) {
                    SoapFault fault = (SoapFault) objectResponse;
                    Log.e("SoapClient", "SOAP Fault: " + fault.getMessage());
                    return itemsGiay;
                }

                SoapObject response = (SoapObject) envelope.bodyIn;
                Log.d("SOAP Response", response.toString());

                SoapObject getResultItemsGiay = (SoapObject) response.getProperty("GetAllItemsGiayResult");

                for (int i = 0; i < getResultItemsGiay.getPropertyCount(); i++) {
                    SoapObject itemsGiayObject = (SoapObject) getResultItemsGiay.getProperty(i);
                    SoapObject idObject = (SoapObject) itemsGiayObject.getProperty("_id");
                    String _id = idObject.getProperty("_a").toString()+"*" + idObject.getProperty("_b").toString()+"*" + idObject.getProperty("_c").toString();
                    String des = itemsGiayObject.getProperty("des").toString();
                    String description = itemsGiayObject.getProperty("description").toString();
                    double oldPrice = Double.parseDouble(itemsGiayObject.getProperty("oldPrice").toString());

                    ArrayList<String> picUrl = new ArrayList<>();
                    SoapObject picUrlObject = (SoapObject) itemsGiayObject.getProperty("picUrl");
                    for (int j = 0; j < picUrlObject.getPropertyCount(); j++) {
                        picUrl.add(picUrlObject.getProperty(j).toString());
                    }

                    int price = Integer.parseInt(itemsGiayObject.getProperty("price").toString());
                    double rating = Double.parseDouble(itemsGiayObject.getProperty("rating").toString());
                    int review = Integer.parseInt(itemsGiayObject.getProperty("review").toString());
                    String title = itemsGiayObject.getProperty("title").toString();

//                    ItemsPopular itemsGiays = new ItemsPopular(_id, description, oldPrice, picUrl, des, price, rating, review, title);
                    ItemsDomain itemsDomain = new ItemsDomain(_id, title, description, picUrl, des, price, oldPrice, review, rating);

                    itemsGiay.add(itemsDomain);
                }
            }catch (SoapFault fault) {
                Log.e("SoapClient", "SOAP Fault: " + fault.getMessage(), fault);
            } catch (Exception e) {
                Log.e("SoapClient", "Error: " + e.getMessage(), e);
            }



            return itemsGiay;
        }
    public ItemsDomain getItemsGiayById(String NAMESPACE, String URL, String id) {
        try {
            SoapObject request = new SoapObject(NAMESPACE, GetItemsGiayById_METHOD_NAME);
            request.addProperty("id", id);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.setOutputSoapObject(request);

            envelope.implicitTypes = true;
            envelope.dotNet = true;

            HttpTransportSE transport = new HttpTransportSE(URL);
            transport.call(GetItemsGiayById_SOAP_ACTION, envelope);



            Object objectResponse = envelope.bodyIn;
            if (objectResponse instanceof SoapFault) {
                SoapFault fault = (SoapFault) objectResponse;
                Log.e("SoapClient", "SOAP Fault: " + fault.getMessage());
                return null;
            }

            SoapObject response = (SoapObject) envelope.bodyIn;
            Log.d("SOAP Response", response.toString());

            SoapObject getItemsGiayByIdResult = (SoapObject) response.getProperty("GetItemsGiayByIdResult");

            if (getItemsGiayByIdResult == null) {
                Log.e("SoapClient", "Giay is null");
                return null;
            }


            SoapObject idObject = (SoapObject) getItemsGiayByIdResult.getProperty("_id");

            String _id = idObject.getPrimitivePropertyAsString("_a") +"*"+ idObject.getPrimitivePropertyAsString("_b") +"*" + idObject.getPrimitivePropertyAsString("_c");

            String des = getItemsGiayByIdResult.getProperty("des").toString();
            String description = getItemsGiayByIdResult.getProperty("description").toString();
            double oldPrice = Double.parseDouble(getItemsGiayByIdResult.getProperty("oldPrice").toString());

            ArrayList<String> picUrl = new ArrayList<>();
            SoapObject picUrlObject = (SoapObject) getItemsGiayByIdResult.getProperty("picUrl");
            for (int j = 0; j < picUrlObject.getPropertyCount(); j++) {
                picUrl.add(picUrlObject.getProperty(j).toString());
            }

            int price = Integer.parseInt(getItemsGiayByIdResult.getProperty("price").toString());
            double rating = Double.parseDouble(getItemsGiayByIdResult.getProperty("rating").toString());
            int review = Integer.parseInt(getItemsGiayByIdResult.getProperty("review").toString());
            String title = getItemsGiayByIdResult.getProperty("title").toString();

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
