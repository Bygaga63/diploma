package io.agileintelligence.ppmtool.exceptions;

public class ActivateResponse {
    private String code;

    public ActivateResponse(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
