package com.example.app2_use_firebase.services;

import android.util.Log;

import com.example.app2_use_firebase.Domain.ItemsDomain;
import com.example.app2_use_firebase.Domain.SliderItems;
import com.example.app2_use_firebase.model.ItemsPopular;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;

public class ItemsQuanService {
    private static ItemsQuanService instance;
    private static final String GET_ItemsQuan_METHOD_NAME = "GetSliderItems";
    private static final String GET_ItemsQuan_SOAP_ACTION = "http://tempuri.org/IService1/GetSliderItems";

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
                String _id = idObject.getProperty("_a").toString() + idObject.getProperty("_b").toString() + idObject.getProperty("_c").toString();
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
}
