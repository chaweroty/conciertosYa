package com.chaweDev.conciertosYa.dto;

import com.chaweDev.conciertosYa.entity.OurPlaces;
import com.chaweDev.conciertosYa.entity.OurSeats;
import lombok.Data;

import java.util.List;

@Data
public class OurSeatsDTO {

    private int statusCode;
    private String error;
    private String message;
    private Integer id;
    private String code;
    private String row;
    private String column;
    private Double price;
    private Double discount;
    private String type; // General, VIP, Palco
    private String state; // Available, Reserved, Sold
    private OurPlaces place;
    private OurSeats ourSeats;
    private List<OurSeats> ourSeatsList;
}
