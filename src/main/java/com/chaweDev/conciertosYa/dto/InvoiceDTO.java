package com.chaweDev.conciertosYa.dto;

import com.chaweDev.conciertosYa.entity.Invoice;
import com.chaweDev.conciertosYa.entity.OurUsers;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
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
