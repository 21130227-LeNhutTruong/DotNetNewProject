package com.example.app2_use_firebase.services;

import com.example.app2_use_firebase.Domain.ItemsDomain;

import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;
public class ItemsAoService {
    private static ItemsAoService instance;
    private static final String GET_ITEMS_AO_METHOD_NAME = "GetAllItemsAos";
    private static final String GET_ITEMS_AO_SOAP_ACTION = "http://tempuri.org/IService1/" + GET_ITEMS_AO_METHOD_NAME;

    public static ItemsAoService getInstance() {
        if (instance == null) instance = new ItemsAoService();
        return instance;
    }

    public List<ItemsDomain> getItemsAos(String NAMESPACE, String URL) {
        List<ItemsDomain> itemsAoList = new ArrayList<>();

        try {
            SoapObject request = new SoapObject(NAMESPACE, GET_ITEMS_AO_METHOD_NAME);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.setOutputSoapObject(request);

            HttpTransportSE transport = new HttpTransportSE(URL);
            transport.call(GET_ITEMS_AO_SOAP_ACTION, envelope);

            Object objectResponse = envelope.bodyIn;
            if (objectResponse instanceof SoapFault) {
                SoapFault fault = (SoapFault) objectResponse;
                Log.e("SoapClient", "SOAP Fault: " + fault.getMessage());
                return itemsAoList;
            }

            SoapObject response = (SoapObject) envelope.bodyIn;
            Log.d("SOAP Response", response.toString());

            SoapObject getResultItemsAo = (SoapObject) response.getProperty("GetAllItemsAosResult");

            for (int i = 0; i < getResultItemsAo.getPropertyCount(); i++) {
                SoapObject itemsAoObject = (SoapObject) getResultItemsAo.getProperty(i);

                SoapObject idObject = (SoapObject) itemsAoObject.getProperty("_id");
                String _id = idObject.getProperty("_a").toString() + idObject.getProperty("_b").toString() + idObject.getProperty("_c").toString();
                String des = itemsAoObject.getProperty("des").toString();
                String description = itemsAoObject.getProperty("description").toString();
                double oldPrice = Double.parseDouble(itemsAoObject.getProperty("oldPrice").toString());

                ArrayList<String> picUrl = new ArrayList<>();
                SoapObject picUrlObject = (SoapObject) itemsAoObject.getProperty("picUrl");
                for (int j = 0; j < picUrlObject.getPropertyCount(); j++) {
                    picUrl.add(picUrlObject.getProperty(j).toString());
                }

                int price = Integer.parseInt(itemsAoObject.getProperty("price").toString());
                double rating = Double.parseDouble(itemsAoObject.getProperty("rating").toString());
                int review = Integer.parseInt(itemsAoObject.getProperty("review").toString());
                String title = itemsAoObject.getProperty("title").toString();

                ItemsDomain itemsAo = new ItemsDomain(_id, title, description, picUrl, des, price, oldPrice, review, rating); // Đảm bảo đúng số lượng và thứ tự các tham số
                itemsAoList.add(itemsAo);
            }
        }
        catch (SoapFault fault) {
            Log.e("SoapClient", "SOAP Fault: " + fault.getMessage(), fault);
        }catch (Exception e) {
            Log.e("SoapClient", "Error: " + e.getMessage(), e);
        }

        return itemsAoList;
    }
}
