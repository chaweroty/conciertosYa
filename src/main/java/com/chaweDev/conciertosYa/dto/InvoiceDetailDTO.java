package com.chaweDev.conciertosYa.dto;

import com.chaweDev.conciertosYa.entity.Invoice;
import com.chaweDev.conciertosYa.entity.InvoiceDetail;
import com.chaweDev.conciertosYa.entity.OurTickets;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class InvoiceDetailDTO {

    private int statusCode;
    private String error;
    private String message;
    private Integer id;
    private OurTickets ticket;
    private Invoice invoice;

    private InvoiceDetail ourInvoiceDetail;
    private List<InvoiceDetail> ourInvoicesDetailList;
}
