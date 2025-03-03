package br.com.alurafood.payments.http;

import br.com.alurafood.payments.dto.OrderDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("order-ms")
public interface IOrderClient {

    @RequestMapping(method = RequestMethod.PUT, value = "/order/{id}/{status}" )
    void updateSatusOrder(@PathVariable() Long id, @PathVariable() String status);

    @RequestMapping(method = RequestMethod.GET, value = "/order/{id}")
    OrderDto getOrderByPaymentId(@PathVariable Long id);
}
