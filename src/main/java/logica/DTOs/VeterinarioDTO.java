package logica.DTOs;

public class VeterinarioDTO {

    private int idVeterinario;
    private String numeroCedulaProfesional;
    private String nombre;
    private String apellidos;
    private String telefonoCelular;
    private String telefonoDeEmergencia;
    private String usuario;
    private String contrasena;
    private String calle;
    private String numero;
    private String colonia;

    public VeterinarioDTO(int idVeterinario, String numeroCedulaProfesional, String nombre, String apellidos, String telefonoCelular, String telefonoDeEmergencia, String usuario, String contrasena, String calle, String numero, String colonia) {

        this.idVeterinario = idVeterinario;
        this.numeroCedulaProfesional = numeroCedulaProfesional;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.telefonoCelular = telefonoCelular;
        this.telefonoDeEmergencia = telefonoDeEmergencia;
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.calle = calle;
        this.numero = numero;
        this.colonia = colonia;
    }

    public int getIdVeterinario() { return idVeterinario; }
    public void setIdVeterinario(int idVeterinario) { this.idVeterinario = idVeterinario; }

    public String getNumeroCedulaProfesional() { return numeroCedulaProfesional; }
    public void setNumeroCedulaProfesional(String numeroCedulaProfesional) { this.numeroCedulaProfesional = numeroCedulaProfesional; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }

    public String getTelefonoCelular() { return telefonoCelular; }
    public void setTelefonoCelular(String telefonoCelular) { this.telefonoCelular = telefonoCelular; }

    public String getTelefonoDeEmergencia() { return telefonoDeEmergencia; }
    public void setTelefonoDeEmergencia(String telefonoDeEmergencia) { this.telefonoDeEmergencia = telefonoDeEmergencia; }

    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }

    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }

    public String getCalle() { return calle; }
    public void setCalle(String calle) { this.calle = calle; }

    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }

    public String getColonia() { return colonia; }
    public void setColonia(String colonia) { this.colonia = colonia; }

    @Override
    public String toString() {
        return
                nombre+ " " +
                apellidos + '\'';

    }
}
