package com.chaweDev.conciertosYa.dto;

import com.chaweDev.conciertosYa.entity.OurArtists;
import com.chaweDev.conciertosYa.entity.OurEvents;
import com.chaweDev.conciertosYa.entity.OurPlaces;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class OurEventsDTO {

    private int statusCode;
    private String error;
    private String message;
    private String name;
    private LocalDate date;
    private LocalTime hour;
    private String description;
    private String musicalGenre;
    private String status;
    private String image;

    // Relación con lugar
    private OurPlaces place;
    private OurArtists artist;

    private OurEvents ourEvents;
    private List<OurEvents> ourEventsList;

}
