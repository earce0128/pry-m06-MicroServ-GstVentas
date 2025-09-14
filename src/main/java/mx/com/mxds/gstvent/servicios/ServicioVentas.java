package mx.com.mxds.gstvent.servicios;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.com.mxds.gstvent.core.IServicioVentas;
import mx.com.mxds.gstvent.entidades.ArticuloVenta;
import mx.com.mxds.gstvent.entidades.DetOrdVenta;
import mx.com.mxds.gstvent.entidades.PagoVenta;
import mx.com.mxds.gstvent.entidades.Venta;
import mx.com.mxds.gstvent.persistencia.ArticuloVentaRepositorioEnMemoria;
import mx.com.mxds.gstvent.persistencia.DetOrdVentaRepositorioEnMemoria;
import mx.com.mxds.gstvent.persistencia.PagoVentaRepositorioEnMemoria;
//import mx.com.mxds.gstvent.persistencia.VentaRepositorioEnMemoria;

@Service
public class ServicioVentas implements IServicioVentas {
    
	@Autowired
	IRepositorioVenta repoVentas;
	
	//IRepositorioVenta repoVentas = new VentaRepositorioEnMemoria();
	
	IRepositorioArticuloVenta repoArtVtas = new ArticuloVentaRepositorioEnMemoria();
	
	IRepositorioDetOrdVenta repoDetVenta = new DetOrdVentaRepositorioEnMemoria();
	
	IRepositorioPagoVenta repoPagoVenta = new PagoVentaRepositorioEnMemoria(); 
	
    public ServicioVentas() {}
    
    @Override
    public void setRepoVenta(IRepositorioVenta repoVentas) {
		this.repoVentas = repoVentas;
	}

	public void setRepoArtVtas(IRepositorioArticuloVenta repoArtVtas) {
		this.repoArtVtas = repoArtVtas;
	}

	public void setRepoDetVenta(IRepositorioDetOrdVenta repoDetVenta) {
		this.repoDetVenta = repoDetVenta;
	}

	public Venta crearVenta(Venta venta, List<DetOrdVenta> detalles, PagoVenta pago) {
        // Validar stock antes de procesar la venta
        for (DetOrdVenta detalle : detalles) {
            //Optional<ArticuloVenta> articuloOpt = articuloRepository.findById(detalle.getIdArticulo());
        	Optional<ArticuloVenta> articuloOpt = repoArtVtas.findById(detalle.getIdArticulo());
            if (!articuloOpt.isPresent()) {
                throw new RuntimeException("Artículo no encontrado: " + detalle.getIdArticulo());
            }
            
            ArticuloVenta articulo = articuloOpt.get();
            if (articulo.getStock() < detalle.getCantidad()) {
                throw new RuntimeException("Stock insuficiente para: " + articulo.getDescripcion());
            }
        }
        
        // Guardar venta
        Venta ventaGuardada = repoVentas.save(venta);
        
        // Guardar detalles y actualizar stock
        for (DetOrdVenta detalle : detalles) {
            detalle.setIdOrden(ventaGuardada.getIdOrden());
            repoDetVenta.save(detalle);
            
            // Actualizar stock
            ArticuloVenta articulo = repoArtVtas.findById(detalle.getIdArticulo()).get();
            articulo.setStock(articulo.getStock() - detalle.getCantidad());
            repoArtVtas.updateStock(articulo.getIdArticulo(), articulo.getStock());
        }
        
        BigDecimal subTotal = this.calcularSubTotalVenta(ventaGuardada.getIdOrden());
        BigDecimal total = this.calcularTotalVenta(ventaGuardada.getIdOrden());
        
        ventaGuardada.setSubtotal(subTotal);
        ventaGuardada.setTotal(total);
        
        // Guardar pago
        if (pago != null) {
            pago.setIdOrdenVta(ventaGuardada.getIdOrden());
            repoPagoVenta.save(pago);
            ventaGuardada.setPago(pago);
            repoVentas.update(ventaGuardada);
        }
        
        return ventaGuardada;
    }
    
    @Override
    public Venta obtenerVenta(String idOrder) {
        //Optional<Venta> ventaOpt = ventaRepository.findById(idOrder);
    	Optional<Venta> ventaOpt = repoVentas.findById(idOrder);
        if (ventaOpt.isPresent()) {
            Venta venta = ventaOpt.get();
            
            //System.out.println("idOrden: " + idOrder + " Venta: " + venta);
            
            // Obtener detalles
            //List<DetOrdVenta> detalles = repoDetVenta.findByOrden(idOrder);
            //System.out.println("OrdDet: " + detalles);
            
            //venta.setDetArticulos(detalles);
            
            // Obtener pago
            //Optional<PagoVenta> pagoOpt = repoPagoVenta.findByOrderId(idOrder);
            //pagoOpt.ifPresent(venta::setPago);
            
            return venta;
        }
        return null;
    }
    
