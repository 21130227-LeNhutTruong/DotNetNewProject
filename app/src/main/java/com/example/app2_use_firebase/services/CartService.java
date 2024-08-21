package com.example.app2_use_firebase.services;

import android.util.Log;

import com.example.app2_use_firebase.model.Cart;
import com.example.app2_use_firebase.model.ProductBuy;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;

public class CartService {
    private static  CartService instance;
    private static final String GetCartByUser_METHOD_NAME = "GetCartByUser";
    private static final String UpdateCartQuantity_METHOD_NAME = "UpdateCartQuantity";
    private static final String RemoveCart_METHOD_NAME = "RemoveCart";
    private static final String AddCart_METHOD_NAME = "AddCart";
    private static final String GetCartByUser_SOAP_ACTION = "http://tempuri.org/IService1/"+GetCartByUser_METHOD_NAME;
    private static final String UpdateCartQuantity_SOAP_ACTION = "http://tempuri.org/IService1/"+UpdateCartQuantity_METHOD_NAME;
    private static final String RemoveCart_SOAP_ACTION = "http://tempuri.org/IService1/"+RemoveCart_METHOD_NAME;
    private static final String AddCartt_SOAP_ACTION = "http://tempuri.org/IService1/"+AddCart_METHOD_NAME;

    public static CartService getInstance() {
        if (instance == null) instance = new CartService();
        return instance;
    }

    public Cart getCartByUser(String NAMESPACE, String URL, String userId) {
        try {
            SoapObject request = new SoapObject(NAMESPACE, GetCartByUser_METHOD_NAME);
            request.addProperty("id", userId);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.setOutputSoapObject(request);

            envelope.implicitTypes = true;
            envelope.dotNet = true;

            HttpTransportSE transport = new HttpTransportSE(URL);
            transport.call(GetCartByUser_SOAP_ACTION, envelope);



            Object objectResponse = envelope.bodyIn;
            if (objectResponse instanceof SoapFault) {
                SoapFault fault = (SoapFault) objectResponse;
                Log.e("SoapClient", "SOAP Fault: " + fault.getMessage());
                return null;
            }

            SoapObject response = (SoapObject) envelope.bodyIn;
            Log.d("SOAP Response", response.toString());

            SoapObject getCartByUserResult = (SoapObject) response.getProperty("GetCartByUserResult");

            if (getCartByUserResult == null) {
                Log.e("SoapClient", "CheckLoginResult is null");
                return null;
            }


            SoapObject idObject = (SoapObject) getCartByUserResult.getProperty("_id");

            String _id = idObject.getPrimitivePropertyAsString("_a") +"*"+ idObject.getPrimitivePropertyAsString("_b") +"*" + idObject.getPrimitivePropertyAsString("_c");

            SoapObject productsObject = (SoapObject) getCartByUserResult.getProperty("products");

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

            Cart cart = new Cart(_id, userId, products);


            return cart;

        }catch (SoapFault fault) {
            Log.e("SoapClient", "SOAP Fault: " + fault.getMessage(), fault);
            return null;
        } catch (Exception e) {
            Log.e("SoapClient", "Error: " + e.getMessage(), e);
            return null;
        }
    }

    public boolean updateCartQuantity(String NAMESPACE, String URL, String id, String idProduct, int quantity) {
        try {
            SoapObject request = new SoapObject(NAMESPACE, UpdateCartQuantity_METHOD_NAME);
            request.addProperty("id", id);
            request.addProperty("idProduct", idProduct);
            request.addProperty("quantity", quantity);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.setOutputSoapObject(request);

            envelope.implicitTypes = true;
            envelope.dotNet = true;

            HttpTransportSE transport = new HttpTransportSE(URL);
            transport.call(UpdateCartQuantity_SOAP_ACTION, envelope);

            Object response = envelope.getResponse();
            if (response instanceof SoapPrimitive) {
                String responseString = response.toString();
                return Boolean.parseBoolean(responseString);
            } else {
                Log.e("SoapClient", "Unexpected response type: " + response.getClass().getSimpleName());
                return false;
            }


        }catch (SoapFault fault) {
            Log.e("SoapClient", "SOAP Fault: " + fault.getMessage(), fault);
            return false;
        } catch (Exception e) {
            Log.e("SoapClient", "Error: " + e.getMessage(), e);
            return false;
        }
    }

    public boolean removeCart(String NAMESPACE, String URL, String id, String idProduct) {
        try {
            SoapObject request = new SoapObject(NAMESPACE, RemoveCart_METHOD_NAME);
            request.addProperty("id", id);
            request.addProperty("idProduct", idProduct);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.setOutputSoapObject(request);

            envelope.implicitTypes = true;
            envelope.dotNet = true;

            HttpTransportSE transport = new HttpTransportSE(URL);
            transport.call(RemoveCart_SOAP_ACTION, envelope);

            Object response = envelope.getResponse();
            if (response instanceof SoapPrimitive) {
                String responseString = response.toString();
                return Boolean.parseBoolean(responseString);
            } else {
                Log.e("SoapClient", "Unexpected response type: " + response.getClass().getSimpleName());
                return false;
            }


        }catch (SoapFault fault) {
            Log.e("SoapClient", "SOAP Fault: " + fault.getMessage(), fault);
            return false;
        } catch (Exception e) {
            Log.e("SoapClient", "Error: " + e.getMessage(), e);
            return false;
        }
    }

    public boolean addCart(String NAMESPACE, String URL, String id, String idProduct, int quantity, String type) {
        try {
            SoapObject request = new SoapObject(NAMESPACE, AddCart_METHOD_NAME);
            request.addProperty("id", id);
            request.addProperty("idProduct", idProduct);
            request.addProperty("quantity", quantity);
            request.addProperty("type", type);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.setOutputSoapObject(request);

            envelope.implicitTypes = true;
            envelope.dotNet = true;

            HttpTransportSE transport = new HttpTransportSE(URL);
            transport.call(AddCartt_SOAP_ACTION, envelope);

            Object response = envelope.getResponse();
            if (response instanceof SoapPrimitive) {
                String responseString = response.toString();
                return Boolean.parseBoolean(responseString);
            } else {
                Log.e("SoapClient", "Unexpected response type: " + response.getClass().getSimpleName());
                return false;
            }


        }catch (SoapFault fault) {
            Log.e("SoapClient", "SOAP Fault: " + fault.getMessage(), fault);
            return false;
        } catch (Exception e) {
            Log.e("SoapClient", "Error: " + e.getMessage(), e);
            return false;
        }
    }
}
