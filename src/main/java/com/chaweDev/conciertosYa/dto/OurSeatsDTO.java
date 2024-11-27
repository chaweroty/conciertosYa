package com.chaweDev.conciertosYa.dto;

import com.chaweDev.conciertosYa.entity.OurPlaces;
import com.chaweDev.conciertosYa.entity.OurSeats;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class OurSeatsDTO {

    private int statusCode;
    private String error;
    private String message;
    private String code;
    private Integer row;
    private Integer column;
    private Double price;
    private Double discount;
    private String type; // General, VIP, Palco
    private String state; // Available, Reserved, Sold
    private Integer place;
    private OurSeats ourSeats;
    private List<OurSeats> ourSeatsList;
}
