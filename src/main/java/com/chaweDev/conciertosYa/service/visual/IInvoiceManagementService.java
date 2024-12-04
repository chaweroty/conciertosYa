package com.chaweDev.conciertosYa.service.visual;

import com.chaweDev.conciertosYa.dto.DTO;
import com.chaweDev.conciertosYa.dto.InvoiceDTO;
import com.chaweDev.conciertosYa.dto.InvoiceRequestDTO;
import com.chaweDev.conciertosYa.entity.Invoice;

public interface IInvoiceManagementService {
    InvoiceRequestDTO addInvoice(DTO request);

    InvoiceDTO getAllInvoices();


    InvoiceDTO getInvoicesByUserId(Integer userId);

    InvoiceDTO getInvoiceById(Integer invoiceId);

    InvoiceDTO updateInvoice(Integer invoiceId, Invoice invoice);

    InvoiceDTO deleteInvoice(Integer invoiceId);
}
