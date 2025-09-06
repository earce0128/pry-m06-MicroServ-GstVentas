package mx.com.mxds.gstvent.persistencia;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;

import mx.com.mxds.gstvent.entidades.Venta;
import mx.com.mxds.gstvent.servicios.IRepositorioVenta;

@Repository
public class VentaRepositorioEnMemoria implements IRepositorioVenta {
    private final Map<String, Venta> ventas = new ConcurrentHashMap<>();
    
    @Override
    public Venta save(Venta venta) {
        if (venta.getIdOrden() == null) {
            venta.setIdOrden("V-" + System.currentTimeMillis());
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

