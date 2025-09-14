package mx.com.mxds.gstvent.messageBroker.rabbitmq;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

public class EmisorTareas extends EmisorRabbitmq {
	private String nombreCola;
	
	public EmisorTareas(String nombreCola) {
		super();
		this.nombreCola = nombreCola;
	}
	
	public String getNombreColaTareas() {
		return nombreCola;
	}
	
	public void setNombreColaTareas(String nombreColaTareas) {
		this.nombreCola = nombreColaTareas;
	}
	
	public void emitirNuevaTarea(String mensaje) {
		ConnectionFactory fabricaConexiones = new ConnectionFactory();
		fabricaConexiones.setHost(this.hostRabbitMQ);
		try(Connection conexion = fabricaConexiones.newConnection();
			Channel canal = conexion.createChannel()	){
			
			//*** queueDeclare(queue, durable, exclusive, autoDelete, arguments)
			//*** basicPublish(exchange, routingKey, props, body)
			canal.queueDeclare(this.nombreCola, true, false, false, null);
			canal.basicPublish("", this.nombreCola, 
					          MessageProperties.PERSISTENT_TEXT_PLAIN, mensaje.getBytes("UTF-8"));
			System.out.println("Tarea enviada: " + mensaje);
		} catch (IOException | TimeoutException e) {
			e.printStackTrace();
		}
	}

	public void generarNuevaTarea(String[] palabrasMensaje) {
		String mensaje = String.join(" ", palabrasMensaje);
		this.emitirNuevaTarea(mensaje);
	}

}
