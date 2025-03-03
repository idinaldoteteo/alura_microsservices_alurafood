package br.com.alurafood.payments.dto;

import br.com.alurafood.payments.model.Status;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PaymentDto {

    private Long id;
    private BigDecimal amount;
    private String name;
    private String number;
    private String expiration;
    private String code;
    private Status status;
    private Long orderId;
    private Long typePaymentId;
    private OrderDto order;
}
