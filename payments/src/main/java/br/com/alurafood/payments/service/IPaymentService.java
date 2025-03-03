package br.com.alurafood.payments.service;

import br.com.alurafood.payments.dto.PaymentDto;
import br.com.alurafood.payments.model.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;

public interface IPaymentService {

    Page<PaymentDto> getAll(@PageableDefault(size = 10) Pageable page);
    PaymentDto getById(Long id);
    PaymentDto createPayment(PaymentDto dto);
    PaymentDto updatePayment(Long id, PaymentDto dto);
    void deletePayment(Long id);
    void confirmPayment(Long id);
    void updateStatusOrder(Long id);
}
