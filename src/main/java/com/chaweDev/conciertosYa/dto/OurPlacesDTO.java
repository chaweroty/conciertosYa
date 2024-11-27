package com.chaweDev.conciertosYa.dto;

import com.chaweDev.conciertosYa.entity.OurPlaces;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class OurPlacesDTO {

    private int statusCode;
    private String error;
    private String message;
    private String name;
    private Integer capacityGeneral;
    private Integer capacityVip;
    private Integer capacityPalco;
    private String state;
    private String city;
    private String direction;
    private String image;
    private OurPlaces ourPlaces;
    private List<OurPlaces> ourPlacesList;
}
