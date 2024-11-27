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
public class OurTicketsDTO {

    private int statusCode;
    private String error;
    private String message;
    private LocalDate buyingDate;
    private Double discount;
    private Double price;
    private Double priceWithDiscount;

    private OurSeats seat;
    private OurUsers client;
    private OurEvents event;

    private OurTickets ourTickets;
    private List<OurTickets> ourTicketsList;
}
