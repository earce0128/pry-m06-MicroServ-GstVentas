package mx.com.mxds.gstvent.servicios;

import java.util.List;
import java.util.Optional;

import mx.com.mxds.gstvent.entidades.PagoVenta;

public interface IRepositorioPagoVenta {
    PagoVenta save(PagoVenta pago);
    Optional<PagoVenta> findByOrderId(String idOrder);
    List<PagoVenta> findAll();
    boolean deleteByOrderId(String idOrder);
    boolean update(PagoVenta pago);
}