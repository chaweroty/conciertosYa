package com.chaweDev.conciertosYa.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "ourartists")
@Data
public class OurArtists {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String musicalGenre;
    private String instagram;
    private String facebook;
    private String contact;
}
