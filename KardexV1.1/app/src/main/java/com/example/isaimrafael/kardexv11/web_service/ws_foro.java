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
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class ws_foro {

    private final String SOAP_ACTION = "http://siia.itlp.edu.mx/consultarForo";
    private final String OPERATION_NAME = "consultarForo";
    private final String SOAP_ACTION_INS = "http://siia.itlp.edu.mx/insertarForoGrupo";
    private final String OPERATION_NAME_INS = "insertarForoGrupo";
    private final String WSDL_TARGET_NAMESPACE = "http://siia.itlp.edu.mx/";
    private final String SOAP_ADRESS = "http://siia.itlp.edu.mx/WebServiceITLP.asmx?WSDL";

    public ws_foro() {
    }

    public List<ws_foro_mensaje> consultarMensaje(String plan, String anio, String periodo, String materia, String grupo, String contrasena) {
        List<ws_foro_mensaje> mensajes = new ArrayList<ws_foro_mensaje>();
        try {
            String xml = getXML(plan, anio, periodo, materia, grupo, contrasena);

            Document doc = readXML(xml);

            NodeList lwsForo = doc.getElementsByTagName("wsForo");
            for (int i = 0; i < lwsForo.getLength(); i++) {
                Element wsForo = (Element) lwsForo.item(i);

                ws_foro_mensaje mensaje = new ws_foro_mensaje();
                mensaje.setFolio(wsForo.getElementsByTagName("folio").item(0).getTextContent());
                mensaje.setControl(wsForo.getElementsByTagName("control").item(0).getTextContent());
                mensaje.setNombre(wsForo.getElementsByTagName("nombre").item(0).getTextContent());
                mensaje.setMensaje(wsForo.getElementsByTagName("mensaje").item(0).getTextContent());
                mensaje.setFecha(wsForo.getElementsByTagName("fecha").item(0).getTextContent());

                mensajes.add(mensaje);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mensajes;
    }

    public String crearMensaje(String plan, String anio, String periodo, String materia, String grupo, String control, String mensaje, String contrasena) {
        String result = null;
        try {
            String xml = sendMsg(plan, anio, periodo, materia, grupo, control, mensaje, contrasena);

            Document doc = readXML(xml);

            result = doc.getElementsByTagName("insertarForoGrupoResult").item(0).getTextContent();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public String getXML(String plan, String anio, String periodo, String materia, String grupo, String contrasena) {
        SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE, OPERATION_NAME);

        PropertyInfo pi = new PropertyInfo();
        pi.setName("plan");
        pi.setValue(plan);
        pi.setType(String.class);
        request.addProperty(pi);

        pi = new PropertyInfo();
        pi.setName("anio");
        pi.setValue(anio);
        pi.setType(String.class);
        request.addProperty(pi);

        pi = new PropertyInfo();
        pi.setName("periodo");
        pi.setValue(periodo);
        pi.setType(String.class);
        request.addProperty(pi);

        pi = new PropertyInfo();
        pi.setName("materia");
        pi.setValue(materia);
        pi.setType(String.class);
        request.addProperty(pi);

        pi = new PropertyInfo();
        pi.setName("grupo");
        pi.setValue(grupo);
        pi.setType(String.class);
        request.addProperty(pi);

        pi = new PropertyInfo();
        pi.setName("contrasena");
        pi.setValue(contrasena);
        pi.setType(String.class);
        request.addProperty(pi);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.implicitTypes = true;
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

    private String sendMsg(String plan, String anio, String periodo, String materia, String grupo, String control, String mensaje, String contrasena) {
        SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE, OPERATION_NAME_INS);

        PropertyInfo pi = new PropertyInfo();
        pi.setName("plan");
        pi.setValue(plan);
        pi.setType(String.class);
        request.addProperty(pi);

        pi = new PropertyInfo();
        pi.setName("anio");
        pi.setValue(anio);
        pi.setType(String.class);
        request.addProperty(pi);

        pi = new PropertyInfo();
        pi.setName("periodo");
        pi.setValue(periodo);
        pi.setType(String.class);
        request.addProperty(pi);

        pi = new PropertyInfo();
        pi.setName("materia");
        pi.setValue(materia);
        pi.setType(String.class);
        request.addProperty(pi);

        pi = new PropertyInfo();
        pi.setName("grupo");
        pi.setValue(grupo);
        pi.setType(String.class);
        request.addProperty(pi);

        pi = new PropertyInfo();
        pi.setName("control");
        pi.setValue(control);
        pi.setType(String.class);
        request.addProperty(pi);

        pi = new PropertyInfo();
        pi.setName("mensaje");
        pi.setValue(mensaje);
        pi.setType(String.class);
        request.addProperty(pi);

        pi = new PropertyInfo();
        pi.setName("contrasena");
        pi.setValue(contrasena);
        pi.setType(String.class);
        request.addProperty(pi);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.implicitTypes = true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE httpTransportSE = new HttpTransportSE(SOAP_ADRESS);
        httpTransportSE.debug = true;
        String xml = null;
        try {
            httpTransportSE.call(SOAP_ACTION_INS, envelope);
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
