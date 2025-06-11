package logica;

import logica.DTOs.ProductoDTO;
import logica.DTOs.ProductosPendientesDTO;

public class ContenedorProductosPendientes {

    private ProductoDTO productoPendiente;
    private ProductosPendientesDTO productosPendientesDTO;

    public ContenedorProductosPendientes(ProductoDTO productoPendiente, ProductosPendientesDTO productosPendientesDTO) {
        this.productoPendiente = productoPendiente;
        this.productosPendientesDTO = productosPendientesDTO;
    }

    public ProductoDTO getProductoPendiente() {
        return productoPendiente;
    }

    public void setProductoPendiente(ProductoDTO productoPendiente) {
        this.productoPendiente = productoPendiente;
    }

    public ProductosPendientesDTO getProductosPendientesDTO() {
        return productosPendientesDTO;
    }

    public void setProductosPendientesDTO(ProductosPendientesDTO productosPendientesDTO) {
        this.productosPendientesDTO = productosPendientesDTO;
    }


}
