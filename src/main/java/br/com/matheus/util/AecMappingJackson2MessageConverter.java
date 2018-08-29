package br.com.matheus.util;

import javax.jms.JMSException;
import javax.jms.Message;

import org.springframework.jms.support.converter.MappingJackson2MessageConverter;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;

import br.com.matheus.entity.DadosEntrada;

/**
 * @author Matheus Melo
 */
public class AecMappingJackson2MessageConverter extends MappingJackson2MessageConverter {

	/* (non-Javadoc)
	 * @see org.springframework.jms.support.converter.MappingJackson2MessageConverter#getJavaTypeForMessage(javax.jms.Message)
	 */
	protected JavaType getJavaTypeForMessage(Message arg0) throws JMSException {
		return TypeFactory.defaultInstance().constructType(DadosEntrada.class);
	}
}
