package com.chaweDev.conciertosYa.service.visual;

import com.chaweDev.conciertosYa.dto.DTO;
import com.chaweDev.conciertosYa.dto.InvoiceDetailDTO;
import com.chaweDev.conciertosYa.entity.InvoiceDetail;

public interface IInvoiceDetailManagementService {
    InvoiceDetailDTO addInvoiceDetail(DTO invoice);

    InvoiceDetailDTO getAllInvoiceDetails();

    InvoiceDetailDTO getInvoiceDetailById(Integer invoiceDetailId);

    InvoiceDetailDTO updateInvoiceDetail(Integer invoiceDetailId, InvoiceDetail invoice);

    InvoiceDetailDTO deleteInvoiceDetail(Integer invoiceDetailId);
}
