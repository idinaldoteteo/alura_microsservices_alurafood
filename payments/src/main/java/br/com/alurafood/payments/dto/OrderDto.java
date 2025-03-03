package br.com.alurafood.payments.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class OrderDto {

    public Long id;
    public LocalDateTime datetime;
    public List<OrderItemDto> itens = new ArrayList<>();
}
