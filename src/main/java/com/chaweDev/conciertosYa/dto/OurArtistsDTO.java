package com.chaweDev.conciertosYa.dto;

import com.chaweDev.conciertosYa.entity.OurArtists;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class OurArtistsDTO {

    private int statusCode;
    private String error;
    private String message;
    private String name;
    private String musicalGenre;
    private String instagram;
    private String facebook;
    private String contact;

    private OurArtists ourArtists;
    private List<OurArtists> ourArtistsList;

}
