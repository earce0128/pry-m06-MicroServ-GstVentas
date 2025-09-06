package mx.com.mxds.gstvent.entidades;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Venta {
	private String idOrden;
	private LocalDate fechaVenta;
	private Long idVendedor;
	private String nombreVendedor;
	private String status;
	private BigDecimal subtotal;
	private double iva;
	private BigDecimal total;
	List<DetOrdVenta> detArticulos;
	PagoVenta pago;
	
	public Venta() {
		detArticulos = new ArrayList<>();
	}
	
	public Venta(String idOrden, LocalDate fechaVenta, Long idVendedor, String nombreVendedor, String status,
			BigDecimal subtotal, double iva, BigDecimal total) {
		super();
		this.idOrden = idOrden;
		this.fechaVenta = fechaVenta;
		this.idVendedor = idVendedor;
		this.nombreVendedor = nombreVendedor;
		this.status = status;
		this.subtotal = subtotal;
		this.iva = iva;
		this.total = total;
		this.detArticulos = new ArrayList<>();
	}

	public String getIdOrden() {
		return idOrden;
	}

	public void setIdOrden(String idOrden) {
		this.idOrden = idOrden;
	}

	public LocalDate getFechaVenta() {
		return fechaVenta;
	}

	public void setFechaVenta(LocalDate fechaVenta) {
		this.fechaVenta = fechaVenta;
	}

	public Long getIdVendedor() {
		return idVendedor;
	}

	public void setIdVendedor(Long idVendedor) {
		this.idVendedor = idVendedor;
	}

	public String getNombreVendedor() {
		return nombreVendedor;
	}

	public void setNombreVendedor(String nombreVendedor) {
		this.nombreVendedor = nombreVendedor;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public BigDecimal getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(BigDecimal subtotal) {
		this.subtotal = subtotal;
	}

	public double getIva() {
		return iva;
	}

	public void setIva(double iva) {
		this.iva = iva;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public List<DetOrdVenta> getDetArticulos() {
		return detArticulos;
	}

	public void setDetArticulos(List<DetOrdVenta> detArticulos) {
		this.detArticulos = detArticulos;
	}

	public PagoVenta getPago() {
		return pago;
	}

	public void setPago(PagoVenta pago) {
		this.pago = pago;
	}

	@Override
	public String toString() {
		return "Venta [idOrden=" + idOrden + ", fechaVenta=" + fechaVenta + ", idVendedor=" + idVendedor
				+ ", nombreVendedor=" + nombreVendedor + ", status=" + status + ", subtotal=" + subtotal + ", iva="
				+ iva + ", total=" + total + ", detArticulos=" + detArticulos + ", pago=" + pago + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(detArticulos, fechaVenta, idOrden, idVendedor, iva, nombreVendedor, pago, status, subtotal,
				total);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Venta other = (Venta) obj;
		return Objects.equals(detArticulos, other.detArticulos) && Objects.equals(fechaVenta, other.fechaVenta)
				&& Objects.equals(idOrden, other.idOrden) && Objects.equals(idVendedor, other.idVendedor)
				&& Double.doubleToLongBits(iva) == Double.doubleToLongBits(other.iva)
				&& Objects.equals(nombreVendedor, other.nombreVendedor) && Objects.equals(pago, other.pago)
				&& Objects.equals(status, other.status) && Objects.equals(subtotal, other.subtotal)
				&& Objects.equals(total, other.total);
	}

	
}
