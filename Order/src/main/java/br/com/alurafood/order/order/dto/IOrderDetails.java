package br.com.alurafood.order.order.dto;

public interface IOrderDetails {

    /*
    Explanation about projections:
    https://docs.spring.io/spring-data/jpa/reference/repositories/projections.html
     */
    String getRegistrationId();
    String getStatusOrder();
    String getQuantityItems();
}
