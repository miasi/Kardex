package com.example.isaimrafael.kardexv11.web_service;

import java.util.ArrayList;
import java.util.List;

public class ws_curso {

    private String alumno;
    private String materia;
    private String grupo;
    private int anio;
    private String periodo;
    private int calificacion;
    private String tipoDeCurso;
    private String clave;
    private int creditos;
    private List<ws_grupo> grupos = new ArrayList<ws_grupo>();
    private List<ws_horario> horarios = new ArrayList<ws_horario>();

    public String getAlumno() {
        return alumno;
    }

    public void setAlumno(String alumno) {
        this.alumno = alumno;
    }

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public int getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(int calificacion) {
        this.calificacion = calificacion;
    }

    public int getCreditos() {
        return creditos;
    }

    public void setCreditos(int creditos) {
        this.creditos = creditos;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public String getTipoDeCurso() {
        return tipoDeCurso;
    }

    public void setTipoDeCurso(String tipoDeCurso) {
        this.tipoDeCurso = tipoDeCurso;
    }

    public List<ws_grupo> getGrupos() {
        return grupos;
    }

    public void setGrupos(List<ws_grupo> grupos) {
        this.grupos = grupos;
    }

    public List<ws_horario> getHorarios() {
        return horarios;
    }

    public void setHorarios(List<ws_horario> horarios) {
        this.horarios = horarios;
    }
}
