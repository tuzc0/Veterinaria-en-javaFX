package logica.DTOs;

public class DueñoDTO {

    private int idDueño;
    private String nombre;
    private String apellidos;
    private String calle;
    private String numero;
    private String colonia;

    public DueñoDTO(int idDueño, String nombre, String apellidos, String calle, String numero, String colonia) {

        this.idDueño = idDueño;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.calle = calle;
        this.numero = numero;
        this.colonia = colonia;
    }

    public int getIdDueño() { return idDueño; }
    public void setIdDueño(int idDueño) { this.idDueño = idDueño; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }

    public String getCalle() { return calle; }
    public void setCalle(String calle) { this.calle = calle; }

    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }

    public String getColonia() { return colonia; }
    public void setColonia(String colonia) { this.colonia = colonia; }
}
