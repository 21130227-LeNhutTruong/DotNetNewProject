package com.example.app2_use_firebase.services;

import android.util.Log;

import com.example.app2_use_firebase.Domain.SliderItems;
import com.example.app2_use_firebase.model.ItemsPopular;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;

public class SliderItemsService {
    private static SliderItemsService instance;
    private static final String GET_SliderItems_METHOD_NAME = "GetSliderItems";
    private static final String GET_SliderItems_SOAP_ACTION = "http://tempuri.org/IService1/GetSliderItems";

    public static SliderItemsService getInstance() {
        if (instance == null) instance = new SliderItemsService();
        return instance;
    }

    public List<SliderItems> getSliderItems(String NAMESPACE, String URL) {

        List<SliderItems> sliderItems = new ArrayList<>();

        try {
            SoapObject request = new SoapObject(NAMESPACE, GET_SliderItems_METHOD_NAME);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.setOutputSoapObject(request);

            HttpTransportSE transport = new HttpTransportSE(URL);
            transport.call(GET_SliderItems_SOAP_ACTION, envelope);



            Object objectResponse = envelope.bodyIn;
            if (objectResponse instanceof SoapFault) {
                SoapFault fault = (SoapFault) objectResponse;
                Log.e("SoapClient", "SOAP Fault: " + fault.getMessage());
                return sliderItems;
            }

            SoapObject response = (SoapObject) envelope.bodyIn;
            Log.d("SOAP Response", response.toString());

            SoapObject getResultSliderItems = (SoapObject) response.getProperty("GetSliderItemsResult");

            for (int i = 0; i < getResultSliderItems.getPropertyCount(); i++) {
                SoapObject itemsPopularObject = (SoapObject) getResultSliderItems.getProperty(i);
                SoapObject idObject = (SoapObject) itemsPopularObject.getProperty("_id");

                String description = itemsPopularObject.getProperty("description").toString();

                ArrayList<String> picUrl = new ArrayList<>();
                SoapObject picUrlObject = (SoapObject) itemsPopularObject.getProperty("picUrl");
                for (int j = 0; j < picUrlObject.getPropertyCount(); j++) {
                    picUrl.add(picUrlObject.getProperty(j).toString());
                }

                int price = Integer.parseInt(itemsPopularObject.getProperty("price").toString());

                String title = itemsPopularObject.getProperty("title").toString();

                SliderItems slideritem = new SliderItems(description, picUrl,price, title);

                sliderItems.add(slideritem);
            }
        }catch (SoapFault fault) {
            Log.e("SoapClient", "SOAP Fault: " + fault.getMessage(), fault);
        } catch (Exception e) {
            Log.e("SoapClient", "Error: " + e.getMessage(), e);
        }
        return sliderItems;
    }
}
