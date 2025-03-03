package br.com.alurafood.payments.repository;

import br.com.alurafood.payments.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPaymentRepository extends JpaRepository<Payment, Long> {
}
