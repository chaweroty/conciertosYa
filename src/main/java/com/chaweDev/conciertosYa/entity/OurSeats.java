package com.chaweDev.conciertosYa.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "ourseats")
@Data
public class OurSeats {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String code;
    private String row;
    private String column;
    private Double price;
    private Double discount;
    private String type; // General, VIP, Palco
    private String state; // Available, Reserved, Sold

    @ManyToOne
    @JoinColumn(name = "place_id")
    private OurPlaces place;

    @Override
    public String toString() {
        return "Seat{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", row='" + row + '\'' +
                ", column='" + column + '\'' +
                ", price=" + price +
                ", discount=" + discount +
                ", type='" + type + '\'' +
                ", state='" + state + '\'' +
                ", place=" + place +
                '}';
    }
}
