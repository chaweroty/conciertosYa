package com.chaweDev.conciertosYa.dto;

import lombok.Data;

@Data
public abstract class DTO {
    private int statusCode;
    private String error;
    private String message;
}
