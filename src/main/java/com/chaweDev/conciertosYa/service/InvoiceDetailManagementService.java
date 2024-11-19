/**package com.chaweDev.conciertosYa.service;

import com.chaweDev.conciertosYa.dto.ReqRes;
import com.chaweDev.conciertosYa.entity.InvoiceDetail;
import com.chaweDev.conciertosYa.repository.InvoiceDetailRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InvoiceDetailManagementService {

    @Autowired
    private InvoiceDetailRepo invoiceDetailRepo;

    public ReqRes addInvoiceDetail(InvoiceDetail invoiceDetail) {
        ReqRes response = new ReqRes();
        try {
            InvoiceDetail savedInvoiceDetail = invoiceDetailRepo.save(invoiceDetail);
            response.setStatusCode(200);
            response.setMessage("Invoice Detail added successfully");
            response.setInvoiceDetail(savedInvoiceDetail);
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred: " + e.getMessage());
        }
        return response;
    }

    public ReqRes getAllInvoiceDetails() {
        ReqRes response = new ReqRes();
        try {
            List<InvoiceDetail> invoiceDetails = invoiceDetailRepo.findAll();
            if (invoiceDetails.isEmpty()) {
                response.setStatusCode(404);
                response.setMessage("No invoice details found");
            } else {
                response.setStatusCode(200);
                response.setInvoiceDetailList(invoiceDetails);
                response.setMessage("Invoice details found successfully");
            }
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred: " + e.getMessage());
        }
        return response;
    }

    public ReqRes getInvoiceDetailById(Integer invoiceDetailId) {
        ReqRes response = new ReqRes();
        try {
            InvoiceDetail invoiceDetail = invoiceDetailRepo.findById(invoiceDetailId).orElseThrow(() -> new RuntimeException("Invoice Detail not found"));
            response.setStatusCode(200);
            response.setInvoiceDetail(invoiceDetail);
            response.setMessage("Invoice Detail found successfully");
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred: " + e.getMessage());
        }
        return response;
    }

    public ReqRes updateInvoiceDetail(Integer invoiceDetailId, InvoiceDetail invoiceDetail) {
        ReqRes response = new ReqRes();
        try {
            Optional<InvoiceDetail> existingInvoiceDetailOpt = invoiceDetailRepo.findById(invoiceDetailId);
            if (existingInvoiceDetailOpt.isPresent()) {
                InvoiceDetail existingInvoiceDetail = existingInvoiceDetailOpt.get();
                existingInvoiceDetail.setPrice(invoiceDetail.getPrice());
                existingInvoiceDetail.setQuantity(invoiceDetail.getQuantity());

                InvoiceDetail updatedInvoiceDetail = invoiceDetailRepo.save(existingInvoiceDetail);
                response.setStatusCode(200);
                response.setInvoiceDetail(updatedInvoiceDetail);
                response.setMessage("Invoice Detail updated successfully");
            } else {
                response.setStatusCode(404);
                response.setMessage("Invoice Detail not found");
            }
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred: " + e.getMessage());
        }
        return response;
    }

    public ReqRes deleteInvoiceDetail(Integer invoiceDetailId) {
        ReqRes response = new ReqRes();
        try {
            Optional<InvoiceDetail> invoiceDetailOptional = invoiceDetailRepo.findById(invoiceDetailId);
            if (invoiceDetailOptional.isPresent()) {
                invoiceDetailRepo.deleteById(invoiceDetailId);
                response.setStatusCode(200);
                response.setMessage("Invoice Detail deleted successfully");
            } else {
                response.setStatusCode(404);
                response.setMessage("Invoice Detail not found");
            }
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred while deleting invoice detail: " + e.getMessage());
        }
        return response;
    }
} **/