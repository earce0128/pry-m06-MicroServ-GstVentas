package mx.com.mxds.gstvent.servicios;

import mx.com.mxds.gstvent.persistencia.ArticuloVentaRepositorioEnMemoria;
import mx.com.mxds.gstvent.persistencia.DetOrdVentaRepositorioEnMemoria;
import mx.com.mxds.gstvent.persistencia.PagoVentaRepositorioEnMemoria;
import mx.com.mxds.gstvent.persistencia.VentaRepositorioEnMemoria;

public class RepositoryFactory {
    private static IRepositorioVenta ventaRepository;
    private static IRepositorioDetOrdVenta detalleRepository;
    private static IRepositorioArticuloVenta articuloRepository;
    private static IRepositorioPagoVenta pagoRepository;
    
    public static IRepositorioVenta getVentaRepository() {
        if (ventaRepository == null) {
            ventaRepository = new VentaRepositorioEnMemoria();
        }
        return ventaRepository;
    }
    
    public static IRepositorioDetOrdVenta getDetalleRepository() {
        if (detalleRepository == null) {
            detalleRepository = new DetOrdVentaRepositorioEnMemoria();
        }
        return detalleRepository;
    }
    
    public static IRepositorioArticuloVenta getArticuloRepository() {
        if (articuloRepository == null) {
            articuloRepository = new ArticuloVentaRepositorioEnMemoria();
        }
        return articuloRepository;
    }
    
    public static IRepositorioPagoVenta getPagoRepository() {
        if (pagoRepository == null) {
            pagoRepository = new PagoVentaRepositorioEnMemoria();
        }
        return pagoRepository;
    }
    
    /*public static ServicioVentas getVentaService() {
        return new ServicioVentas(
            getVentaRepository(),
            getDetalleRepository(),
            getArticuloRepository(),
            getPagoRepository()
        );
    }*/
}