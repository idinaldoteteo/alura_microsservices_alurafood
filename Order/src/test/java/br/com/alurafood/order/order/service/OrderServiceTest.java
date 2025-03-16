package br.com.alurafood.order.order.service;

import br.com.alurafood.order.order.dto.OrderDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Tag("First-Test")
class OrderServiceTest {

    @Autowired
    OrderService orderService;

    @Test
    @DisplayName("Need to return all elements")
    void getAll_needToReturnTenElements() {

        //Arrange
		List<OrderDto> orders = orderService.getAll();

		//Act

		//Assert
		Assertions.assertEquals(5, orders.size());
    }
}