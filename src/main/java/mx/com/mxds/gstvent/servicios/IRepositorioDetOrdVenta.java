package mx.com.mxds.gstvent.servicios;

import java.util.List;
import java.util.Optional;

import mx.com.mxds.gstvent.entidades.DetOrdVenta;

public interface IRepositorioDetOrdVenta {
    DetOrdVenta save(DetOrdVenta detalle);
    DetOrdVenta save(String idOrden, DetOrdVenta detalle);
    Optional<DetOrdVenta> findById(String idOrden, long idArticulo);
    List<DetOrdVenta> findByOrden(String idOrden);
    List<DetOrdVenta> findAll();
    boolean deleteById(String idOrden, long idArticulo);
    boolean update(DetOrdVenta detalle);
}

// Interface para el repositorio de ArticuloVenta
