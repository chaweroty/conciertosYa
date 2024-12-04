package com.chaweDev.conciertosYa.controller.Visual;

import com.chaweDev.conciertosYa.dto.InvoiceDetailDTO;
import com.chaweDev.conciertosYa.entity.InvoiceDetail;

public interface IInvoiceDetailManagementService {
    InvoiceDetailDTO addInvoiceDetail(InvoiceDetailDTO invoice);

    InvoiceDetailDTO getAllInvoiceDetails();

    InvoiceDetailDTO getInvoiceDetailById(Integer invoiceDetailId);

    InvoiceDetailDTO updateInvoiceDetail(Integer invoiceDetailId, InvoiceDetail invoice);

    InvoiceDetailDTO deleteInvoiceDetail(Integer invoiceDetailId);
}
