package com.example.isaimrafael.kardexv11.web_service;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by IsaimRafael on 30/11/2015.
 */
public class ws_horario_maestro {
    public final String SOAP_ACTION = "http://siia.itlp.edu.mx/consultarHorarioDocente";
    public final String OPERATION_NAME = "consultarHorarioDocente";
    public final String WSDL_TARGET_NAMESPACE = "http://siia.itlp.edu.mx/";
    public final String SOAP_ADRESS = "http://siia.itlp.edu.mx/WebServiceITLP.asmx?WSDL";

    public ws_horario_maestro(String control, String passws) {
        try {
            String xml = getXML(control, passws);
            Document doc = readXML(xml);
            NodeList list = doc.getElementsByTagName("consultarHorarioDocente");
        } catch (Exception e) {
        }
    }

    public String getXML(String control, String passws) {
        SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE, OPERATION_NAME);

        PropertyInfo pi = new PropertyInfo();
        pi.setName("nombre");
        pi.setValue(control);
        pi.setType(String.class);
        request.addProperty(pi);
        pi = new PropertyInfo();
        pi.setName("contrasena");
        pi.setValue(passws);
        pi.setType(String.class);
        request.addProperty(pi);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE httpTransportSE = new HttpTransportSE(SOAP_ADRESS);
        httpTransportSE.debug = true;
        String xml = null;
        try {
            httpTransportSE.call(SOAP_ACTION, envelope);
            xml = httpTransportSE.responseDump;
        } catch (Exception e) {
            return e.getMessage();
        }
        return xml;
    }

    private Document readXML(String xml) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        InputSource source = new InputSource(new StringReader(xml));
        return builder.parse(source);
    }
}

