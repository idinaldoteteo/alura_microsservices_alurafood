package br.com.alurafood.order.order.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private LocalDateTime datetime;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Status status;

    private String msg;

    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "order")
    private List<OrderItem> itens = new ArrayList<>();
}
