package com.chaweDev.conciertosYa.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "ourtickets")
@Data
public class OurTickets {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private LocalDate buyingDate;
    private Double discount;
    private Double price;
    private Double priceWithDiscount;

    @ManyToOne
    @JoinColumn(name = "id_seat")
    private OurSeats seat;

    @ManyToOne
    @JoinColumn(name = "id_client")
    private OurUsers client;

    @ManyToOne
    @JoinColumn(name = "id_event")
    private OurEvents event;
}

