package br.com.matheus;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

import br.com.matheus.util.AecMappingJackson2MessageConverter;

/**
 * @author Matheus Melo
 */
@Configuration
public class ActiveMqConnectionFactoryConfig {

	/**
	 * @author Matheus Melo
	 */
	@Value("${activemq.broker.url}")
	String brokerUrl;

	/**
	 * @author Matheus Melo
	 */
	@Value("${activemq.borker.username}")
	String userName;

	/**
	 * @author Matheus Melo
	 */
	@Value("${activemq.borker.password}")
	String password;

	/**
	 * @return {@link ConnectionFactory} - Configuração inicial da conexão ( O nome da fila será anotado no método receptor )
	 * @author Matheus Melo
	 */
	@Bean
	public ConnectionFactory connectionFactory() {
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
		connectionFactory.setBrokerURL(brokerUrl);
		connectionFactory.setUserName(userName);
		connectionFactory.setPassword(password);
		return connectionFactory;
	}

	/**
	 * @return {@link MessageConverter}
	 * @author Matheus Melo
	 */
	@Bean
	public MessageConverter jacksonJmsMessageConverter() {
		AecMappingJackson2MessageConverter converter = new AecMappingJackson2MessageConverter();
		converter.setTargetType(MessageType.TEXT);
		return converter;
	}

	/**
	 * Usado para receber as mensagens
	 * @param connectionFactory
	 * @param configurer
	 * @return {@link JmsListenerContainerFactory}
	 * @author Matheus Melo
	 */
	@Bean
	public JmsListenerContainerFactory<?> jsaFactory(ConnectionFactory connectionFactory, DefaultJmsListenerContainerFactoryConfigurer configurer) {
		DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
		factory.setMessageConverter(jacksonJmsMessageConverter());
		configurer.configure(factory, connectionFactory);
		return factory;
	}

	
	/**
	 * Usado para publicar mensagens
	 * @return JmsTemplate
	 * @author Matheus Melo
	 */
	@Bean
	public JmsTemplate jmsTemplate() {
		JmsTemplate template = new JmsTemplate();
		template.setMessageConverter(jacksonJmsMessageConverter());
		template.setConnectionFactory(connectionFactory());
		return template;
	}
}
