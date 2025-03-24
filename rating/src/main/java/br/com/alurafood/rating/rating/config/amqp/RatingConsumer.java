package br.com.alurafood.avaliacao.avaliacao.config.amqp;

import br.com.alurafood.avaliacao.avaliacao.service.IRatingService;
import br.com.alurafood.avaliacao.avaliacao.service.RatingService;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class RatingConsumer {

    @Value("${exchange.payment.confirmation}")
    private String exchange;

    @Value("${queue.rating.payment.confirmation}")
    private String queue;

    @Autowired
    private IRatingService ratingService;

    @RabbitListener(queues = "${queue.rating.payment.confirmation}")
    public void queue(String message) {
        ratingService.computingRatingReceivedByQueue(message);
    }

    @Bean
    public Queue queue(){
        return new Queue(queue);
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
