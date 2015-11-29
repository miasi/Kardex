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

public class ws_alumno {

    public final String SOAP_ACTION = "http://siia.itlp.edu.mx/consultarAlumno";
    public final String OPERATION_NAME = "consultarAlumno";
    public final String WSDL_TARGET_NAMESPACE = "http://siia.itlp.edu.mx/";
    public final String SOAP_ADRESS = "http://siia.itlp.edu.mx/WebServiceITLP.asmx?WSDL";

    private String control;
    private String nombre;
    private String nombres;
    private String apellido;
    private String sexo;
    private String especialidad;
    private String plan;
    private String modulo;
    private int semestre;
    private int creditosAcumulados;
    private int creditosDelSemestre;
    private int creditosDelPlan;
    private boolean inscrito;
    private String correo;

    public ws_alumno(String control, String passws) {
        try {
            String xml = getXML(control, passws);

            Document doc = readXML(xml);

            //doc.getDocumentElement().normalize();
            //XPath xPath = XPathFactory.newInstance().newXPath();
            //String expression = "/soap:Envelope/soap:Body/consultarAlumnoResponse/consultarAlumnoResult";
            NodeList list = doc.getElementsByTagName("consultarAlumnoResult");//(NodeList) xPath.compile(expression).evaluate(doc, XPathConstants.NODESET);
            for (int i = 0; i < list.getLength(); i++) {
                Element element = (Element) list.item(i);
                control = element.getElementsByTagName("control").item(0).getTextContent();
                nombre = element.getElementsByTagName("nombre").item(0).getTextContent();
                nombres = element.getElementsByTagName("nombres").item(0).getTextContent();
                apellido = element.getElementsByTagName("apellido").item(0).getTextContent();
                sexo = element.getElementsByTagName("sexo").item(0).getTextContent();
                especialidad = element.getElementsByTagName("especialidad").item(0).getTextContent();
                plan = element.getElementsByTagName("plan").item(0).getTextContent();
                modulo = element.getElementsByTagName("modulo").item(0).getTextContent();
                semestre = Integer.parseInt(element.getElementsByTagName("semestre").item(0).getTextContent());
                creditosAcumulados = Integer.parseInt(element.getElementsByTagName("creditosAcumulados").item(0).getTextContent());
                creditosDelSemestre = Integer.parseInt(element.getElementsByTagName("creditosDelSemestre").item(0).getTextContent());
                creditosDelPlan = Integer.parseInt(element.getElementsByTagName("creditosDelPlan").item(0).getTextContent());
                inscrito = Boolean.parseBoolean(element.getElementsByTagName("inscrito").item(0).getTextContent());
                correo = element.getElementsByTagName("correo").item(0).getTextContent();
            }
        } catch (Exception e) {
        }
    }

    public String getControl() {
        return control;
    }

    public String getNombre() {
        return nombre;
    }

    public String getNombres() {
        return nombres;
    }

    public String getApellido() {
        return apellido;
    }

    public String getSexo() {
        return sexo;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public String getPlan() {
        return plan;
    }

    public String getModulo() {
        return modulo;
    }

    public int getSemestre() {
        return semestre;
    }

    public int getCreditosAcumulados() {
        return creditosAcumulados;
    }

    public int getCreditosDelSemestre() {
        return creditosDelSemestre;
    }

    public int getCreditosDelPlan() {
        return creditosDelPlan;
    }

    public boolean getInscrito() {
        return inscrito;
    }

    public String getCorreo() {
        return correo;
    }

    public String getXML(String control, String passws) {
        SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE, OPERATION_NAME);

        PropertyInfo pi = new PropertyInfo();
        pi.setName("numeroDeControl");
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
