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

public class ItemsTuiXachService {
    private static ItemsTuiXachService instance;
    private static final String GET_ItemsTuiXach_METHOD_NAME = "GetAllItemsTuiXach";
    private static final String GET_ItemsTuiXach_SOAP_ACTION = "http://tempuri.org/IService1/"+GET_ItemsTuiXach_METHOD_NAME;

    public static ItemsTuiXachService getInstance() {
        if (instance == null) instance = new ItemsTuiXachService();
        return instance;
    }

    public List<ItemsPopular> getItemsTuiXach(String NAMESPACE, String URL) {
        List<ItemsPopular> itemsTuiXachs = new ArrayList<>();

        try {
            SoapObject request = new SoapObject(NAMESPACE, GET_ItemsTuiXach_METHOD_NAME);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.setOutputSoapObject(request);

            HttpTransportSE transport = new HttpTransportSE(URL);
            transport.call(GET_ItemsTuiXach_SOAP_ACTION, envelope);



            Object objectResponse = envelope.bodyIn;
            if (objectResponse instanceof SoapFault) {
                SoapFault fault = (SoapFault) objectResponse;
                Log.e("SoapClient", "SOAP Fault: " + fault.getMessage());
                return itemsTuiXachs;
            }

            SoapObject response = (SoapObject) envelope.bodyIn;
            Log.d("SOAP Response", response.toString());

            SoapObject getResultItemsTuiXach = (SoapObject) response.getProperty("GetAllItemsTuiXachResult");

            for (int i = 0; i < getResultItemsTuiXach.getPropertyCount(); i++) {
                SoapObject itemsTuiXachObject = (SoapObject) getResultItemsTuiXach.getProperty(i);
                SoapObject idObject = (SoapObject) itemsTuiXachObject.getProperty("_id");
                String _id = idObject.getProperty("_a").toString() + idObject.getProperty("_b").toString() + idObject.getProperty("_c").toString();
                String des = itemsTuiXachObject.getProperty("des").toString();
                String description = itemsTuiXachObject.getProperty("description").toString();
                double oldPrice = Double.parseDouble(itemsTuiXachObject.getProperty("oldPrice").toString());

                ArrayList<String> picUrl = new ArrayList<>();
                SoapObject picUrlObject = (SoapObject) itemsTuiXachObject.getProperty("picUrl");
                for (int j = 0; j < picUrlObject.getPropertyCount(); j++) {
                    picUrl.add(picUrlObject.getProperty(j).toString());
                }

                int price = Integer.parseInt(itemsTuiXachObject.getProperty("price").toString());
                double rating = Double.parseDouble(itemsTuiXachObject.getProperty("rating").toString());
                int review = Integer.parseInt(itemsTuiXachObject.getProperty("review").toString());
                String title = itemsTuiXachObject.getProperty("title").toString();

                ItemsPopular itemsTuiXach = new ItemsPopular(_id, description, oldPrice, picUrl, des, price, rating, review, title);

                itemsTuiXachs.add(itemsTuiXach);
            }
        }catch (SoapFault fault) {
            Log.e("SoapClient", "SOAP Fault: " + fault.getMessage(), fault);
        } catch (Exception e) {
            Log.e("SoapClient", "Error: " + e.getMessage(), e);
        }



        return itemsTuiXachs;
    }
}
