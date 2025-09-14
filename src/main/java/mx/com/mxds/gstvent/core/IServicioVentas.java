package mx.com.mxds.gstvent.core;

import java.math.BigDecimal;
import java.util.List;

import mx.com.mxds.gstvent.entidades.DetOrdVenta;
import mx.com.mxds.gstvent.entidades.PagoVenta;
import mx.com.mxds.gstvent.entidades.Venta;
import mx.com.mxds.gstvent.servicios.IRepositorioVenta;

public interface IServicioVentas {

	Venta crearVenta(Venta venta, List<DetOrdVenta> detalles, PagoVenta pago);
	Venta obtenerVenta(String idOrden);
	List<Venta> obtenerListaVentas();
	boolean cancelarVenta(String idOrder);
	BigDecimal calcularTotalVenta(String idOrder);
	BigDecimal calcularSubTotalVenta(String idOrder);
	void setRepoVenta(IRepositorioVenta repoVentas);

}
