package logica.DTOs;

public class DiagnosticaDTO {
    private int idCita;
    private int idEnfermedad;

    public DiagnosticaDTO(int idCita, int idEnfermedad) {

        this.idCita = idCita;
        this.idEnfermedad = idEnfermedad;
    }

    public int getIdCita() { return idCita; }
    public void setIdCita(int idCita) { this.idCita = idCita; }

    public int getIdEnfermedad() { return idEnfermedad; }
    public void setIdEnfermedad(int idEnfermedad) { this.idEnfermedad = idEnfermedad; }
}
