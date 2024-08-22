package com.example.app2_use_firebase.services;

import android.util.Log;

import com.example.app2_use_firebase.model.Bill;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class BillService {
    private static BillService instance;

    private static final String GetBillByUser_METHOD_NAME = "GetBillByUser";
    private static final String GetAllBill_METHOD_NAME = "GetAllBill";
    private static final String AddBill_METHOD_NAME = "AddBill";
    private static final String UpdateStatusBill_METHOD_NAME = "UpdateStatusBill";
    private static final String GetBillByUser_SOAP_ACTION = "http://tempuri.org/IService1/"+GetBillByUser_METHOD_NAME;
    private static final String GetAllBill_SOAP_ACTION = "http://tempuri.org/IService1/"+GetAllBill_METHOD_NAME;
    private static final String AddBill_SOAP_ACTION = "http://tempuri.org/IService1/"+AddBill_METHOD_NAME;
    private static final String UpdateStatusBill_SOAP_ACTION = "http://tempuri.org/IService1/"+UpdateStatusBill_METHOD_NAME;

    public static BillService getInstance() {
        if (instance == null) instance = new BillService();
        return instance;
    }

    public List<Bill> getBillByUser(String NAMESPACE, String URL, String userId) {
        try {
            SoapObject request = new SoapObject(NAMESPACE, GetBillByUser_METHOD_NAME);
            request.addProperty("idUser", userId);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.setOutputSoapObject(request);

            envelope.implicitTypes = true;
            envelope.dotNet = true;

            HttpTransportSE transport = new HttpTransportSE(URL);
            transport.call(GetBillByUser_SOAP_ACTION, envelope);



            Object objectResponse = envelope.bodyIn;
            if (objectResponse instanceof SoapFault) {
                SoapFault fault = (SoapFault) objectResponse;
                Log.e("SoapClient", "SOAP Fault: " + fault.getMessage());
                return null;
            }

            SoapObject response = (SoapObject) envelope.bodyIn;
            Log.d("SOAP Response", response.toString());

            SoapObject getBillByUserResult = (SoapObject) response.getProperty("GetBillByUserResult");

            if (getBillByUserResult == null) {
                Log.e("SoapClient", "BillUser is null");
                return null;
            }

            List<Bill> bills = new ArrayList<>();
            for (int i = 0; i < getBillByUserResult.getPropertyCount(); i++) {
                SoapObject billObject = (SoapObject) getBillByUserResult.getProperty(i);
                SoapObject idObject = (SoapObject) billObject.getProperty("_id");
                String _id = idObject.getPrimitivePropertyAsString("_a") +"*"+ idObject.getPrimitivePropertyAsString("_b") +"*" + idObject.getPrimitivePropertyAsString("_c");

                String address = billObject.getProperty("address").toString();
                String dateString = billObject.getProperty("date").toString();
                LocalDateTime localDateTime = LocalDateTime.parse(dateString, DateTimeFormatter.ISO_DATE_TIME);
                String fullName = billObject.getProperty("fullName").toString();
                String payment = billObject.getProperty("payment").toString();
                String phone = billObject.getProperty("phone").toString();
                String status = billObject.getProperty("status").toString();
                int totalAmount = Integer.parseInt(billObject.getProperty("totalAmount").toString());

                String date = localDateTime.getYear()+"/"+localDateTime.getMonthValue()+"/"+localDateTime.getDayOfMonth()+" "+localDateTime.getHour()+":"+localDateTime.getMinute()+":"+localDateTime.getSecond();

                bills.add(new Bill(_id, date, address, fullName, payment, status, phone, totalAmount, userId));
            }


            return bills;

        }catch (SoapFault fault) {
            Log.e("SoapClient", "SOAP Fault: " + fault.getMessage(), fault);
            return null;
        } catch (Exception e) {
            Log.e("SoapClient", "Error: " + e.getMessage(), e);
            return null;
        }
    }
    public List<Bill> getAllBills(String NAMESPACE, String URL) {
        try {
            SoapObject request = new SoapObject(NAMESPACE, GetAllBill_METHOD_NAME);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.setOutputSoapObject(request);

            envelope.implicitTypes = true;
            envelope.dotNet = true;

            HttpTransportSE transport = new HttpTransportSE(URL);
            transport.call(GetAllBill_SOAP_ACTION, envelope);



            Object objectResponse = envelope.bodyIn;
            if (objectResponse instanceof SoapFault) {
                SoapFault fault = (SoapFault) objectResponse;
                Log.e("SoapClient", "SOAP Fault: " + fault.getMessage());
                return null;
            }

            SoapObject response = (SoapObject) envelope.bodyIn;
            Log.d("SOAP Response", response.toString());

            SoapObject getBillResult = (SoapObject) response.getProperty("GetAllBillResult");

            if (getBillResult == null) {
                Log.e("SoapClient", "BillAll is null");
                return null;
            }

            List<Bill> bills = new ArrayList<>();
            for (int i = 0; i < getBillResult.getPropertyCount(); i++) {
                SoapObject billObject = (SoapObject) getBillResult.getProperty(i);
                SoapObject idObject = (SoapObject) billObject.getProperty("_id");
                String _id = idObject.getPrimitivePropertyAsString("_a") +"*"+ idObject.getPrimitivePropertyAsString("_b") +"*" + idObject.getPrimitivePropertyAsString("_c");

                String address = billObject.getProperty("address").toString();
                String dateString = billObject.getProperty("date").toString();
                LocalDateTime localDateTime = LocalDateTime.parse(dateString, DateTimeFormatter.ISO_DATE_TIME);
                String fullName = billObject.getProperty("fullName").toString();
                String payment = billObject.getProperty("payment").toString();
                String phone = billObject.getProperty("phone").toString();
                String status = billObject.getProperty("status").toString();
                int totalAmount = Integer.parseInt(billObject.getProperty("totalAmount").toString());

                String date = localDateTime.getYear()+"/"+localDateTime.getMonthValue()+"/"+localDateTime.getDayOfMonth()+" "+localDateTime.getHour()+":"+localDateTime.getMinute()+":"+localDateTime.getSecond();

                SoapObject idUserObject = (SoapObject) billObject.getProperty("userId");
                String idUser = idUserObject.getPrimitivePropertyAsString("_a") +"*"+ idObject.getPrimitivePropertyAsString("_b") +"*" + idObject.getPrimitivePropertyAsString("_c");


                bills.add(new Bill(_id, date, address, fullName, payment, status, phone, totalAmount, idUser));
            }


            return bills;

        }catch (SoapFault fault) {
            Log.e("SoapClient", "SOAP Fault: " + fault.getMessage(), fault);
            return null;
        } catch (Exception e) {
            Log.e("SoapClient", "Error: " + e.getMessage(), e);
            return null;
        }
    }

    public boolean addBill(String NAMESPACE, String URL, Bill bill) {
        try {
            SoapObject request = new SoapObject(NAMESPACE, AddBill_METHOD_NAME);
            SoapObject billObject = (SoapObject) new SoapObject(NAMESPACE, "bill");

            request.addProperty("address", bill.getAddress());
            request.addProperty("fullName", bill.getFullName());
            request.addProperty("payment", bill.getPayment());
            request.addProperty("phone", bill.getPhone());
            request.addProperty("totalAmount", bill.getTotalAmount());
            request.addProperty("idUser", bill.getUserId());

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.setOutputSoapObject(request);

            envelope.implicitTypes = true;
            envelope.dotNet = true;

            HttpTransportSE transport = new HttpTransportSE(URL);
            transport.call(AddBill_SOAP_ACTION, envelope);

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

    public boolean updateStatusBill(String NAMESPACE, String URL, String id, String status) {
        try {
            SoapObject request = new SoapObject(NAMESPACE, UpdateStatusBill_METHOD_NAME);
            SoapObject billObject = (SoapObject) new SoapObject(NAMESPACE, "bill");

            request.addProperty("id", id);
            request.addProperty("status", status);


            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.setOutputSoapObject(request);

            envelope.implicitTypes = true;
            envelope.dotNet = true;

            HttpTransportSE transport = new HttpTransportSE(URL);
            transport.call(UpdateStatusBill_SOAP_ACTION, envelope);

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
