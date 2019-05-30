package com.service.antenna.domain;

public enum Status {
    TO_DO("В ожидание"),
    IN_PROGRESS("В работе"),
    DONE("Выполен"),
    ALL("Все");

    private String name;

    Status(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
