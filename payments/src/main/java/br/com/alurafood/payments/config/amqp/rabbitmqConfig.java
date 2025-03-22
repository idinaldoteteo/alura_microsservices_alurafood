package br.com.alurafood.payments.config.amqp;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class rabbitmqConfig {

    @Value("${queue.name.payment.confirmation}")
    private String paymentQueue;

    @Bean
    public Queue queue (){
        return new Queue(paymentQueue, true);
    }
}
