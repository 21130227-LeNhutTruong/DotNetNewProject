package com.example.app2_use_firebase.services;

import android.util.Log;

import com.example.app2_use_firebase.Domain.ItemsDomain;
import com.example.app2_use_firebase.model.ItemsPopular;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;

public class ItemsBagService {
        private static ItemsBagService instance;
        private static final String GET_ItemsBag_METHOD_NAME = "GetAllItemsBag";
    private static final String GetItemsBagById_METHOD_NAME = "GetItemsBagById";

    private static final String GET_ItemsBag_SOAP_ACTION = "http://tempuri.org/IService1/"+GET_ItemsBag_METHOD_NAME;
    private static final String GetItemsBagById_SOAP_ACTION = "http://tempuri.org/IService1/"+GetItemsBagById_METHOD_NAME;

        public static ItemsBagService getInstance() {
            if (instance == null) instance = new ItemsBagService();
            return instance;
        }

        public List<ItemsPopular> getItemsBagService(String NAMESPACE, String URL) {
            List<ItemsPopular> ItemsBag = new ArrayList<>();

            try {
                SoapObject request = new SoapObject(NAMESPACE, GET_ItemsBag_METHOD_NAME);

                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.setOutputSoapObject(request);

                HttpTransportSE transport = new HttpTransportSE(URL);
                transport.call(GET_ItemsBag_SOAP_ACTION, envelope);



                Object objectResponse = envelope.bodyIn;
                if (objectResponse instanceof SoapFault) {
                    SoapFault fault = (SoapFault) objectResponse;
                    Log.e("SoapClient", "SOAP Fault: " + fault.getMessage());
                    return ItemsBag;
                }

                SoapObject response = (SoapObject) envelope.bodyIn;
                Log.d("SOAP Response", response.toString());

                SoapObject getResultItemsBag = (SoapObject) response.getProperty("GetAllItemsBagResult");

                for (int i = 0; i < getResultItemsBag.getPropertyCount(); i++) {
                    SoapObject ItemsBagObject = (SoapObject) getResultItemsBag.getProperty(i);
                    SoapObject idObject = (SoapObject) ItemsBagObject.getProperty("_id");
                    String _id = idObject.getProperty("_a").toString() + idObject.getProperty("_b").toString() + idObject.getProperty("_c").toString();
                    String des = ItemsBagObject.getProperty("des").toString();
                    String description = ItemsBagObject.getProperty("description").toString();
                    double oldPrice = Double.parseDouble(ItemsBagObject.getProperty("oldPrice").toString());

                    ArrayList<String> picUrl = new ArrayList<>();
                    SoapObject picUrlObject = (SoapObject) ItemsBagObject.getProperty("picUrl");
                    for (int j = 0; j < picUrlObject.getPropertyCount(); j++) {
                        picUrl.add(picUrlObject.getProperty(j).toString());
                    }

                    int price = Integer.parseInt(ItemsBagObject.getProperty("price").toString());
                    double rating = Double.parseDouble(ItemsBagObject.getProperty("rating").toString());
                    int review = Integer.parseInt(ItemsBagObject.getProperty("review").toString());
                    String title = ItemsBagObject.getProperty("title").toString();

                    ItemsPopular itemsBags = new ItemsPopular(_id, description, oldPrice, picUrl, des, price, rating, review, title);

                    ItemsBag.add(itemsBags);
                }
            }catch (SoapFault fault) {
                Log.e("SoapClient", "SOAP Fault: " + fault.getMessage(), fault);
            } catch (Exception e) {
                Log.e("SoapClient", "Error: " + e.getMessage(), e);
            }



            return ItemsBag;
        }
    public ItemsDomain getItemsBagById(String NAMESPACE, String URL, String id) {
        try {
            SoapObject request = new SoapObject(NAMESPACE, GetItemsBagById_METHOD_NAME);
            request.addProperty("id", id);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.setOutputSoapObject(request);

            envelope.implicitTypes = true;
            envelope.dotNet = true;

            HttpTransportSE transport = new HttpTransportSE(URL);
            transport.call(GetItemsBagById_SOAP_ACTION, envelope);



            Object objectResponse = envelope.bodyIn;
            if (objectResponse instanceof SoapFault) {
                SoapFault fault = (SoapFault) objectResponse;
                Log.e("SoapClient", "SOAP Fault: " + fault.getMessage());
                return null;
            }

            SoapObject response = (SoapObject) envelope.bodyIn;
            Log.d("SOAP Response", response.toString());

            SoapObject getItemsBagByIdResult = (SoapObject) response.getProperty("GetItemsBagByIdResult");

            if (getItemsBagByIdResult == null) {
                Log.e("SoapClient", "CheckLoginResult is null");
                return null;
            }


            SoapObject idObject = (SoapObject) getItemsBagByIdResult.getProperty("_id");

            String _id = idObject.getPrimitivePropertyAsString("_a") +"*"+ idObject.getPrimitivePropertyAsString("_b") +"*" + idObject.getPrimitivePropertyAsString("_c");

            String des = getItemsBagByIdResult.getProperty("des").toString();
            String description = getItemsBagByIdResult.getProperty("description").toString();
            double oldPrice = Double.parseDouble(getItemsBagByIdResult.getProperty("oldPrice").toString());

            ArrayList<String> picUrl = new ArrayList<>();
            SoapObject picUrlObject = (SoapObject) getItemsBagByIdResult.getProperty("picUrl");
            for (int j = 0; j < picUrlObject.getPropertyCount(); j++) {
                picUrl.add(picUrlObject.getProperty(j).toString());
            }

            int price = Integer.parseInt(getItemsBagByIdResult.getProperty("price").toString());
            double rating = Double.parseDouble(getItemsBagByIdResult.getProperty("rating").toString());
            int review = Integer.parseInt(getItemsBagByIdResult.getProperty("review").toString());
            String title = getItemsBagByIdResult.getProperty("title").toString();

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
