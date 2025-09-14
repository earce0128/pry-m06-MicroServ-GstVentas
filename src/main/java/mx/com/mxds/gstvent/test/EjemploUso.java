package mx.com.mxds.gstvent.test;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import mx.com.mxds.gstvent.core.IServicioVentas;
import mx.com.mxds.gstvent.entidades.ArticuloVenta;
import mx.com.mxds.gstvent.entidades.DetOrdVenta;
import mx.com.mxds.gstvent.entidades.PagoVenta;
import mx.com.mxds.gstvent.entidades.Venta;
import mx.com.mxds.gstvent.persistencia.ArticuloVentaRepositorioEnMemoria;
import mx.com.mxds.gstvent.persistencia.VentaRepositorioEnMemoria;
import mx.com.mxds.gstvent.servicios.IRepositorioArticuloVenta;
import mx.com.mxds.gstvent.servicios.ServicioVentas;

public class EjemploUso {
    public static void main(String[] args) {
        // Obtener servicios
        IRepositorioArticuloVenta articuloRepo = new ArticuloVentaRepositorioEnMemoria();
        
    	IServicioVentas ventaService = new ServicioVentas();
    	ventaService.setRepoVenta(new VentaRepositorioEnMemoria());
        
        Venta ventaBuscar = ventaService.obtenerVenta("V-2");
        System.out.println("VentaBuscar: " + ventaBuscar);
        System.out.println("Detalle: " + ventaBuscar.getDetArticulos());
        System.out.println("Pago: " + ventaBuscar.getPago());
        
        // Crear una venta
        Venta venta = new Venta();
        venta.setFechaVenta(LocalDate.now());
        venta.setIdVendedor(1L);
        venta.setNombreVendedor("Juan Pérez");
        venta.setStatus("PENDIENTE");
        venta.setIva(0.16);

        // Crear artículos de prueba
        
        Optional <ArticuloVenta> art1Opt = articuloRepo.findById(10L);
        Optional <ArticuloVenta> art2Opt = articuloRepo.findById(1L);
        Optional <ArticuloVenta> art3Opt = articuloRepo.findById(5L);

        ArticuloVenta art1 = (art1Opt.isPresent() ? art1Opt.get() : null );
        ArticuloVenta art2 = (art2Opt.isPresent() ? art2Opt.get() : null );
        ArticuloVenta art3 = (art3Opt.isPresent() ? art3Opt.get() : null );
        
        // Crear detalles de venta
        DetOrdVenta det1 = new DetOrdVenta("",art1, 3);
        DetOrdVenta det2 = new DetOrdVenta("",art2, 1);
        DetOrdVenta det3 = new DetOrdVenta("",art3, 1);
        
        // Crear pago
        PagoVenta pago = new PagoVenta();
        pago.setFechaPago(LocalDate.now());
        pago.setTipoPago("TARJETA");
        
        // Procesar venta
        try {
            Venta ventaProcesada = ventaService.crearVenta(
                venta, 
                Arrays.asList(det1, det2, det3), 
                pago
            );
            
            BigDecimal subTotal = ventaService.calcularSubTotalVenta(ventaProcesada.getIdOrden());
            BigDecimal total = ventaService.calcularTotalVenta(ventaProcesada.getIdOrden());
            
            ventaProcesada.setSubtotal(subTotal);
            ventaProcesada.setTotal(total);
            
            ventaProcesada.getPago().setMonto(total);
            
            System.out.println("Venta procesada: " + ventaProcesada.getIdOrden());
            System.out.println("Total: " + ventaService.calcularTotalVenta(ventaProcesada.getIdOrden()));
            
            // Consultar venta completa
            Venta ventaResp = ventaService.obtenerVenta(ventaProcesada.getIdOrden());
            
            System.out.println("General: " + ventaResp);
            
            if (ventaResp != null) {
                System.out.println("Detalles de la venta:");
                for (DetOrdVenta det : ventaResp.getDetArticulos()) {
                    System.out.println(" - Artículo: " + det.getCodigo());
                }
                ventaProcesada.setSubtotal(subTotal);
                ventaProcesada.setTotal(total);
                PagoVenta pagoP =  ventaProcesada.getPago();
                pagoP.setMonto(total);
                System.out.println("Subtotal:" + ventaResp.getSubtotal());
                System.out.println("Total:" + ventaResp.getTotal());
                System.out.println("Pago:" + ventaResp.getPago());
            }
            
        } catch (Exception e) {
            System.out.println("Error al procesar venta: " + e.getMessage());
        }
    }
}