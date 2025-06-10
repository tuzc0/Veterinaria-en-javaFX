package logica.DTOs;

import java.sql.Timestamp;

public class CitaDTO {

    private int idCita;
    private Timestamp fecha;
    private String tipo;
    private String motivo;
    private int idAnimal;
    private int idSecretario;
    private int idVeterinario;
    private Estatus estatus;

    public CitaDTO(int idCita, Timestamp fecha, String tipo, String motivo, int idAnimal, int idSecretario, int idVeterinario, Estatus estatus) {

        this.idCita = idCita;
        this.fecha = fecha;
        this.tipo = tipo;
        this.motivo = motivo;
        this.idAnimal = idAnimal;
        this.idSecretario = idSecretario;
        this.idVeterinario = idVeterinario;
        this.estatus = estatus;
    }

    public CitaDTO(){

    }

    public int getIdCita() { return idCita; }
    public void setIdCita(int idCita) { this.idCita = idCita; }

    public Timestamp getFecha() { return fecha; }
    public void setFecha(Timestamp fecha) { this.fecha = fecha; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }

    public int getIdAnimal() { return idAnimal; }
    public void setIdAnimal(int idAnimal) { this.idAnimal = idAnimal; }

    public int getIdSecretario() { return idSecretario; }
    public void setIdSecretario(int idSecretario) { this.idSecretario = idSecretario; }

    public int getIdVeterinario() { return idVeterinario; }
    public void setIdVeterinario(int idVeterinario) { this.idVeterinario = idVeterinario; }

    public Estatus getEstatus() { return estatus; }
    public void setEstatus(Estatus estatus) { this.estatus = estatus; }
}
