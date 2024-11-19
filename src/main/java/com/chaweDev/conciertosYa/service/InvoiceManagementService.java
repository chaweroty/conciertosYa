/** package com.chaweDev.conciertosYa.service;

import com.chaweDev.conciertosYa.dto.ReqRes;
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

    public ReqRes addInvoice(Invoice invoice) {
        ReqRes response = new ReqRes();
        try {
            Invoice savedInvoice = invoiceRepo.save(invoice);
            response.setStatusCode(200);
            response.setMessage("Invoice added successfully");
            response.setInvoice(savedInvoice);
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred: " + e.getMessage());
        }
        return response;
    }

    public ReqRes getAllInvoices() {
        ReqRes response = new ReqRes();
        try {
            List<Invoice> invoices = invoiceRepo.findAll();
            if (invoices.isEmpty()) {
                response.setStatusCode(404);
                response.setMessage("No invoices found");
            } else {
                response.setStatusCode(200);
                response.setInvoiceList(invoices);
                response.setMessage("Invoices found successfully");
            }
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred: " + e.getMessage());
        }
        return response;
    }

    public ReqRes getInvoiceById(Integer invoiceId) {
        ReqRes response = new ReqRes();
        try {
            Invoice invoice = invoiceRepo.findById(invoiceId).orElseThrow(() -> new RuntimeException("Invoice not found"));
            response.setStatusCode(200);
            response.setInvoice(invoice);
            response.setMessage("Invoice found successfully");
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred: " + e.getMessage());
        }
        return response;
    }

    public ReqRes updateInvoice(Integer invoiceId, Invoice invoice) {
        ReqRes response = new ReqRes();
        try {
            Optional<Invoice> existingInvoiceOpt = invoiceRepo.findById(invoiceId);
            if (existingInvoiceOpt.isPresent()) {
                Invoice existingInvoice = existingInvoiceOpt.get();
                existingInvoice.setAmount(invoice.getAmount());
                existingInvoice.setDate(invoice.getDate());
                existingInvoice.setStatus(invoice.getStatus());

                Invoice updatedInvoice = invoiceRepo.save(existingInvoice);
                response.setStatusCode(200);
                response.setInvoice(updatedInvoice);
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

    public ReqRes deleteInvoice(Integer invoiceId) {
        ReqRes response = new ReqRes();
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
} **/