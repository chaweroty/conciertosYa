package com.chaweDev.conciertosYa.service.Visual;

import com.chaweDev.conciertosYa.dto.InvoiceDTO;
import com.chaweDev.conciertosYa.dto.InvoiceRequestDTO;
import com.chaweDev.conciertosYa.entity.Invoice;

public interface IInvoiceManagementService {
    InvoiceRequestDTO addInvoice(InvoiceRequestDTO request);

    InvoiceDTO getAllInvoices();

    InvoiceDTO getInvoiceById(Integer invoiceId);

    InvoiceDTO updateInvoice(Integer invoiceId, Invoice invoice);

    InvoiceDTO deleteInvoice(Integer invoiceId);
}
