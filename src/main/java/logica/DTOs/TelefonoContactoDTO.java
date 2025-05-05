package logica.DTOs;

public class TelefonoContactoDTO {

    private int idUsuario;
    private String telefono;

    public TelefonoContactoDTO(int idUsuario, String telefono) {
        this.idUsuario = idUsuario;
        this.telefono = telefono;
    }


    public TelefonoContactoDTO() {
    }



    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
