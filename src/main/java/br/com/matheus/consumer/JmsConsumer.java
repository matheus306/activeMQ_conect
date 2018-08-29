package br.com.matheus.consumer;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import br.com.matheus.entity.DadosEntrada;

/**
 * @author Matheus Melo
 */
@Component
public class JmsConsumer {

	/**
	 * @param msg
	 * @author Matheus Melo
	 */
	@JmsListener(destination = "${activemq.queue}", containerFactory="jsaFactory")
	public void receive(DadosEntrada msg) {
		System.out.println(msg);
	}
}
