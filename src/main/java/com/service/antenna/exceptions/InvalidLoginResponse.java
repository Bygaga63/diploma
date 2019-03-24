package com.service.antenna.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvalidLoginResponse {
    private String username;
    private String password;

    public InvalidLoginResponse() {
        this.username = "Неверный логин";
        this.password = "Неверный пароль";
    }
}
