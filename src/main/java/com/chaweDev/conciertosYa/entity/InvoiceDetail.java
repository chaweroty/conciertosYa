package com.chaweDev.conciertosYa.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "invoice_details")
@Data
public class InvoiceDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "ticket_id")
    private OurTickets ticket;

    @ManyToOne
    @JoinColumn(name = "invoice_id")
    private Invoice invoice;

    @Override
    public String toString() {
        return "InvoiceDetail{" +
                "id=" + id +
                ", ticket=" + ticket +
                ", invoice=" + invoice +
                '}';
    }
}
