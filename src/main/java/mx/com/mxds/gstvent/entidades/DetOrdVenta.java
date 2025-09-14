package mx.com.mxds.gstvent.entidades;

import java.math.BigDecimal;
import java.util.Objects;

public class DetOrdVenta {
	
	private String idOrden;
	private long idArticulo;
	private String codigo; 
	private int cantidad;
	private String descripcionArt;
	private BigDecimal precioBase;
	//private ArticuloVenta articulo;
	
	public DetOrdVenta() {	}

	public DetOrdVenta(String idOrden, ArticuloVenta articulo, int cantidad) {
		super();
		this.idOrden = idOrden;
		this.idArticulo = articulo.getIdArticulo();
		this.codigo = articulo.getCodigo();
		this.descripcionArt = articulo.getDescripcion();
		this.cantidad = cantidad;
		this.precioBase = articulo.getPrecioBase();
	}
	
	public String getDescripcionArt() {
		return descripcionArt;
	}

	public void setDescripcionArt(String descripcionArt) {
		this.descripcionArt = descripcionArt;
	}

	public BigDecimal getPrecioBase() {
		return precioBase;
	}

	public void setPrecioBase(BigDecimal precioBase) {
		this.precioBase = precioBase;
	}

	public String getIdOrden() {
		return idOrden;
	}

	public void setIdOrden(String idOrden) {
		this.idOrden = idOrden;
	}

	public long getIdArticulo() {
		return idArticulo;
	}

	public void setIdArticulo(long idArticulo) {
		this.idArticulo = idArticulo;
	}
	
	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	
	@Override
	public String toString() {
		return "DetOrdVenta [idOrden=" + idOrden + ", idArticulo=" + idArticulo + ", codigo=" + codigo + ", cantidad="
				+ cantidad + ", descripcionArt=" + descripcionArt + ", precioBase=" + precioBase + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(cantidad, codigo, descripcionArt, idArticulo, idOrden, precioBase);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DetOrdVenta other = (DetOrdVenta) obj;
		return cantidad == other.cantidad
				&& Objects.equals(codigo, other.codigo) && Objects.equals(descripcionArt, other.descripcionArt)
				&& idArticulo == other.idArticulo && Objects.equals(idOrden, other.idOrden)
				&& Objects.equals(precioBase, other.precioBase);
	}

}
