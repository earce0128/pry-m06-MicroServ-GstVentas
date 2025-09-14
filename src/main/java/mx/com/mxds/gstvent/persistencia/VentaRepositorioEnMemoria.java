package mx.com.mxds.gstvent.persistencia;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Repository;

import mx.com.mxds.gstvent.entidades.ArticuloVenta;
import mx.com.mxds.gstvent.entidades.DetOrdVenta;
import mx.com.mxds.gstvent.entidades.PagoVenta;
import mx.com.mxds.gstvent.entidades.Venta;
import mx.com.mxds.gstvent.servicios.IRepositorioArticuloVenta;
import mx.com.mxds.gstvent.servicios.IRepositorioDetOrdVenta;
import mx.com.mxds.gstvent.servicios.IRepositorioVenta;

@Repository
public class VentaRepositorioEnMemoria implements IRepositorioVenta {
    private final Map<String, Venta> ventas = new ConcurrentHashMap<>();
    private final AtomicLong idCounter = new AtomicLong(1);
    
    IRepositorioArticuloVenta articuloRepo = new ArticuloVentaRepositorioEnMemoria();
    IRepositorioDetOrdVenta detallesVtaRepo = new DetOrdVentaRepositorioEnMemoria();
    
    public VentaRepositorioEnMemoria() {
		cargarDatosVentas();
	}
    
    //Venta(String idOrden, LocalDate fechaVenta, Long idVendedor, String nombreVendedor, String status,
	//		BigDecimal subtotal, double iva, BigDecimal total)
    
	private List<Venta> obtenerVentas() {
		return Arrays.asList(
    			new Venta("",LocalDate.now(),1L,"Luis Torres","PENDIENTE",new BigDecimal(25695.00),0.16, new BigDecimal(29806.2)),
    			new Venta("",LocalDate.now(),2L,"Juan Perez","PAGADA",new BigDecimal(25695.00),0.16, new BigDecimal(29806.2)),
    			new Venta("",LocalDate.now(),2L,"Juan Perez","PAGADA",new BigDecimal(25695.00),0.16, new BigDecimal(29806.2)),
    			new Venta("",LocalDate.now(),3L,"Luisa Baez","PAGADA",new BigDecimal(25695.00),0.16, new BigDecimal(29806.2))
    			);
	}

	private void cargarDatosVentas() {
		
		List<Venta> lstVentas = obtenerVentas();
    	
    	for (Venta venta : lstVentas) {
            if(venta.getIdOrden().isEmpty()) {
            	venta.setIdOrden("v-" + idCounter.getAndIncrement());
            }
            
            ArticuloVenta art1 = articuloRepo.findById(10L).get();
            ArticuloVenta art2 = articuloRepo.findById(1L).get();
            ArticuloVenta art3 = articuloRepo.findById(5L).get();
            
            DetOrdVenta det1 = new DetOrdVenta(venta.getIdOrden(),art1, 3);
            DetOrdVenta det2 = new DetOrdVenta(venta.getIdOrden(),art2, 1);
            DetOrdVenta det3 = new DetOrdVenta(venta.getIdOrden(),art3, 1);
            
            PagoVenta pago = new PagoVenta();
            pago.setIdOrdenVta(venta.getIdOrden());
            pago.setFechaPago(LocalDate.now());
            pago.setTipoPago("TARJETA");
            pago.setMonto(new BigDecimal(29806.2));
            
            //det1 = detallesVtaRepo.save(venta.getIdOrden(),det1);
            //det2 = detallesVtaRepo.save(venta.getIdOrden(),det2);
            //det3 = detallesVtaRepo.save(venta.getIdOrden(),det3);
            
            det1 = detallesVtaRepo.save(det1);
            det2 = detallesVtaRepo.save(det2);
            det3 = detallesVtaRepo.save(det3);
            
            List<DetOrdVenta> detalles = new ArrayList<>();
            detalles.add(det1);
            detalles.add(det2);
            detalles.add(det3);
            
            //venta.setDetArticulos(Arrays.asList(det1,det2,det3));
            venta.setDetArticulos(detalles);
            venta.setPago(pago);
            
            //System.out.println("Ordenes: " + detallesVtaRepo.findByOrden(venta.getIdOrden()));
            //System.out.println("Pago: " + venta.getPago());
            //System.out.println("Detalle: " + venta.getDetArticulos());
            
            ventas.put(venta.getIdOrden(), venta);
        }
		
	}

	@Override
    public Venta save(Venta venta) {
        if (venta.getIdOrden() == null || venta.getIdOrden().isEmpty()) {
            venta.setIdOrden("v-" + idCounter.getAndIncrement());
        } 
        ventas.put(venta.getIdOrden(), venta);
        return venta;
    }
    
    @Override
    public Optional<Venta> findById(String id) {
        return Optional.ofNullable(ventas.get(id));
    }
    
    @Override
    public List<Venta> findAll() {
        return new ArrayList<>(ventas.values());
    }
    
    @Override
    public boolean deleteById(String id) {
        return ventas.remove(id) != null;
    }
    
    @Override
    public boolean update(Venta venta) {
        if (ventas.containsKey(venta.getIdOrden())) {
            ventas.put(venta.getIdOrden(), venta);
            return true;
        }
        return false;
    }
}

