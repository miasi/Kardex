package com.example.isaimrafael.kardexv11.web_service;

public class ws_grupo {

    private String materia;
    private String grupo;
    private int año;
    private String carrera;
    private String periodo;
    private String plan;
    private String claveOficial;
    private String maestro;

    public String getPeriodo() {
        return periodo;
    }

    public String getGrupo() {
        return grupo;
    }

    public int getAño() {
        return año;
    }

    public String getCarrera() {
        return carrera;
    }

    public String getClaveOficial() {
        return claveOficial;
    }

    public String getMaestro() {
        return maestro;
    }

    public String getPlan() {
        return plan;
    }

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }

    public void setAño(int año) {
        this.año = año;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    public void setClaveOficial(String claveOficial) {
        this.claveOficial = claveOficial;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public void setMaestro(String maestro) {
        this.maestro = maestro;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }
}
