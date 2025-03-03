package br.com.alurafood.order.order.controller;

import br.com.alurafood.order.order.dto.OrderDto;
import br.com.alurafood.order.order.model.Status;
import br.com.alurafood.order.order.service.IOrderService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private IOrderService orderService;

    @GetMapping
    public List<OrderDto> getAll(){
        return orderService.getAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<OrderDto> getById(@PathVariable @NotNull Long id){
        OrderDto order = orderService.getById(id);

        if(order == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(order);
    }

    @PostMapping
    public ResponseEntity<OrderDto> create(@RequestBody OrderDto order){
        return ResponseEntity.ok(orderService.create(order));
    }

    @GetMapping("/port")
    public ResponseEntity<String> getPort(@Value("${local.server.port}") String port){
        return ResponseEntity.ok(String.format("Requests running on port %s", port));
    }

    @PutMapping("/{id}/{status}")
    public ResponseEntity<String> updateStatus(@PathVariable @NotNull Long id, @PathVariable @NotNull String status){
        OrderDto order = orderService.getById(id);

        if(order == null){
            return ResponseEntity.notFound().build();
        }

        order.setStatus(Status.get(status));
        orderService.updateStatus(order);

        return ResponseEntity.ok("Order status changed to " + status);
    }
}
