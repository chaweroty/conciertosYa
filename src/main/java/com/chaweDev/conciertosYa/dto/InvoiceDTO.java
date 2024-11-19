package com.chaweDev.conciertosYa.dto;

import com.chaweDev.conciertosYa.entity.OurUsers;
import lombok.Data;

import java.time.LocalDate;

@Data
public class InvoiceDTO {

    private Integer id;
    private LocalDate issueDate;
    private Double total;
    private String paymentMethod;
    private OurUsers client;
}
