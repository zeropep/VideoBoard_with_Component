package com.videoboard.boot.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RabbitMQConfig {
//	
//	@Value("${EXCHANGE_NAME}")
//	private String EXCHANGE_NAME;
//	@Value("${QUEUE_NAME}")
//	private String QUEUE_NAME;
//	@Value("${ROUTING_KEY}")
//	private String ROUTING_KEY;
//	
//	@Bean
//	TopicExchange exchange() {
//		return new TopicExchange(EXCHANGE_NAME);
//	}
//	
//	@Bean
//	Queue queue() {
//		return new Queue(QUEUE_NAME);
//	}
//	
//	@Bean
//	Binding binding (Queue queue, TopicExchange exchange) {
//		return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
//	}
//	
//	@Bean
//	RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, MessageConverter converter) {
//		RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
//		rabbitTemplate.setMessageConverter(converter);
//		return rabbitTemplate;
//	}
}
