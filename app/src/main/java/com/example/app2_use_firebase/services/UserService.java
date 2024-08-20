package com.example.app2_use_firebase.services;

import android.util.Log;

import com.example.app2_use_firebase.model.User;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class UserService {
    private static UserService instance;
    private static final String AddUser_METHOD_NAME = "AddUser";
    private static final String Register_METHOD_NAME = "Register";
    private static final String CheckLogin_METHOD_NAME = "CheckLogin";
    private static final String IsExistUser_METHOD_NAME = "isExistUser";
    private static final String GetUserById_METHOD_NAME = "GetUserById";
    private static final String AddUser_SOAP_ACTION = "http://tempuri.org/IService1/"+AddUser_METHOD_NAME;
    private static final String Register_SOAP_ACTION = "http://tempuri.org/IService1/"+Register_METHOD_NAME;
    private static final String CheckLogin_SOAP_ACTION = "http://tempuri.org/IService1/"+CheckLogin_METHOD_NAME;
    private static final String IsExistUser_SOAP_ACTION = "http://tempuri.org/IService1/"+IsExistUser_METHOD_NAME;
    private static final String GetUserById_SOAP_ACTION = "http://tempuri.org/IService1/"+GetUserById_METHOD_NAME;

    public static UserService getInstance() {
        if (instance == null) instance = new UserService();
        return instance;
    }



    public boolean addUser(String NAMESPACE, String URL, User user) {
        try {
            SoapObject request = new SoapObject(NAMESPACE, AddUser_METHOD_NAME);

            SoapObject userObject = new SoapObject(NAMESPACE, "user");

            userObject.addProperty("age", user.getAge());
            userObject.addProperty("avatar", user.getAvatar());
            userObject.addProperty("email", user.getEmail());
            userObject.addProperty("name", user.getName());
            userObject.addProperty("pass", user.getPass());
            userObject.addProperty("role", user.getRole());
            userObject.addProperty("sex", user.getSex());
            userObject.addProperty("username", user.getUsername());

            request.addProperty("user", userObject);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.setOutputSoapObject(request);

            envelope.implicitTypes = true;
            envelope.dotNet = true;

            HttpTransportSE transport = new HttpTransportSE(URL);
            transport.call(AddUser_SOAP_ACTION, envelope);

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


    public boolean register(String NAMESPACE, String URL, User user) {
        try {
            SoapObject request = new SoapObject(NAMESPACE, Register_METHOD_NAME);

            SoapObject userObject = new SoapObject(NAMESPACE, "user");

            userObject.addProperty("age", user.getAge());
            userObject.addProperty("avatar", user.getAvatar());
            userObject.addProperty("email", user.getEmail());
            userObject.addProperty("name", user.getName());
            userObject.addProperty("pass", user.getPass());
            userObject.addProperty("role", user.getRole());
            userObject.addProperty("sex", user.getSex());
            userObject.addProperty("username", user.getUsername());

            request.addProperty("user", userObject);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.setOutputSoapObject(request);

            envelope.implicitTypes = true;
            envelope.dotNet = true;

            HttpTransportSE transport = new HttpTransportSE(URL);
            transport.call(Register_SOAP_ACTION, envelope);

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

    public User checkLogin(String NAMESPACE, String URL, String emailSend, String passSend) {
        try {
            SoapObject request = new SoapObject(NAMESPACE, CheckLogin_METHOD_NAME);
            request.addProperty("email", emailSend);
            request.addProperty("password", passSend);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.setOutputSoapObject(request);

            envelope.implicitTypes = true;
            envelope.dotNet = true;

            HttpTransportSE transport = new HttpTransportSE(URL);
            transport.call(CheckLogin_SOAP_ACTION, envelope);



            Object objectResponse = envelope.bodyIn;
            if (objectResponse instanceof SoapFault) {
                SoapFault fault = (SoapFault) objectResponse;
                Log.e("SoapClient", "SOAP Fault: " + fault.getMessage());
                return null;
            }

            SoapObject response = (SoapObject) envelope.bodyIn;
            Log.d("SOAP Response", response.toString());

            SoapObject checkLoginResult = (SoapObject) response.getProperty("CheckLoginResult");

            if (checkLoginResult == null) {
                Log.e("SoapClient", "CheckLoginResult is null");
                return null;
            }


            SoapObject idObject = (SoapObject) checkLoginResult.getProperty("_id");

            String _id = idObject.getProperty("_a").toString()+"*"+idObject.getProperty("_b").toString() +"*"+ idObject.getProperty("_c").toString();
            int age = Integer.parseInt(checkLoginResult.getProperty("age").toString());
            String avatar = checkLoginResult.getProperty("avatar").toString();
            String email = checkLoginResult.getProperty("email").toString();
            String name = checkLoginResult.getProperty("name").toString();
            String username = checkLoginResult.getProperty("username").toString();
            String pass = checkLoginResult.getProperty("pass").toString();
            int role = Integer.parseInt(checkLoginResult.getProperty("role").toString());
            int sex = Integer.parseInt(checkLoginResult.getProperty("sex").toString());

            User user = new User(_id, email, pass, username, name, age, avatar, sex, role);

            return user;

        }catch (SoapFault fault) {
            Log.e("SoapClient", "SOAP Fault: " + fault.getMessage(), fault);
            return null;
        } catch (Exception e) {
            Log.e("SoapClient", "Error: " + e.getMessage(), e);
            return null;
        }
    }

    public boolean isExistUser(String NAMESPACE, String URL, String email) {
        try {
            SoapObject request = new SoapObject(NAMESPACE, IsExistUser_METHOD_NAME);
            request.addProperty("email", email);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.setOutputSoapObject(request);

            envelope.implicitTypes = true;
            envelope.dotNet = true;

            HttpTransportSE transport = new HttpTransportSE(URL);
            transport.call(IsExistUser_SOAP_ACTION, envelope);

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

    public User getUserById(String NAMESPACE, String URL, String userId) {
        try {
            SoapObject request = new SoapObject(NAMESPACE, GetUserById_METHOD_NAME);
            request.addProperty("id", userId);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.setOutputSoapObject(request);

            envelope.implicitTypes = true;
            envelope.dotNet = true;

            HttpTransportSE transport = new HttpTransportSE(URL);
            transport.call(GetUserById_SOAP_ACTION, envelope);



            Object objectResponse = envelope.bodyIn;
            if (objectResponse instanceof SoapFault) {
                SoapFault fault = (SoapFault) objectResponse;
                Log.e("SoapClient", "SOAP Fault: " + fault.getMessage());
                return null;
            }

            SoapObject response = (SoapObject) envelope.bodyIn;
            Log.d("SOAP Response", response.toString());

            SoapObject getUserByIdResult = (SoapObject) response.getProperty("GetUserByIdResult");

            if (getUserByIdResult == null) {
                Log.e("SoapClient", "GetUserByIdResult is null");
                return null;
            }


            SoapObject idObject = (SoapObject) getUserByIdResult.getProperty("_id");

            String _id = idObject.getProperty("_a").toString()+"*"+idObject.getProperty("_b").toString()+"*"+idObject.getProperty("_c").toString();
            int age = Integer.parseInt(getUserByIdResult.getProperty("age").toString());
            String avatar = getUserByIdResult.getProperty("avatar").toString();
            String email = getUserByIdResult.getProperty("email").toString();
            String name = getUserByIdResult.getProperty("name").toString();
            String username = getUserByIdResult.getProperty("username").toString();
            String pass = getUserByIdResult.getProperty("pass").toString();
            int role = Integer.parseInt(getUserByIdResult.getProperty("role").toString());
            int sex = Integer.parseInt(getUserByIdResult.getProperty("sex").toString());

            User user = new User(_id, email, pass, username, name, age, avatar, sex, role);

            return user;

        }catch (SoapFault fault) {
            Log.e("SoapClient", "SOAP Fault: " + fault.getMessage(), fault);
            return null;
        } catch (Exception e) {
            Log.e("SoapClient", "Error: " + e.getMessage(), e);
            return null;
        }
    }
}
