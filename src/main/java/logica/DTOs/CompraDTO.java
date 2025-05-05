package logica.DTOs;

import java.sql.Timestamp;

public class CompraDTO {

    private int idProducto;
    private int idDueño;
    private Timestamp fechaCompra;

    public CompraDTO(int idProducto, int idDueño, Timestamp fechaCompra) {

        this.idProducto = idProducto;
        this.idDueño = idDueño;
        this.fechaCompra = fechaCompra;
    }

    public int getIdProducto() { return idProducto; }
    public void setIdProducto(int idProducto) { this.idProducto = idProducto; }

    public int getIdDueño() { return idDueño; }
    public void setIdDueño(int idDueño) { this.idDueño = idDueño; }

    public Timestamp getFechaCompra() { return fechaCompra; }
    public void setFechaCompra(Timestamp fechaCompra) { this.fechaCompra = fechaCompra;}
}
