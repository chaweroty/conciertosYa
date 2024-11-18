package com.chaweDev.conciertosYa.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "invoices")
@Data
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private LocalDate issueDate;
    private Double total;
    private String paymentMethod;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private OurUsers client;

    @Override
    public String toString() {
        return "Invoice{" +
                "id=" + id +
                ", issueDate=" + issueDate +
                ", total=" + total +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", client=" + client +
                '}';
    }
}
