package mx.com.mxds.gstvent.servicios;

import java.util.List;
import java.util.Optional;

import mx.com.mxds.gstvent.entidades.ArticuloVenta;

public interface IRepositorioArticuloVenta {
    //ArticuloVenta save(ArticuloVenta articulo);
	Optional<ArticuloVenta> findById(long id);
    List<ArticuloVenta> findAll();
    //boolean deleteById(long id);
    //boolean update(ArticuloVenta articulo);
    boolean updateStock(long idArticulo, int nuevoStock);
    Optional<ArticuloVenta> findByCodigo(String codigo);
}