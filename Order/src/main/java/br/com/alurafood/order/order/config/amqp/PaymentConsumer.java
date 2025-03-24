package br.com.alurafood.order.order.config.amqp;

import br.com.alurafood.order.order.service.IOrderService;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class PaymentConsumer {

    @Value("${spring.application.name}")
    private String nameApplicaiton;

    @Value("${exchange.payment.confirmation}")
    private String exchange;

    @Value("${queue.payment.confirmation}")
    private String queue;

    @Autowired
    private IOrderService orderService;

    @RabbitListener(queues = "${queue.payment.confirmation}", batch = "5")
    public void queue(String message){
        orderService.requestedQueuePaymentConfirm(message);
    }

    @Bean
    public Queue queue(){
        return QueueBuilder
                .durable(queue)
                .build();
    }

    @Bean
    public FanoutExchange exchange(){
        return new FanoutExchange(exchange);
    }

    @Bean
    public Binding bindingPaymentConfirmation() {
        return BindingBuilder
                .bind(queue())
                .to(exchange());
    }

}
