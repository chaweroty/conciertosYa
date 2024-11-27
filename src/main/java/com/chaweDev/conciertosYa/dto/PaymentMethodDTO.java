package com.chaweDev.conciertosYa.dto;

import com.chaweDev.conciertosYa.entity.OurUsers;
import com.chaweDev.conciertosYa.entity.PaymentMethod;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentMethodDTO {

    private int statusCode;
    private String error;
    private String message;
    private Integer id;
    private String type; // Cash, Cash and Credit Card, etc.
    private OurUsers client;
    private PaymentMethod paymentMethod;
    private List<PaymentMethod> ourPaymentMethodList;
}
