package com.example.isaimrafael.kardexv11.web_service;

public class ws_horario_aux {

    private String materia;
    private String dia;
    private String horaInicio;
    private String horaFin;
    private String aula;

    public void setAula(String aula) {
        this.aula = aula;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public void setHoraFin(String horaFin) {
        this.horaFin = horaFin;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }

    public String getHorario() {
        return String.format("%s-%s %s", horaInicio, horaFin, aula);
    }

    public int getHoraInicioInt() {
        return Integer.parseInt(horaInicio);
    }
}
