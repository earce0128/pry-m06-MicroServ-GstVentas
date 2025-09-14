package mx.com.mxds.gstvent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;

import mx.com.mxds.gstvent.core.IPublicadorNotificaciones;
import mx.com.mxds.gstvent.messageBroker.rabbitmq.EmisorRabbitmq;


@SpringBootApplication
public class Prym06MicroGstVentasApplication {

	public static void main(String[] args) {
		SpringApplication.run(Prym06MicroGstVentasApplication.class, args);
	}
	
	@Bean
	RestTemplate obtenerRestTemplate(){
		return new RestTemplate();
	}
	
	@Bean
	IPublicadorNotificaciones getPublicadorNotificaciones(Environment env){
		return EmisorRabbitmq.getEmisorNotificacion(
				env.getProperty("mxds.progVentasService.messageBroker.host", "localhost"),
				env.getProperty("mxds.progVentasService.messageBroker.exchangeVtas", "exchangeDefault")
				);
	}

}
