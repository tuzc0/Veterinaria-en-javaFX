package logica.DTOs;

public class ProductoDTO {

    private int idProducto;
    private String nombre;
    private double precio;
    private String especie;
    private String tipo;
    private String marca;
    private int existencia;

    public ProductoDTO(int idProducto, String nombre, double precio, String especie, String tipo, String marca, int existencia) {

        this.idProducto = idProducto;
        this.nombre = nombre;
        this.precio = precio;
        this.especie = especie;
        this.tipo = tipo;
        this.marca = marca;
        this.existencia = existencia;
    }

    public int getIdProducto() { return idProducto; }
    public void setIdProducto(int idProducto) { this.idProducto = idProducto; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }

    public String getEspecie() { return especie; }
    public void setEspecie(String especie) { this.especie = especie; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }

    public int getExistencia() { return existencia; }
    public void setExistencia(int existencia) { this.existencia = existencia; }
}
