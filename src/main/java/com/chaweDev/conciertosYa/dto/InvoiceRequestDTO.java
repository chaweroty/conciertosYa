package com.chaweDev.conciertosYa.dto;

import com.chaweDev.conciertosYa.entity.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class InvoiceRequestDTO extends DTO{
    private Integer id;
    private LocalDate issueDate;
    private Double total;
    private String paymentMethod;
    private OurUsers client;
    private List<Integer> ourSeatsList;
    private LocalDate buyingDate;
    private OurEvents event;
}
