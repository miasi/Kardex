package com.example.isaimrafael.kardexv11.web_service;

public class ws_horario_maestro_personal {

    private String dia;
    private String hora;
    private String horaFin;
    private String lugar;

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public void setHoraFin(String hora) {
        int h = Integer.parseInt(hora) + 1;
        this.horaFin = Integer.toString(h);
    }

    public int getHoraInt() {
        return Integer.parseInt(hora);
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public String getHorario() {
        return String.format("%s-%s", hora, horaFin);
    }
}
