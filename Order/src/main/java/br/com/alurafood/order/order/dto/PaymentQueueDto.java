package br.com.alurafood.order.order.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentQueueDto {

    private Long orderId;
    private String msg;
}
