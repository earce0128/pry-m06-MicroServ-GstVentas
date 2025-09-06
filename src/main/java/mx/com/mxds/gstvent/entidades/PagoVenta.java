package mx.com.mxds.gstvent.entidades;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class PagoVenta {
	
	private String idOrdenVta;
	private LocalDate fechaPago;
	private String tipoPago;
	private BigDecimal monto;
	
	public PagoVenta() {}

	public PagoVenta(String idOrdenVta, LocalDate fechaPago, String tipoPago, BigDecimal monto) {
		super();
		this.idOrdenVta = idOrdenVta;
		this.fechaPago = fechaPago;
		this.tipoPago = tipoPago;
		this.monto = monto;
	}

	public String getIdOrdenVta() {
		return idOrdenVta;
	}

	public void setIdOrdenVta(String idOrdenVta) {
		this.idOrdenVta = idOrdenVta;
	}

	public LocalDate getFechaPago() {
		return fechaPago;
	}

	public void setFechaPago(LocalDate fechaPago) {
		this.fechaPago = fechaPago;
	}

	public String getTipoPago() {
		return tipoPago;
	}

	public void setTipoPago(String tipoPago) {
		this.tipoPago = tipoPago;
	}

	public BigDecimal getMonto() {
		return monto;
	}

	public void setMonto(BigDecimal monto) {
		this.monto = monto;
	}

	@Override
	public String toString() {
		return "PagoVenta [idOrdenVta=" + idOrdenVta + ", fechaPago=" + fechaPago + ", tipoPago=" + tipoPago
				+ ", monto=" + monto + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(fechaPago, idOrdenVta, monto, tipoPago);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PagoVenta other = (PagoVenta) obj;
		return Objects.equals(fechaPago, other.fechaPago) && Objects.equals(idOrdenVta, other.idOrdenVta)
				&& Objects.equals(monto, other.monto) && Objects.equals(tipoPago, other.tipoPago);
	}
	
}
