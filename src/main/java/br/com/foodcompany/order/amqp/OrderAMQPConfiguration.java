package br.com.foodcompany.order.amqp;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderAMQPConfiguration {

    @Value("${spring.rabbitmq.queue-name}")
    private String queueName;

    @Value("${spring.rabbitmq.fanout-exchange-name}")
    private String exchangeName;

    @Value("${spring.rabbitmq.deadletterexchange-name}")
    private String dlxExchangeName;

    @Value("${spring.rabbitmq.deadletterqueue-name}")
    private String dlQueueName;

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        var rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jackson2JsonMessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    // Definindo a fila principal com configuração de Dead Letter
    @Bean
    public Queue mainQueue() {
        return QueueBuilder.nonDurable(queueName)
                .deadLetterExchange(dlxExchangeName)  // Define o DLX para a fila principal
                .build();
    }

    // Exchange principal
    @Bean
    public FanoutExchange mainExchange() {
        return ExchangeBuilder.fanoutExchange(exchangeName)
                .build();
    }

    // Binding da fila principal à exchange principal
    @Bean
    public Binding mainQueueBinding(Queue mainQueue, FanoutExchange mainExchange) {
        return BindingBuilder.bind(mainQueue)
                .to(mainExchange);
    }

    // Definindo o Dead Letter Exchange (DLX)
    @Bean
    public FanoutExchange deadLetterExchange() {
        return ExchangeBuilder.fanoutExchange(dlxExchangeName)
                .build();
    }

    // Definindo a Dead Letter Queue (DLQ)
    @Bean
    public Queue deadLetterQueue() {
        return QueueBuilder.nonDurable(dlQueueName)
                .build();
    }

    // Binding da DLQ ao DLX
    @Bean
    public Binding deadLetterQueueBinding(Queue deadLetterQueue, FanoutExchange deadLetterExchange) {
        return BindingBuilder.bind(deadLetterQueue)
                .to(deadLetterExchange);
    }

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

}
