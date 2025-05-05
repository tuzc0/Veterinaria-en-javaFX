package logica.DTOs;

public class EnfermedadesDTO {

    private int idEnfermedad;
    private String nombre;
    private String especie;
    private String tratamiento;

    public EnfermedadesDTO(int idEnfermedad, String nombre, String especie, String tratamiento) {

        this.idEnfermedad = idEnfermedad;
        this.nombre = nombre;
        this.especie = especie;
        this.tratamiento = tratamiento;
    }

    public int getIdEnfermedad() { return idEnfermedad; }
    public void setIdEnfermedad(int idEnfermedad) { this.idEnfermedad = idEnfermedad; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEspecie() { return especie; }
    public void setEspecie(String especie) { this.especie = especie; }

    public String getTratamiento() { return tratamiento; }
    public void setTratamiento(String tratamiento) { this.tratamiento = tratamiento; }
}
