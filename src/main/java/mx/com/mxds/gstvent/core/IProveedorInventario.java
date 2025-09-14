package mx.com.mxds.gstvent.core;

import java.util.List;

import mx.com.mxds.gstvent.prov.dtos.ArticuloInvDTO;
import mx.com.mxds.gstvent.prov.dtos.ArticuloVtaStockDTO;

public interface IProveedorInventario {
	public List<ArticuloInvDTO> getInventario();
	
	ArticuloInvDTO getArticuloInvXCodigo(String codigo);
	
	boolean actualizarStockVta(ArticuloVtaStockDTO[] artVtas);
}
