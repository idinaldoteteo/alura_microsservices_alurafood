package br.com.alurafood.order.order.repository;


import br.com.alurafood.order.order.model.Order;
import br.com.alurafood.order.order.model.Status;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface IOrderRepository extends JpaRepository<Order, Long> {

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update Order o set o.status = :status where o = :pedido")
    void updateStatus(Status status, Order order);

    @Query("select o from Order o left join fetch o.itens where o.id =: id")
    Order findOrder(Long id);
}
