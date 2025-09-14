package mx.com.mxds.gstvent.apiweb;

import java.util.List;

class ErrorResponse {
	
	private final String error;
	private final List<String> detalles;
	
	public ErrorResponse(String error, List<String> detalles) {
		this.error = error;
		this.detalles = detalles;
	}

	public String getError() {
		return error;
	}

	public List<String> getDetalles() {
		return detalles;
	}
	
}
