package br.com.alurafood.avaliacao.avaliacao.service;

import br.com.alurafood.avaliacao.avaliacao.dto.PaymentDto;

public interface IRatingService {

    void computingRatingReceivedByQueue(String message);
}
