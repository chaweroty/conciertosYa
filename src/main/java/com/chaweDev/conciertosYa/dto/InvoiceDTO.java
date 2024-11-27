package com.chaweDev.conciertosYa.dto;

import com.chaweDev.conciertosYa.entity.Invoice;
import com.chaweDev.conciertosYa.entity.OurUsers;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class InvoiceDTO {

    private int statusCode;
    private String error;
    private String message;
    private Integer id;
    private LocalDate issueDate;
    private Double total;
    private String paymentMethod;
    private OurUsers client;

    private Invoice ourInvoice;
    private List<Invoice> ourInvoicesList;
}
