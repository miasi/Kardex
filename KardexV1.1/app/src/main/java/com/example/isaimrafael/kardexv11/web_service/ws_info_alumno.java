package com.example.isaimrafael.kardexv11.web_service;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

/**
 * Created by IsaimRafael on 26/11/2015.
 */
public class ws_info_alumno {
    public final String SOAP_ACTION = "http://siia.itlp.edu.mx/consultarAlumno";
    public final String OPERATION_NAME = "consultarAlumno";
    public final String WSDL_TARGET_NAMESPACE = "http://siia.itlp.edu.mx/";
    public final String SOAP_ADRESS = "http://siia.itlp.edu.mx/WebServiceITLP.asmx?WSDL";

    public ws_info_alumno() {
    }

    public String Cargador(String control, String passWS){
        SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,OPERATION_NAME);
        PropertyInfo pi = new PropertyInfo();
        pi.setName("numeroDeControl");
        pi.setValue(control);
        pi.setType(String.class);
        request.addProperty(pi);
        pi = new PropertyInfo();
        pi.setName("contrasena");
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
            response = e.toString();
        }
        return response.toString();
    }
}
