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
    private Integer quantity;
    private Double unitPrice;
    private Double discount;
    private Double totalPrice;

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
                ", quantity=" + quantity +
                ", unitPrice=" + unitPrice +
                ", discount=" + discount +
                ", totalPrice=" + totalPrice +
                ", ticket=" + ticket +
                ", invoice=" + invoice +
                '}';
    }
}
