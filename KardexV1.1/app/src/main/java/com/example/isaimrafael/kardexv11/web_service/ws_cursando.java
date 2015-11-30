package com.example.isaimrafael.kardexv11.web_service;

import android.support.annotation.NonNull;

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
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by IsaimRafael on 27/11/2015.
 */
public class ws_cursando {
    public final String SOAP_ACTION = "http://siia.itlp.edu.mx/consultarHistorialAcademico";
    public final String OPERATION_NAME = "consultarHistorialAcademico";
    public final String WSDL_TARGET_NAMESPACE = "http://siia.itlp.edu.mx/";
    public final String SOAP_ADRESS = "http://siia.itlp.edu.mx/WebServiceITLP.asmx?WSDL";

    private String alumno;
    private String materia;
    private String grupo;
    private int año;
    private String periodo;
    private int calificacion;
    private String tipodecurso;
    private String clave;
    private int creditos;
    private List<ws_grupo> arregloCurso;

    public ws_cursando(String control, String passws) {
        try {
            int val = valor();
            String xml = getXML(control, passws);
            Document doc = readXML(xml);
            NodeList list = doc.getElementsByTagName("consultarCursanddoResult");
            for (int i = 0; i < list.getLength(); i++) {
                Element element = (Element) list.item(i);
                alumno = element.getElementsByTagName("alumno").item(0).getTextContent();
                materia = element.getElementsByTagName("materia").item(0).getTextContent();
                grupo = element.getElementsByTagName("grupo").item(0).getTextContent();
                año = Integer.parseInt(element.getElementsByTagName("año").item(0).getTextContent());
                periodo = element.getElementsByTagName("periodo").item(0).getTextContent();
                calificacion = Integer.parseInt(element.getElementsByTagName("calificacion").item(0).getTextContent());
                tipodecurso = element.getElementsByTagName("tipoDeCurso").item(0).getTextContent();
                clave = element.getElementsByTagName("clave").item(0).getTextContent();
                creditos = Integer.parseInt(element.getElementsByTagName("creditos").item(0).getTextContent());
                NodeList grupolis = doc.getElementsByTagName("Grupo");
                for (int j=0; j < grupolis.getLength(); j++){
                    Element gr = (Element)grupolis.item(j);
                    //arregloCurso.setMateria(gr.getElementsByTagName("materia").item(0).getTextContent());
                    //arregloCurso.add(gr.getElementsByTagName("materia").item(gr).getTextContent());
                }
            }
        } catch (Exception e) {
        }
    }

    public int valor(){
        return 1;
    }

    public String getMateria() {
        return materia;
    }

    public int getAño() {
        return año;
    }

    public int getCreditos() {
        return creditos;
    }

    public List<ws_grupo> getArregloCurso() {
        return arregloCurso;
    }

    public String getAlumno() {
        return alumno;
    }

    public int getCalificacion() {
        return calificacion;
    }

    public String getClave() {
        return clave;
    }

    public String getGrupo() {
        return grupo;
    }

    public String getPeriodo() {
        return periodo;
    }

    public String getXML(String control, String passws) {
        SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE, OPERATION_NAME);

        PropertyInfo pi = new PropertyInfo();
        pi.setName("numeroDeControl");
        pi.setValue(control);
        pi.setType(String.class);
        request.addProperty(pi);
        pi = new PropertyInfo();
        pi.setName("contraseña");
        pi.setValue(passws);
        pi.setType(String.class);
        request.addProperty(pi);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.implicitTypes =true;
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
