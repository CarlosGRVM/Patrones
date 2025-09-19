package Modelo;

import java.sql.Date;
import java.sql.Time;

public class Cita {

    private int idCita;
    private int idPaciente;
    private int idMedico;
    private Date fecha;
    private Time hora;
    private String estado; // Pendiente, Atendida, Cancelada

    public Cita() {
    }

    public Cita(int idPaciente, int idMedico, Date fecha, Time hora, String estado) {
        this.idPaciente = idPaciente;
        this.idMedico = idMedico;
        this.fecha = fecha;
        this.hora = hora;
        this.estado = estado;
    }

    // Getters y setters
    public int getIdCita() {
        return idCita;
    }

    public void setIdCita(int idCita) {
        this.idCita = idCita;
    }

    public int getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(int idPaciente) {
        this.idPaciente = idPaciente;
    }

    public int getIdMedico() {
        return idMedico;
    }

    public void setIdMedico(int idMedico) {
        this.idMedico = idMedico;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Time getHora() {
        return hora;
    }

    public void setHora(Time hora) {
        this.hora = hora;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
