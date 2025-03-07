package br.com.alurafood.order.order.service;

import br.com.alurafood.order.order.dto.IOrderDetails;
import br.com.alurafood.order.order.dto.OrderDto;

import java.util.List;

public interface IOrderService {

    List<OrderDto> getAll();
    OrderDto getById(Long id);
    OrderDto create(OrderDto orderDto);
    void updateStatus(OrderDto orderDto);
    List<IOrderDetails> reportOrder();
}
