package com.chaweDev.conciertosYa.dto;

import com.chaweDev.conciertosYa.entity.OurArtists;
import lombok.Data;

import java.util.List;

@Data
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
