package com.chaweDev.conciertosYa.dto;

import com.chaweDev.conciertosYa.entity.Invoice;
import com.chaweDev.conciertosYa.entity.InvoiceDetail;
import com.chaweDev.conciertosYa.entity.OurTickets;
import lombok.Data;

import java.util.List;

@Data
public class InvoiceDetailDTO {

    private int statusCode;
    private String error;
    private String message;
    private Integer id;
    private Integer quantity;
    private Double unitPrice;
    private Double discount;
    private Double totalPrice;
    private OurTickets ticket;
    private Invoice invoice;

    private InvoiceDetail ourInvoiceDetail;
    private List<InvoiceDetail> ourInvoicesDetailList;
}
