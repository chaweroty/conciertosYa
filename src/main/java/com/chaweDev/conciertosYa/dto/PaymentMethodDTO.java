package com.chaweDev.conciertosYa.dto;

import lombok.Data;

@Data
public class PaymentMethodDTO {

    private Integer id;
    private String type; // Cash, Cash and Credit Card, etc.
}
