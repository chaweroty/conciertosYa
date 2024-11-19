package com.chaweDev.conciertosYa.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "ourevents")
@Data
public class OurEvents {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private LocalDate date;
    private LocalTime hour;
    private String description;
    private String musicalGenre;
    private String status; // Programado, Cancelado, Finalizado
    private String image;

    @ManyToOne
    @JoinColumn(name = "place_id")
    private OurPlaces place;

    @ManyToOne
    @JoinColumn(name = "artist_id")
    private OurArtists artist;
}
