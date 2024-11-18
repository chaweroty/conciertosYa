package com.chaweDev.conciertosYa.dto;

import com.chaweDev.conciertosYa.entity.Invoice;
import com.chaweDev.conciertosYa.entity.OurTickets;
import lombok.Data;

@Data
public class InvoiceDetailDTO {

    private Integer id;
    private Integer quantity;
    private Double unitPrice;
    private Double discount;
    private Double totalPrice;
    private OurTickets ticket;
    private Invoice invoice;
}
