package mx.com.mxds.gstvent.persistencia;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import mx.com.mxds.gstvent.entidades.PagoVenta;
import mx.com.mxds.gstvent.servicios.IRepositorioPagoVenta;

public class PagoVentaRepositorioEnMemoria implements IRepositorioPagoVenta {
    private final Map<String, PagoVenta> pagos = new ConcurrentHashMap<>();
    
    @Override
    public PagoVenta save(PagoVenta pago) {
        pagos.put(pago.getIdOrdenVta(), pago);
        return pago;
    }
    
    @Override
    public Optional<PagoVenta> findByOrderId(String idOrder) {
        return Optional.ofNullable(pagos.get(idOrder));
    }
    
    @Override
    public List<PagoVenta> findAll() {
        return new ArrayList<>(pagos.values());
    }
    
    @Override
    public boolean deleteByOrderId(String idOrder) {
        return pagos.remove(idOrder) != null;
    }
    
    @Override
    public boolean update(PagoVenta pago) {
        if (pagos.containsKey(pago.getIdOrdenVta())) {
            pagos.put(pago.getIdOrdenVta(), pago);
            return true;
        }
        return false;
    }
	
}