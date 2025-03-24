package br.com.alurafood.order.order.service;

import br.com.alurafood.order.order.config.mapper.MapperConfig;
import br.com.alurafood.order.order.dto.IOrderDetails;
import br.com.alurafood.order.order.dto.OrderDto;
import br.com.alurafood.order.order.dto.PaymentQueueDto;
import br.com.alurafood.order.order.model.Order;
import br.com.alurafood.order.order.model.Status;
import br.com.alurafood.order.order.repository.IOrderRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
public class OrderService implements IOrderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    private IOrderRepository repository;

    @Autowired
    private ModelMapper mapper;

    public List<OrderDto> getAll() {
        return repository
                .findAll()
                .stream()
                .map(OrderDto::new)
                .toList();
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

        LOGGER.info("Updated status of order: " + orderDto.getId());
    }

    public List<IOrderDetails> reportOrder() {
        return repository.reportOrder();
    }


    public void requestedQueuePaymentConfirm(String message) {

        LOGGER.info(message);

        try {
//            ObjectMapper mapper = new ObjectMapper();
//            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//
//            PaymentQueueDto queueDto = mapper.readValue(message, PaymentQueueDto.class);

            PaymentQueueDto queueDto = MapperConfig.convertTo(message, PaymentQueueDto.class);

            OrderDto orderDto = new OrderDto();
            orderDto.setId(queueDto.getOrderId());
            orderDto.setStatus(Status.PAYD);
            orderDto.setDatetime(LocalDateTime.now());
            orderDto.setMsg(message);

            updateStatus(orderDto);

        } catch (Exception e) {
            LOGGER.error(e.getMessage(),"Error to catch the message on queue %s");
            throw new RuntimeException(e);
        }

    }

}
