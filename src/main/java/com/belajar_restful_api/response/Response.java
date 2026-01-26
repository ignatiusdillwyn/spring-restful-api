package com.belajar_restful_api.response;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter

public class Response {
    private Integer status;
    private String message;
    private Object data;

    // Constructor
    public Response() {
    }

    public Response(Integer status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
}