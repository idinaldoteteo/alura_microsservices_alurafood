package br.com.alurafood.order.order.service;

import br.com.alurafood.order.order.dto.OrderDto;
import br.com.alurafood.order.order.model.Order;
import br.com.alurafood.order.order.model.Status;
import br.com.alurafood.order.order.repository.IOrderRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService implements IOrderService {

    @Autowired
    private IOrderRepository repository;

    @Autowired
    private ModelMapper mapper;

    public List<OrderDto> getAll() {
        return repository
                .findAll()
                .stream()
                .map(p -> mapper.map(p, OrderDto.class))
                .collect(Collectors.toList());
    }

    public OrderDto getById(Long id){
        Optional<Order> order = repository.findById(id);

        if(order.isPresent()){
            return mapper.map(order.get(), OrderDto.class);
        }

        return null;
    }

    public OrderDto create(OrderDto orderDto) {
        Order order = mapper.map(orderDto, Order.class);

        order.setDatetime(LocalDateTime.now());
        order.setStatus(Status.REALIZED);
        Order finalOrder = order;
        order.getItens().forEach(item -> item.setOrder(finalOrder));

        order = repository.save(order);

        return mapper.map(order, OrderDto.class);
    }

    public void updateStatus(OrderDto orderDto) {
        repository.save(mapper.map(orderDto, Order.class));
    }

}
