package com.chaweDev.conciertosYa.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "payment_methods")
@Data
public class PaymentMethod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String type; // Cash, Cash and Credit Card, etc.

    @ManyToOne
    @JoinColumn(name = "id_client")
    private OurUsers client;

    @Override
    public String toString() {
        return "PaymentMethod{" +
                "id=" + id +
                ", type='" + type + '\'' +
                '}';
    }
}