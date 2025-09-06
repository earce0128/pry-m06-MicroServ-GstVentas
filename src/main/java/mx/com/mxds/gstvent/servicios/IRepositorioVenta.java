package mx.com.mxds.gstvent.servicios;
import java.util.List;
import java.util.Optional;

import mx.com.mxds.gstvent.entidades.Venta;

// Interface para el repositorio de Venta
public interface IRepositorioVenta {
    Venta save(Venta venta);
    Optional<Venta> findById(String id);
    List<Venta> findAll();
    boolean deleteById(String id);
    boolean update(Venta venta);
}

// Interface para el repositorio de DetOrdVenta
