package logica;

import accesoadatos.ConexionBaseDeDatos;
import logica.DAOs.ProcedimientosDAO;
import logica.DAOs.ReporteVentasDAO;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class main {

    public static void main(String[] args) {
        try {
            // Crear conexión
            ConexionBaseDeDatos conexionBD = new ConexionBaseDeDatos();

            // Instanciar DAO
            ProcedimientosDAO dao = new ProcedimientosDAO(conexionBD);
            ReporteVentasDAO reporteVentasDAO = new ReporteVentasDAO(conexionBD);

            // 1. Probar registrar compra (usa IDs válidos existentes)
            int idProducto = 1;
            int idDueno = 1;
            Timestamp fecha = Timestamp.valueOf(LocalDateTime.now());
            dao.registrarCompra(idProducto, idDueno, fecha);

            // 2. Verificar veterinarios disponibles (en fecha/hora futura)
            Timestamp cita = Timestamp.valueOf("2025-06-12 10:00:00");
            dao.verificarVeterinariosDisponibles(cita);

            // 3. Producto más vendido
            reporteVentasDAO.obtenerProductoMasVendido(6, 2025);

            // 4. Producto menos vendido
            reporteVentasDAO.obtenerProductoMenosVendido(6, 2025);

            // 5. Promedio de ventas
            reporteVentasDAO.obtenerPromedioVentas(6, 2025);

            // 6. Suma total de ventas
            reporteVentasDAO.obtenerSumaTotalVentas(6, 2025);

            // 7. Contar productos por nombre
            int totalProductos = dao.contarProductosPorNombre("croquetas premium");
            System.out.println("Cantidad de productos con ese nombre: " + totalProductos);

            // 8. Contar citas por mes por veterinario
            int totalCitas = dao.contarCitasPorMes(1, 6, 2025);
            System.out.println("Citas de junio del veterinario 1: " + totalCitas);

        } catch (Exception e) {
            System.err.println("Error general: " + e.getMessage());
        }
    }
}
