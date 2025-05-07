package br.com.nttdata.nttskillboost.activitymanagementservice.infrastructure.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    //@Value("${progress.queue.name}")
    //String queueName;

    public static final String QUEUE_NAME = "progress-queue";
    public static final String EXCHANGE_NAME = QUEUE_NAME + ".exchange";
    public static final String ROUTING_KEY = QUEUE_NAME + ".routing.key" ;

    @Bean
    public Queue commandCreateClientQueue() {
        return new Queue(QUEUE_NAME, true); // durable = true
    }

    @Bean
    public DirectExchange commandExchange() {
        return new DirectExchange(EXCHANGE_NAME , true, false); // durable = true, auto-delete = false
    }

    @Bean
    public Binding binding(Queue commandCreateClientQueue, DirectExchange commandExchange) {
        return BindingBuilder
                .bind(commandCreateClientQueue)
                .to(commandExchange)
                .with(ROUTING_KEY);
    }

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory,
                                                                               Jackson2JsonMessageConverter converter) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(converter);
        return factory;
    }
}
