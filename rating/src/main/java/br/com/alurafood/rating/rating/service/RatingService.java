package br.com.alurafood.avaliacao.avaliacao.service;

import br.com.alurafood.avaliacao.avaliacao.config.mapper.MapperConfig;
import br.com.alurafood.avaliacao.avaliacao.dto.PaymentDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;

@Service
public class RatingService implements IRatingService {

    public void computingRatingReceivedByQueue(String message) {

        try {
            PaymentDto paymentDto = MapperConfig.convertTo(message, PaymentDto.class);

            String mensagem = String.format("Payment received with ID %s", paymentDto.getId());

            System.out.println(mensagem);

        } catch (JsonProcessingException e) {

            throw new RuntimeException(e);
        }

    }
}
