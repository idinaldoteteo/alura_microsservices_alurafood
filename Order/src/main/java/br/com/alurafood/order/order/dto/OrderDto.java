package br.com.alurafood.order.order.dto;

import br.com.alurafood.order.order.model.Order;
import br.com.alurafood.order.order.model.OrderItem;
import br.com.alurafood.order.order.model.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {

    private Long id;
    private LocalDateTime datetime;
    private Status status;
    private String msg;
    private List<OrderItemDto> itens = new ArrayList<>();

    public OrderDto(Order order) {
    }
}
