package mx.com.mxds.gstvent.entidades;

import java.util.Objects;

public class DetOrdVenta {
	
	private String idOrden;
	private long idArticulo;
	private int cantidad;
	private ArticuloVenta articulo;
	
	public DetOrdVenta() {	}

	public DetOrdVenta(String idOrden, Long idArticulo, int cantidad) {
		super();
		this.idOrden = idOrden;
		this.idArticulo = idArticulo;
		this.cantidad = cantidad;
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

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	
	public ArticuloVenta getArticulo() {
		return articulo;
	}

	public void setArticulo(ArticuloVenta articulo) {
		this.articulo = articulo;
	}

	@Override
	public String toString() {
		return "DetOrdVenta [idOrden=" + idOrden + ", idArticulo=" + idArticulo + ", cantidad=" + cantidad + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(cantidad, idArticulo, idOrden);
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
		return cantidad == other.cantidad && Objects.equals(idArticulo, other.idArticulo)
				&& Objects.equals(idOrden, other.idOrden);
	}

	
}
