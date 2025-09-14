package mx.com.mxds.gstvent.apiweb;

import mx.com.mxds.gstvent.entidades.Venta;

class VentaResponse {
	private final Venta venta;
	private final String mensaje;

	public VentaResponse(String mensaje, Venta venta) {
		this.mensaje = mensaje;
		this.venta = venta;
	} 
	
	// Getters
	public String getMensaje() { return mensaje; }
	public Venta getVenta() { return venta; }
}