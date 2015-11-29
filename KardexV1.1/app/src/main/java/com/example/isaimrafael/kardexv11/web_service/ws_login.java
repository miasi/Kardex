package com.example.isaimrafael.kardexv11.web_service;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.net.SocketPermission;

/**
 * Created by IsaimRafael on 26/11/2015.
 */
public class ws_login {
    public final String SOAP_ACTION = "http://siia.itlp.edu.mx/loginAlumno";
    public final String OPERATION_NAME = "loginAlumno";
    public final String WSDL_TARGET_NAMESPACE = "http://siia.itlp.edu.mx/";
    public final String SOAP_ADRESS = "http://siia.itlp.edu.mx/WebServiceITLP.asmx?WSDL";

    public ws_login() {
    }

    public String Cargador(String control, String pass, String passWS){
        SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,OPERATION_NAME);
        PropertyInfo pi = new PropertyInfo();
        pi.setName("control");
        pi.setValue(control);
        pi.setType(String.class);
        request.addProperty(pi);
        pi = new PropertyInfo();
        pi.setName("password");
        pi.setValue(pass);
        pi.setType(String.class);
        request.addProperty(pi);
        pi = new PropertyInfo();
        pi.setName("passwordWebService");
        pi.setValue(passWS);
        pi.setType(String.class);
        request.addProperty(pi);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE httpTransportSE = new HttpTransportSE(SOAP_ADRESS);
        Object response = null;
        try{
            httpTransportSE.call(SOAP_ACTION, envelope);
            response = envelope.getResponse();
        }catch (Exception e){
            response = e.toString();}
        return response.toString();
    }
}
