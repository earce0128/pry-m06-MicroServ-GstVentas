package mx.com.mxds.gstvent.persistencia;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import mx.com.mxds.gstvent.entidades.ArticuloVenta;
import mx.com.mxds.gstvent.servicios.IRepositorioArticuloVenta;

public class ArticuloVentaRepositorioEnMemoria implements IRepositorioArticuloVenta {
    private final Map<Long, ArticuloVenta> articulos = new ConcurrentHashMap<>();
    private final Map<String, Long> codigoToId = new ConcurrentHashMap<>();
    private final AtomicLong idCounter = new AtomicLong(1);
    
    public ArticuloVentaRepositorioEnMemoria() {
		cargarDatosArticulo();
	}
    
    public static List<ArticuloVenta> obtenerArtEjemplo(){
    	return Arrays.asList(
    			new ArticuloVenta(0,"LAP-001", "Laptop Dell XPS 13", new BigDecimal("18999.00"), 15),
                new ArticuloVenta(0,"MON-002", "Monitor Samsung 24\" Curvo", new BigDecimal("3499.00"), 25),
                new ArticuloVenta(0,"TEC-003", "Teclado Mecánico Redragon Kumara", new BigDecimal("899.00"), 40),
                new ArticuloVenta(0,"MOU-004", "Mouse Logitech G203 Lightsync", new BigDecimal("499.00"), 60),
                new ArticuloVenta(0,"AUD-005", "Audífonos Sony WH-CH510", new BigDecimal("1299.00"), 30),
                new ArticuloVenta(0,"TAB-006", "Tableta Samsung Galaxy Tab A8", new BigDecimal("5499.00"), 18),
                new ArticuloVenta(0,"IMP-007", "Impresora HP Deskjet 2775", new BigDecimal("1999.00"), 12),
                new ArticuloVenta(0,"DIS-008", "Disco Duro Externo Seagate 1TB", new BigDecimal("1299.00"), 22),
                new ArticuloVenta(0,"MEM-009", "Memoria USB Kingston 64GB", new BigDecimal("299.00"), 100),
                new ArticuloVenta(0,"CAM-010", "Cámara Web Logitech C920", new BigDecimal("1799.00"), 20)
            );
    }
    
    private void cargarDatosArticulo() {
    	
    	List<ArticuloVenta> articulosVta = obtenerArtEjemplo();
    	
    	for (ArticuloVenta articulo : articulosVta) {
            if(articulo.getIdArticulo() == 0) {
            	articulo.setIdArticulo(idCounter.getAndIncrement());
            }
            articulos.put(articulo.getIdArticulo(), articulo);
            codigoToId.put(articulo.getCodigo(), articulo.getIdArticulo());
        }
    }

//    @Override
//    public ArticuloVenta save(ArticuloVenta articulo) {
//        if (articulo.getIdArticulo() == 0) {
//            articulo.setIdArticulo(idCounter.getAndIncrement());
//        }
//        articulos.put(articulo.getIdArticulo(), articulo);
//        codigoToId.put(articulo.getCodigo(), articulo.getIdArticulo());
//        return articulo;
//    }
    
    @Override
    public Optional<ArticuloVenta> findById(long id) {
    	return Optional.ofNullable(articulos.get(id));
    }
    
    @Override
    public List<ArticuloVenta> findAll() {
        return new ArrayList<>(articulos.values());
    }
    
//    @Override
//    public boolean deleteById(long id) {
//        ArticuloVenta articulo = articulos.remove(id);
//        if (articulo != null) {
//            codigoToId.remove(articulo.getCodigo());
//            return true;
//        }
//        return false;
//    }
    
//    @Override
//    public boolean update(ArticuloVenta articulo) {
//        if (articulos.containsKey(articulo.getIdArticulo())) {
//            ArticuloVenta antiguo = articulos.get(articulo.getIdArticulo());
//            // Si cambió el código, actualizar el mapa de códigos
//            if (!antiguo.getCodigo().equals(articulo.getCodigo())) {
//                codigoToId.remove(antiguo.getCodigo());
//                codigoToId.put(articulo.getCodigo(), articulo.getIdArticulo());
//            }
//            articulos.put(articulo.getIdArticulo(), articulo);
//            return true;
//        }
//        return false;
//    }
    
    @Override
    public boolean updateStock(long idArticulo, int nuevoStock) {
        Optional<ArticuloVenta> articuloOpt = findById(idArticulo);
        if (articuloOpt.isPresent()) {
            ArticuloVenta articulo = articuloOpt.get();
            articulo.setStock(nuevoStock);
            articulos.put(articulo.getIdArticulo(), articulo);
            return true;
        }
        return false;
    }
    
    @Override
    public Optional<ArticuloVenta> findByCodigo(String codigo) {
        Long id = codigoToId.get(codigo);
        if (id != null) {
            return Optional.ofNullable(articulos.get(id));
        }
        return Optional.empty();
    }
}

