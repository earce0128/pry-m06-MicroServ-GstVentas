package mx.com.mxds.gstvent.prov.inv;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import mx.com.mxds.gstvent.core.IProveedorInventario;
import mx.com.mxds.gstvent.prov.dtos.ArticuloInvDTO;
import mx.com.mxds.gstvent.prov.dtos.ArticuloVtaStockDTO;

@Component
public class ProvInventarioMicro implements IProveedorInventario {
	
	@Autowired
	RestTemplate restTemplate;
	
	private static Logger bitacora = LoggerFactory.getLogger(ProvInventarioMicro.class);
	
	public final static String URI_MICRO_INV_X_CODIGO = "http://localhost:8081/inventario/{codigo}";
	public final static String URI_MICRO_INV_LISTA = "http://localhost:8081/inventario";
	public final static String URI_MICRO_INV_STOCK = "http://localhost:8081/inventario/";

	@Override
	public List<ArticuloInvDTO> getInventario() {
		try {
			ArticuloInvDTO[] artInv = 
					restTemplate.getForObject(URI_MICRO_INV_LISTA, ArticuloInvDTO[].class);
			return List.of(artInv);
		} catch(RestClientResponseException rcrex) {
			String errorDevuelto = rcrex.getResponseBodyAsString();
			bitacora.error("Invocacion de GET " + URI_MICRO_INV_LISTA +  " ha fallado");
			bitacora.error("error devuelto:" + errorDevuelto);
			return new ArrayList<>();
		}
	}

	@Override
	public ArticuloInvDTO getArticuloInvXCodigo(String codigo) {
		try {
			ArticuloInvDTO artInv = restTemplate.getForObject(URI_MICRO_INV_X_CODIGO, ArticuloInvDTO.class, codigo);
			return artInv;
		} catch(RestClientResponseException rcrex) {
			String errorDevuelto = rcrex.getResponseBodyAsString();
			bitacora.error("Invocacion de GET " + URI_MICRO_INV_X_CODIGO +  " ha fallado con codigo = " + codigo);
			bitacora.error("error devuelto:" + errorDevuelto);
			return null;
		}
	}

	@Override
	public boolean actualizarStockVta(ArticuloVtaStockDTO[] artVtas) {
		try {
			restTemplate.put(URI_MICRO_INV_STOCK, artVtas, Boolean.class);
			return true;
		} catch(RestClientResponseException rcrex) {
			String errorDevuelto = rcrex.getResponseBodyAsString();
			bitacora.error("Invocacion de PUT " + URI_MICRO_INV_STOCK + " ha fallado");
			bitacora.error("error devuelto:" + errorDevuelto);
		}
		return false;
	}

}
