package mx.com.mxds.gstvent.apiweb;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.validation.Valid;
import mx.com.mxds.gstvent.core.IPublicadorNotificaciones;
import mx.com.mxds.gstvent.core.IServicioVentas;
import mx.com.mxds.gstvent.entidades.PagoVenta;
import mx.com.mxds.gstvent.entidades.Venta;
import mx.com.mxds.gstvent.prov.dtos.ArticuloInvDTO;
import mx.com.mxds.gstvent.prov.dtos.ArticuloVtaStockDTO;
import mx.com.mxds.gstvent.prov.inv.ServicioInventario;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
public class ApiVentas {
	
	@Autowired
	IServicioVentas servicioVtas;
	
	@Autowired
	ServicioInventario serInv;
	
	@Autowired
	private IPublicadorNotificaciones notificador;
	
	private static Logger bitacora = LoggerFactory.getLogger(ApiVentas.class);

	@GetMapping(path="/ventas/{idOrden}", produces=MediaType.APPLICATION_JSON_VALUE)
	public Venta getVentaXOrden(@PathVariable String idOrden) {
		Venta vta = this.servicioVtas.obtenerVenta(idOrden);
		return vta;
	}
	
	// Usado para manipular los headers de la respuesta
	@GetMapping(path="/ventas/test/{idOrden}", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Venta> getRespVentaXOrden(@PathVariable String idOrden) {
		Venta vta = this.servicioVtas.obtenerVenta(idOrden);
		Date ahora = new Date();
		ResponseEntity<Venta> respEntity = 
			ResponseEntity.ok()
						  .header("LastModified", ahora.getTime() + "")
						  .body(vta);
		return respEntity;
	}
	
	@GetMapping(path="/ventas", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Venta> getEventos() {
		List<Venta> lstVentas =  this.servicioVtas.obtenerListaVentas();
		return lstVentas;
	}
	
	@PostMapping(path="/ventas/anterior" , consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Venta insertarVenta(@RequestBody Venta nvaVenta) {
		bitacora.info("Venta = [" + nvaVenta + "]");
		Venta ventaInsertada = this.servicioVtas.crearVenta(nvaVenta, nvaVenta.getDetArticulos(), nvaVenta.getPago());
		bitacora.info("Venta = [" + ventaInsertada + "]");
		return ventaInsertada;
	}
	
	@PostMapping(path="/ventas/pagos", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Venta insertarPago(@RequestBody PagoVenta pago) {
		bitacora.info("Venta = [" + pago + "]");
		Venta ventaPago = this.servicioVtas.obtenerVenta(pago.getIdOrdenVta());
		ventaPago.setPago(pago);
		ventaPago.setStatus("PAGADA");
		return ventaPago;
	}
	
	@PostMapping(path="/ventas" , consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<VentaResponse> crearVenta(@Valid @RequestBody Venta nuevaVenta) {
		
		Venta ventaGuardada = this.servicioVtas.crearVenta(nuevaVenta,nuevaVenta.getDetArticulos(), nuevaVenta.getPago());
		return ResponseEntity.status(HttpStatus.CREATED)
							 .body(new VentaResponse("Orden de venta creada exitosamente", ventaGuardada));
	}
	
	// Llamados a microservicio GstInventario
	
	@GetMapping(path="/ventas/testInv", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<ArticuloInvDTO> getInventario(){
		return this.serInv.obtenerInventario();
	}
	
	@GetMapping(path="/ventas/testInv/{codigo}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ArticuloInvDTO getArtXCodigo(@PathVariable String codigo){
		return this.serInv.obtenerArticuloXCodigo(codigo);
	}
	
	@PutMapping(path="/ventas/testInv/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public boolean actualizarStocInv(@RequestBody ArticuloVtaStockDTO[] vtasStock) {
		bitacora.debug("ArtStock = [" + vtasStock[0] + "]");
		
		if(this.serInv.actualizarStocInv(vtasStock)) return true;
		return false;
	}
	
	@GetMapping(path = "/ventas/ordenVta", produces = MediaType.TEXT_PLAIN_VALUE)
	public String getUiOrdenVta() {
		
		bitacora.info("getUiOrdenVta()");
		
		// Coreografía: enviar eventoProgramado a MessageBroker	
		String objJsonVenta = getEventoJSon();
		this.notificador.emitirNotificacion(objJsonVenta);
		
		return objJsonVenta;
	}
	
	private String getEventoJSon() {
		JsonObjectBuilder evtoProgJsonBuilder = Json.createObjectBuilder();
		JsonObject evtoJson = evtoProgJsonBuilder
			.add("idOrden", "v-6")
			.add("fechaVenta", "2025-09-13")
			.add("idVendedor", 2)
			.add("nombreVendedor", "Juan Perez")
			.add("status", "PAGADA")
			.add("subtotal", 25695)
			.add("iva", 0.16)
			.add("total", 29806.20)
			.add("detArticulos",Json.createArrayBuilder()
				.add(Json.createObjectBuilder()
					.add("idOrden","v-6")
					.add("idArticulo", 10)
					.add("codigo","CAM-010")
					.add("cantidad", 3)
					.add("descripcion", "Cámara Web Logitech C920")
					.add("precioBase", 1799.00)
				.build())
				.add(Json.createObjectBuilder()
					.add("idOrden","v-6")
					.add("idArticulo", 5)
					.add("codigo","AUD-005")
					.add("cantidad", 1)
					.add("descripcion", "Audífonos Sony WH-CH510")
					.add("precioBase", 1299.00)
				.build())
			.build())
//			.add("pago",Json.createObjectBuilder()
//				.add("idOrden", "v-6")
//				.add("fechaPago", "v-6")
//				.add("fechaPago", "2025-09-13")
//				.add("tipoPago", "TARJETA")
//				.add("monto", 29806.20)
//			.build())
		.build();
		return evtoJson.toString();
	}

	// Manejador de errores de validación
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> manejarErrorresValidacion(
			MethodArgumentNotValidException ex ) {
		bitacora.warn("manejarErroresValidacion(" + ex.getDetailMessageCode() + ")");
		List<String> errores = ex.getBindingResult()
							.getFieldErrors()
							.stream()
							.map(error -> error.getField() + ": " + error.getDefaultMessage())
							.collect(Collectors.toList());
			
		return ResponseEntity.badRequest()
						.body(new ErrorResponse("Error de validación", errores));
	}
		
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> manejarOtrosErrorres(
			Exception ex ) {
		bitacora.warn("manejarOtrosErrorres(" + ex.getMessage() + ")");
			
		return ResponseEntity.badRequest()
				.body(new ErrorResponse("Error inesperado [" + ex.getClass().getName() + "]", 
						List.of(ex.getMessage())));
	}
	
}
