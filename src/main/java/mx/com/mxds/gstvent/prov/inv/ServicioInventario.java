package mx.com.mxds.gstvent.prov.inv;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.com.mxds.gstvent.core.IProveedorInventario;
import mx.com.mxds.gstvent.prov.dtos.ArticuloInvDTO;
import mx.com.mxds.gstvent.prov.dtos.ArticuloVtaStockDTO;

@Service
public class ServicioInventario {
	
	@Autowired
	IProveedorInventario provInv; 
	
	private static Logger bitacora = LoggerFactory.getLogger(ServicioInventario.class);
	
	public ServicioInventario() {
		bitacora.info("SrvicioInventario()");
	}

	public List<ArticuloInvDTO> obtenerInventario() {
		bitacora.info("obtenerInventario()");
		return provInv.getInventario();
	}
	
	public ArticuloInvDTO obtenerArticuloXCodigo(String codigo) {
		bitacora.info("obtenerArticuloXCodigo(" + codigo + ")");
		return provInv.getArticuloInvXCodigo(codigo);
	}
	
	public boolean actualizarStocInv(ArticuloVtaStockDTO[] artVtas) {
		bitacora.info("actualizarStocInv(" + artVtas + ")");
		return provInv.actualizarStockVta(artVtas);
	}
	
}
