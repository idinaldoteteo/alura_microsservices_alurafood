package br.com.alurafood.payments.controller;

import br.com.alurafood.payments.dto.PaymentDto;
import br.com.alurafood.payments.service.IPaymentService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private IPaymentService service;

    @GetMapping
    public Page<PaymentDto> getAll(Pageable pageable) {
        return service.getAll(pageable);
    }

    @GetMapping("{id}")
    public ResponseEntity<PaymentDto> getById(@PathVariable @NotNull Long id) {
        PaymentDto dto = service.getById(id);

        if ( dto == null ) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<PaymentDto> create(@RequestBody @Valid PaymentDto dto, UriComponentsBuilder uriBuilder) {
        PaymentDto payment = service.createPayment(dto);
//        URI uri = uriBuilder.path("/payment/{id}").buildAndExpand(payment.getId()).toUri();

//        return ResponseEntity.created(uri).body(payment);
        return ResponseEntity.ok(payment);
    }

    @PutMapping("{id}")
    public ResponseEntity<PaymentDto> update(@PathVariable @NotNull Long id, @RequestBody @Valid PaymentDto dto) {
        PaymentDto paymentDto = service.updatePayment(id, dto);

        return ResponseEntity.ok(paymentDto);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<PaymentDto> delete(@PathVariable @NotNull Long id) {
        service.deletePayment(id);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/confirm")
    @CircuitBreaker(name = "updateOrder", fallbackMethod = "paymentAllowedWithoutIntegration")
    public void confirm(@PathVariable @NotNull Long id){
        service.confirmPayment(id);
    }

    public void paymentAllowedWithoutIntegration(Long id, Exception e){
        service.updateStatusOrder(id);
    }
}
