package mx.com.mxds.gstvent.apiweb;

import java.math.BigDecimal;
import java.util.List;

import mx.com.mxds.gstvent.entidades.DetOrdVenta;
import mx.com.mxds.gstvent.entidades.PagoVenta;
import mx.com.mxds.gstvent.entidades.Venta;
import mx.com.mxds.gstvent.servicios.IRepositorioVenta;

public interface IServicioVentas {

	Venta crearVenta(Venta venta, List<DetOrdVenta> detalles, PagoVenta pago);
	Venta obtenerVenta(String idOrden);
	boolean cancelarVenta(String idOrder);
	BigDecimal calcularTotalVenta(String idOrder);
	void setRepoVenta(IRepositorioVenta repoVentas);

}
