package mx.com.mxds.gstvent.apiweb;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import mx.com.mxds.gstvent.entidades.Venta;

@RestController
public class ApiVentas {
	
	@Autowired
	IServicioVentas servicioVtas;

	public Venta getVentaXOrden(String idOrden) {
		Venta vta = this.servicioVtas.obtenerVenta(idOrden);
		return vta;
	}

}
