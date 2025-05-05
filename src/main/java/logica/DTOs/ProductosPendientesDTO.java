package logica.DTOs;

import java.sql.Timestamp;

public class ProductosPendientesDTO {

    private int idProducto;
    private Timestamp fechaRegistro;

    public ProductosPendientesDTO(int idProducto, Timestamp fechaRegistro) {

        this.idProducto = idProducto;
        this.fechaRegistro = fechaRegistro;
    }

    public int getIdProducto() { return idProducto; }
    public void setIdProducto(int idProducto) { this.idProducto = idProducto; }

    public Timestamp getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(Timestamp fechaRegistro) { this.fechaRegistro = fechaRegistro; }
}
