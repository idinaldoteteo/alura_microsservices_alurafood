package br.com.alurafood.payments.service;

import br.com.alurafood.payments.dto.PaymentDetailDto;
import br.com.alurafood.payments.dto.PaymentDto;
import br.com.alurafood.payments.http.IOrderClient;
import br.com.alurafood.payments.model.Payment;
import br.com.alurafood.payments.model.Status;
import br.com.alurafood.payments.repository.IPaymentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PaymentService implements IPaymentService {

    @Autowired
    private IPaymentRepository paymentRepository;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private IOrderClient orderClient;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${exchange.payment.confirmation}")
    private String exchange;

    public Page<PaymentDto> getAll(Pageable page) {
        return paymentRepository
                .findAll(page)
                .map(p -> mapper.map(p, PaymentDto.class));
    }

    public PaymentDto getById(Long id) {
        Optional<Payment> payment = paymentRepository.findById(id);

        if (payment.isPresent()) {
            PaymentDto dto = mapper.map(payment, PaymentDto.class);
            return dto;
        }

        return null;
    }

    public PaymentDetailDto getWithDetailsById(Long id) {
        Optional<Payment> payment = paymentRepository.findById(id);

        if (payment.isPresent()) {
            PaymentDetailDto dto = mapper.map(payment, PaymentDetailDto.class);
            dto.setOrder(orderClient.getOrderByPaymentId(dto.getOrderId()));
            return dto;
        }

        return null;
    }

    public PaymentDto createPayment(PaymentDto dto) {
        Payment payment = mapper.map(dto, Payment.class);
        payment.setStatus(Status.CREATED);
        payment = paymentRepository.save(payment);

        // Send message to queue
//        Message message = new Message(("payment requested number " + payment.getId()).getBytes());
        dto.setId(payment.getId());
//        rabbitTemplate.convertAndSend(queueNamePayment, dto); // when I send to directly to queue
        rabbitTemplate.convertAndSend(exchange,"", dto);

        return mapper.map(payment, PaymentDto.class);
    }

    public PaymentDto updatePayment(Long id, PaymentDto dto) {
        Payment payment = mapper.map(dto, Payment.class);
        payment.setId(id);
        payment = paymentRepository.save(payment);

        return mapper.map(payment, PaymentDto.class);
    }

    public void deletePayment(Long id) {
        paymentRepository.deleteById(id);
    }

    public void confirmPayment(Long id){
        Optional<Payment> payment = paymentRepository.findById(id);

        if(!payment.isPresent()){
            throw new EntityNotFoundException("Payment with id " + id + " not found");
        }

        PaymentDto dto = mapper.map(payment.get(), PaymentDto.class);
        dto.setStatus(Status.CONFIRMED);

        updateOrder(dto);

        orderClient.updateSatusOrder(payment.get().getOrderId(), Status.CONFIRMED.toString());
    }

    public void updateStatusOrder(Long id){
        Optional<Payment> payment = paymentRepository.findById(id);

        if(!payment.isPresent()){
            throw new EntityNotFoundException("Payment with id " + id + " not found");
        }

        PaymentDto dto = mapper.map(payment.get(), PaymentDto.class);
        dto.setStatus(Status.CONFIRM_WITHOUT_INTEGRATION);

        updateOrder(dto);

    }

    private void updateOrder(PaymentDto dto){
        Payment payment = mapper.map(dto, Payment.class);

        paymentRepository.save(payment);
    }
}
