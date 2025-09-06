package mx.com.mxds.gstvent.persistencia;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import mx.com.mxds.gstvent.entidades.DetOrdVenta;
import mx.com.mxds.gstvent.servicios.IRepositorioDetOrdVenta;

public class DetOrdVentaRepositorioEnMemoria implements IRepositorioDetOrdVenta {
    private final Map<String, DetOrdVenta> detalles = new ConcurrentHashMap<>();
    
    private String generateKey(String idOrden, long idArticulo) {
        return idOrden + "-" + idArticulo;
    }
    
    @Override
    public DetOrdVenta save(DetOrdVenta detalle) {
        String key = generateKey(detalle.getIdOrden(), detalle.getIdArticulo());
        detalles.put(key, detalle);
        return detalle;
    }
    
    @Override
    public Optional<DetOrdVenta> findById(String idOrden, long idArticulo) {
        String key = generateKey(idOrden, idArticulo);
        return Optional.ofNullable(detalles.get(key));
    }
    
    @Override
    public List<DetOrdVenta> findByOrden(String idOrden) {
        return detalles.values().stream()
                .filter(det -> det.getIdOrden().equals(idOrden))
                .collect(Collectors.toList());
    }
    
    @Override
    public List<DetOrdVenta> findAll() {
        return new ArrayList<>(detalles.values());
    }
    
    @Override
    public boolean deleteById(String idOrden, long idArticulo) {
        String key = generateKey(idOrden, idArticulo);
        return detalles.remove(key) != null;
    }
    
    @Override
    public boolean update(DetOrdVenta detalle) {
        String key = generateKey(detalle.getIdOrden(), detalle.getIdArticulo());
        if (detalles.containsKey(key)) {
            detalles.put(key, detalle);
            return true;
        }
        return false;
    }
}

