package mx.com.mxds.gstvent.entidades;

import java.math.BigDecimal;
import java.util.Objects;

public class ArticuloVenta {
	private long idArticulo;
	private String codigo;
	private String descripcion;
	private BigDecimal precioBase;
	private int stock;
	
	public ArticuloVenta() {}

	public ArticuloVenta(long idArticulo, String codigo, String descripcion, BigDecimal precioBase, int stock) {
		super();
		this.idArticulo = idArticulo;
		this.codigo = codigo;
		this.descripcion = descripcion;
		this.precioBase = precioBase;
		this.stock = stock;
	}

	public Long getIdArticulo() {
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

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public BigDecimal getPrecioBase() {
		return precioBase;
	}

	public void setPrecioBase(BigDecimal precioBase) {
		this.precioBase = precioBase;
	}
	
	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	@Override
	public String toString() {
		return "ArticuloVenta [idArticulo=" + idArticulo + ", codigo=" + codigo + ", descripcion=" + descripcion
				+ ", precioBase=" + precioBase + ", stock=" + stock + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(codigo, descripcion, idArticulo, precioBase, stock);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ArticuloVenta other = (ArticuloVenta) obj;
		return Objects.equals(codigo, other.codigo) && Objects.equals(descripcion, other.descripcion)
				&& idArticulo == other.idArticulo && Objects.equals(precioBase, other.precioBase)
				&& stock == other.stock;
	}

}
