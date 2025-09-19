package Modelo;

public class Medico {

    private int idMedico;
    private int idUsuario;       // referencia a Usuario
    private String especialidad;
    private String horarioDisponible;

    public Medico() {
    }

    public Medico(int idUsuario, String especialidad, String horarioDisponible) {
        this.idUsuario = idUsuario;
        this.especialidad = especialidad;
        this.horarioDisponible = horarioDisponible;
    }

    // Getters y setters
    public int getIdMedico() {
        return idMedico;
    }

    public void setIdMedico(int idMedico) {
        this.idMedico = idMedico;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getHorarioDisponible() {
        return horarioDisponible;
    }

    public void setHorarioDisponible(String horarioDisponible) {
        this.horarioDisponible = horarioDisponible;
    }
}