    public boolean cancelarVenta(String idOrder) {
        //Optional<Venta> ventaOpt = ventaRepository.findById(idOrder);
    	Optional<Venta> ventaOpt = repoVentas.findById(idOrder);
        if (ventaOpt.isPresent()) {
            //Venta venta = ventaOpt.get();
            
            // Restaurar stock
            List<DetOrdVenta> detalles = repoDetVenta.findByOrden(idOrder);
            for (DetOrdVenta detalle : detalles) {
                //Optional<ArticuloVenta> articuloOpt = articuloRepository.findById(detalle.getIdArticulo());
                Optional<ArticuloVenta> articuloOpt = repoArtVtas.findById(detalle.getIdArticulo());
                if (articuloOpt.isPresent()) {
                    ArticuloVenta articulo = articuloOpt.get();
                    articulo.setStock(articulo.getStock() + detalle.getCantidad());
                    //articuloRepository.updateStock(articulo.getIdArticulo(), articulo.getStock());
                    repoArtVtas.updateStock(articulo.getIdArticulo(), articulo.getStock());
                }
            }
            
            // Eliminar detalles y pago
            for (DetOrdVenta detalle : detalles) {
            	repoDetVenta.deleteById(idOrder, detalle.getIdArticulo());
            }
            
            repoPagoVenta.deleteByOrderId(idOrder);
            
            // Eliminar venta
            //return ventaRepository.deleteById(idOrder);
            return repoVentas.deleteById(idOrder);
        }
        return false;
    }
    
    @Override
    public BigDecimal calcularTotalVenta(String idOrder) {
        //Optional<Venta> ventaOpt = ventaRepository.findById(idOrder);
    	Optional<Venta> ventaOpt = repoVentas.findById(idOrder);
        if (ventaOpt.isPresent()) {
            Venta venta = ventaOpt.get();
            List<DetOrdVenta> detalles = repoDetVenta.findByOrden(idOrder);
            
            BigDecimal subtotal = BigDecimal.ZERO;
            for (DetOrdVenta detalle : detalles) {
                //Optional<ArticuloVenta> articuloOpt = articuloRepository.findById(detalle.getIdArticulo());
                Optional<ArticuloVenta> articuloOpt = repoArtVtas.findById(detalle.getIdArticulo());
                if (articuloOpt.isPresent()) {
                    ArticuloVenta articulo = articuloOpt.get();
                    BigDecimal precioTotal = articulo.getPrecioBase()
                            .multiply(BigDecimal.valueOf(detalle.getCantidad()));
                    subtotal = subtotal.add(precioTotal);
                }
            }
            
            BigDecimal iva = subtotal.multiply(BigDecimal.valueOf(venta.getIva()));
            return subtotal.add(iva);
        }
        return BigDecimal.ZERO;
    }
    
    @Override
    public BigDecimal calcularSubTotalVenta(String idOrder) {
        //Optional<Venta> ventaOpt = ventaRepository.findById(idOrder);
    	Optional<Venta> ventaOpt = repoVentas.findById(idOrder);
        if (ventaOpt.isPresent()) {
            //Venta venta = ventaOpt.get();
            List<DetOrdVenta> detalles = repoDetVenta.findByOrden(idOrder);
            
            BigDecimal subtotal = BigDecimal.ZERO;
            for (DetOrdVenta detalle : detalles) {
                Optional<ArticuloVenta> articuloOpt = repoArtVtas.findById(detalle.getIdArticulo());
                if (articuloOpt.isPresent()) {
                    ArticuloVenta articulo = articuloOpt.get();
                    BigDecimal precioTotal = articulo.getPrecioBase()
                            .multiply(BigDecimal.valueOf(detalle.getCantidad()));
                    subtotal = subtotal.add(precioTotal);
                }
            }
            
            //BigDecimal iva = subtotal.multiply(BigDecimal.valueOf(venta.getIva()));
            return subtotal;
        }
        return BigDecimal.ZERO;
    }

	@Override
	public List<Venta> obtenerListaVentas() {
		return repoVentas.findAll();
	}
}