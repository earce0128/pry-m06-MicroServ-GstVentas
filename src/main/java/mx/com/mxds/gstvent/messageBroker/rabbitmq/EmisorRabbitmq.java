package mx.com.mxds.gstvent.messageBroker.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmisorRabbitmq{
	private static Logger bitacora = LoggerFactory.getLogger(EmisorRabbitmq.class); 
	protected String hostRabbitMQ;
	
	public EmisorRabbitmq() {
		super();
		this.hostRabbitMQ = "localhost";
	}
	public static EmisorRabbitmq getEmisorTareas(String hostRabbitmq, String nomColaTareas) {
		EmisorTareas emisorTarea = new EmisorTareas(nomColaTareas);
		emisorTarea.hostRabbitMQ = hostRabbitmq;
		return emisorTarea;
	}
	
	public static EmisorNotificacion getEmisorNotificacion(String hostRabbitmq, String nomIntermediario) {
		EmisorNotificacion emisorTarea = new EmisorNotificacion(nomIntermediario);
		emisorTarea.hostRabbitMQ = hostRabbitmq;
		bitacora.info("EmisorNotificacion(" + hostRabbitmq + ", " + nomIntermediario + ") instanciado");
		return emisorTarea;
	}
	
	public String getHostRabbitMQ() {
		return hostRabbitMQ;
	}
	public void setHostRabbitMQ(String hostRabbitMQ) {
		this.hostRabbitMQ = hostRabbitMQ;
	}

}
