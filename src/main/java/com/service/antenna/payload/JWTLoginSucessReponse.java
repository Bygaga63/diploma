package com.service.antenna.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JWTLoginSucessReponse {
    private boolean success;
    private String token;

    public JWTLoginSucessReponse(boolean success, String token) {
        this.success = success;
        this.token = token;
    }

    public String toString() {
        return "JWTLoginSucessReponse{" +
                "success=" + success +
                ", token='" + token + '\'' +
                '}';
    }
}
