package com.chaweDev.conciertosYa.service;

import com.chaweDev.conciertosYa.dto.InvoiceDTO;
import com.chaweDev.conciertosYa.entity.Invoice;
import com.chaweDev.conciertosYa.repository.InvoiceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InvoiceManagementService {

    @Autowired
    private InvoiceRepo invoiceRepo;

    public InvoiceDTO addInvoice(InvoiceDTO invoice) {
        InvoiceDTO response = new InvoiceDTO();
        try {

            Invoice savedInvoice = new Invoice();

            savedInvoice.setIssueDate(invoice.getIssueDate());
            savedInvoice.setTotal(invoice.getTotal());
            savedInvoice.setPaymentMethod(invoice.getPaymentMethod());
            savedInvoice.setClient(invoice.getClient());

            Invoice ourInvoiceResult = invoiceRepo.save(savedInvoice);
            if (ourInvoiceResult.getId() > 0) {
                response.setOurInvoice(ourInvoiceResult);
                response.setMessage("Invoice Saved Successfully");
                response.setStatusCode(200);
            } else {
                response.setMessage("Invoice not saved due to an unknown error.");
                response.setStatusCode(500);
            }
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred: " + e.getMessage());
        }
        return response;
    }

    public InvoiceDTO getAllInvoices() {
        InvoiceDTO response = new InvoiceDTO();
        try {
            List<Invoice> invoices = invoiceRepo.findAll();
            if (invoices.isEmpty()) {
                response.setStatusCode(404);
                response.setMessage("No invoices found");
            } else {
                response.setStatusCode(200);
                response.setOurInvoicesList(invoices);
                response.setMessage("Invoices found successfully");
            }
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred: " + e.getMessage());
        }
        return response;
    }

    public InvoiceDTO getInvoiceById(Integer invoiceId) {
        InvoiceDTO response = new InvoiceDTO();
        try {
            Invoice invoice = invoiceRepo.findById(invoiceId).orElseThrow(() -> new RuntimeException("Invoice not found"));
            response.setStatusCode(200);
            response.setOurInvoice(invoice);
            response.setMessage("Invoice found successfully");
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred: " + e.getMessage());
        }
        return response;
    }

    public InvoiceDTO updateInvoice(Integer invoiceId, Invoice invoice) {
        InvoiceDTO response = new InvoiceDTO();
        try {
            Optional<Invoice> existingInvoiceOpt = invoiceRepo.findById(invoiceId);
            if (existingInvoiceOpt.isPresent()) {
                Invoice existingInvoice = existingInvoiceOpt.get();

                existingInvoice.setIssueDate(invoice.getIssueDate());
                existingInvoice.setTotal(invoice.getTotal());
                existingInvoice.setPaymentMethod(invoice.getPaymentMethod());
                existingInvoice.setClient(invoice.getClient());

                Invoice updatedInvoice = invoiceRepo.save(existingInvoice);
                response.setStatusCode(200);
                response.setOurInvoice(updatedInvoice);
                response.setMessage("Invoice updated successfully");
            } else {
                response.setStatusCode(404);
                response.setMessage("Invoice not found");
            }
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred: " + e.getMessage());
        }
        return response;
    }

    public InvoiceDTO deleteInvoice(Integer invoiceId) {
        InvoiceDTO response = new InvoiceDTO();
        try {
            Optional<Invoice> invoiceOptional = invoiceRepo.findById(invoiceId);
            if (invoiceOptional.isPresent()) {
                invoiceRepo.deleteById(invoiceId);
                response.setStatusCode(200);
                response.setMessage("Invoice deleted successfully");
            } else {
                response.setStatusCode(404);
                response.setMessage("Invoice not found");
            }
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred while deleting invoice: " + e.getMessage());
        }
        return response;
    }
}