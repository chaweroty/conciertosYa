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
