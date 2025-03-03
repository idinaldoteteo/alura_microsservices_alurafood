package br.com.alurafood.order.order.model;

public enum Status {
    REALIZED,
    CANCELLED,
    PAYD,
    NOT_ALLOWED,
    CONFIRMED,
    READY,
    LEFT_TO_DELIVER,
    DELIVERED;

    public static Status get(String status){
        return switch (status) {
            case "REALIZED" -> Status.REALIZED;
            case "CONFIRMED" -> Status.CONFIRMED;
            case "DELIVERED" -> Status.DELIVERED;
            default -> Status.NOT_ALLOWED;
        };

    }
}
