package logica.DTOs;

public class SecretarioDTO {

    private int idSecretario;
    private String numeroINE;
    private String nombre;
    private String apellidos;
    private String telefono;
    private String usuario;
    private String contrasena;
    private String calle;
    private String numero;
    private String colonia;

    public SecretarioDTO(int idSecretario, String numeroINE, String nombre, String apellidos, String telefono, String usuario, String contrasena, String calle, String numero, String colonia) {

        this.idSecretario = idSecretario;
        this.numeroINE = numeroINE;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.telefono = telefono;
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.calle = calle;
        this.numero = numero;
        this.colonia = colonia;
    }

    public int getIdSecretario() { return idSecretario; }
    public void setIdSecretario(int idSecretario) { this.idSecretario = idSecretario; }

    public String getNumeroINE() { return numeroINE; }
    public void setNumeroINE(String numeroINE) { this.numeroINE = numeroINE; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

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
}
