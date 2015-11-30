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

public class ws_cursando {

    public final String SOAP_ACTION = "http://siia.itlp.edu.mx/consultarCursanddo";
    public final String OPERATION_NAME = "consultarCursanddo";
    public final String WSDL_TARGET_NAMESPACE = "http://siia.itlp.edu.mx/";
    public final String SOAP_ADRESS = "http://siia.itlp.edu.mx/WebServiceITLP.asmx?WSDL";

    private List<ws_curso> cursos = new ArrayList<ws_curso>();

    public ws_cursando(String control, String passws) {
        try {
            String xml = getXML(control, passws);
            Document doc = readXML(xml);
            NodeList list = doc.getElementsByTagName("consultarCursanddoResult");
            for (int i = 0; i < list.getLength(); i++) {
                Element element = (Element) list.item(i);
                ws_curso curso = new ws_curso();
                curso.setAlumno(element.getElementsByTagName("alumno").item(0).getTextContent());
                curso.setMateria(element.getElementsByTagName("materia").item(0).getTextContent());
                curso.setGrupo(element.getElementsByTagName("grupo").item(0).getTextContent());
                curso.setAnio(Integer.parseInt(element.getElementsByTagName("anio").item(0).getTextContent()));
                curso.setPeriodo(element.getElementsByTagName("periodo").item(0).getTextContent());
                curso.setCalificacion(Integer.parseInt(element.getElementsByTagName("calificacion").item(0).getTextContent()));
                curso.setTipoDeCurso(element.getElementsByTagName("tipoDeCurso").item(0).getTextContent());
                curso.setClave(element.getElementsByTagName("clave").item(0).getTextContent());
                curso.setCreditos(Integer.parseInt(element.getElementsByTagName("creditos").item(0).getTextContent()));
                NodeList grupolis = doc.getElementsByTagName("Grupo");
                for (int j = 0; j < grupolis.getLength(); j++) {
                    Element gr = (Element) grupolis.item(j);
                    ws_grupo grupo = new ws_grupo();
                    grupo.setMateria(gr.getElementsByTagName("materia").item(0).getTextContent());
                    grupo.setGrupo(gr.getElementsByTagName("grupo").item(0).getTextContent());
                    grupo.setAño(Integer.parseInt(gr.getElementsByTagName("anio").item(0).getTextContent()));
                    grupo.setCarrera(gr.getElementsByTagName("carrera").item(0).getTextContent());
                    grupo.setPeriodo(gr.getElementsByTagName("periodo").item(0).getTextContent());
                    grupo.setPlan(gr.getElementsByTagName("plan").item(0).getTextContent());
                    grupo.setClaveOficial(gr.getElementsByTagName("claveOficial").item(0).getTextContent());
                    grupo.setMaestro(gr.getElementsByTagName("maestro").item(0).getTextContent());
                    NodeList nHorario = doc.getElementsByTagName("horario");
                    for (int k = 0; k < nHorario.getLength(); k++) {
                        Element he = (Element) nHorario.item(k);
                        ws_horario horario = new ws_horario();
                        horario.setAula(he.getElementsByTagName("aula").item(0).getTextContent());
                        horario.setDia(he.getElementsByTagName("dia").item(0).getTextContent());
                        horario.setHoraInicio(he.getElementsByTagName("horaInicio").item(0).getTextContent());
                        horario.setHoraFin(he.getElementsByTagName("horaFin").item(0).getTextContent());
                        curso.getHorarios().add(horario);
                    }
                    curso.getGrupos().add(grupo);
                }
                cursos.add(curso);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    private Document readXML(String xml) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        InputSource source = new InputSource(new StringReader(xml));
        return builder.parse(source);
    }
}
