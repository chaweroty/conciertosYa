package com.chaweDev.conciertosYa.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Data
public abstract class DTO {
    // Setters
    protected int statusCode;
    protected String error;
    protected String message;


}
