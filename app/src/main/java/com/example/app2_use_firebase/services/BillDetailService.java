package com.example.app2_use_firebase.services;

import android.util.Log;

import com.example.app2_use_firebase.model.BillDetail;
import com.example.app2_use_firebase.model.ProductBuy;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;

public class BillDetailService {
    private static  BillDetailService instance;

    private static final String GetBillDetail_METHOD_NAME = "GetBillDetail";

    private static final String GetBillDetail_SOAP_ACTION = "http://tempuri.org/IService1/"+GetBillDetail_METHOD_NAME;


    public static BillDetailService getInstance() {
        if (instance == null) instance = new BillDetailService();
        return instance;
    }

    public BillDetail getBillDetail(String NAMESPACE, String URL, String idUser) {
        try {
            SoapObject request = new SoapObject(NAMESPACE, GetBillDetail_METHOD_NAME);
            request.addProperty("idUser", idUser);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.setOutputSoapObject(request);

            envelope.implicitTypes = true;
            envelope.dotNet = true;

            HttpTransportSE transport = new HttpTransportSE(URL);
            transport.call(GetBillDetail_SOAP_ACTION, envelope);



            Object objectResponse = envelope.bodyIn;
            if (objectResponse instanceof SoapFault) {
                SoapFault fault = (SoapFault) objectResponse;
                Log.e("SoapClient", "SOAP Fault: " + fault.getMessage());
                return null;
            }

            SoapObject response = (SoapObject) envelope.bodyIn;
            Log.d("SOAP Response", response.toString());

            SoapObject getBillDetailResult = (SoapObject) response.getProperty("GetBillDetailResult");

            if (getBillDetailResult == null) {
                Log.e("SoapClient", "CheckLoginResult is null");
                return null;
            }


            SoapObject idObject = (SoapObject) getBillDetailResult.getProperty("_id");

            String _id = idObject.getPrimitivePropertyAsString("_a") +"*"+ idObject.getPrimitivePropertyAsString("_b") +"*" + idObject.getPrimitivePropertyAsString("_c");

            SoapObject productsObject = (SoapObject) getBillDetailResult.getProperty("products");

            List<ProductBuy> products = new ArrayList<>();
            for (int i = 0; i < productsObject.getPropertyCount(); i++) {
                SoapObject productItemsObject = (SoapObject) productsObject.getProperty(i);
                SoapObject productIdObject = (SoapObject) productItemsObject.getProperty("_id");
                String _idProduct = productIdObject.getPrimitivePropertyAsString("_a") +"*"+
                        productIdObject.getPrimitivePropertyAsString("_b") +"*"+
                        productIdObject.getPrimitivePropertyAsString("_c");
                int quantity = Integer.parseInt(productItemsObject.getProperty("quantity").toString());
                String type = productItemsObject.getProperty("type").toString();
                ProductBuy productBuy = new ProductBuy(_idProduct, quantity, type);
                products.add(productBuy);
            }

            BillDetail billDetail = new BillDetail(_id, idUser, products);


            return billDetail;

        }catch (SoapFault fault) {
            Log.e("SoapClient", "SOAP Fault: " + fault.getMessage(), fault);
            return null;
        } catch (Exception e) {
            Log.e("SoapClient", "Error: " + e.getMessage(), e);
            return null;
        }
    }


}
