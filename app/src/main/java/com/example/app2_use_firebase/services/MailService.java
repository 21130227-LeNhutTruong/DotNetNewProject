package com.example.app2_use_firebase.services;

import android.util.Log;

import com.example.app2_use_firebase.model.User;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class MailService {
    private static MailService instance;
    private static final String GET_MailConfirm_METHOD_NAME = "SendEmail";
    private static final String GET_MailConfirm_SOAP_ACTION = "http://tempuri.org/IService1/SendEmail";
    public static MailService getInstance() {
        if (instance == null) instance = new MailService();
        return instance;
    }


    public boolean sendMail(String NAMESPACE, String URL, String mailUser, String subject, String body) {
        try {
            SoapObject request = new SoapObject(NAMESPACE, GET_MailConfirm_METHOD_NAME);
            request.addProperty("to", mailUser);
            request.addProperty("subject", subject);
            request.addProperty("body", body);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.setOutputSoapObject(request);

            envelope.implicitTypes = true;
            envelope.dotNet = true;

            HttpTransportSE transport = new HttpTransportSE(URL);
            transport.call(GET_MailConfirm_SOAP_ACTION, envelope);
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
