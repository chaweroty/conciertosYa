package com.chaweDev.conciertosYa.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "ourplaces")
@Data
public class OurPlaces {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private Integer capacityGeneral;
    private Integer capacityVip;
    private Integer capacityPalco;
    private String state;
    private String city;
    private String direction;
    private String image;

    @Override
    public String toString() {
        return "OurPlaces{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", capacityGeneral=" + capacityGeneral +
                ", capacityVip=" + capacityVip +
                ", capacityPalco=" + capacityPalco +
                ", state='" + state + '\'' +
                ", city='" + city + '\'' +
                ", direction='" + direction + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}