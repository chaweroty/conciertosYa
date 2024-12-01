package com.chaweDev.conciertosYa.service;

import com.chaweDev.conciertosYa.dto.InvoiceDetailDTO;
import com.chaweDev.conciertosYa.entity.Invoice;
import com.chaweDev.conciertosYa.entity.InvoiceDetail;
import com.chaweDev.conciertosYa.entity.OurTickets;
import com.chaweDev.conciertosYa.repository.InvoiceDetailRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InvoiceDetailManagementService {

    @Autowired
    private InvoiceDetailRepo invoiceDetailRepo;

    public InvoiceDetailDTO addInvoiceDetail(InvoiceDetailDTO invoiceDetail) {
        InvoiceDetailDTO response = new InvoiceDetailDTO();
        try {
            InvoiceDetail savedInvoiceDetail = new InvoiceDetail();

            saveInvoiceDetail(savedInvoiceDetail, invoiceDetail.getTicket(), invoiceDetail.getInvoice());

            InvoiceDetail updatedInvoiceDetail = invoiceDetailRepo.save(savedInvoiceDetail);

            if (updatedInvoiceDetail.getId() > 0) {
                response.setOurInvoiceDetail(updatedInvoiceDetail);
                response.setMessage("Invoice detail Saved Successfully");
                response.setStatusCode(200);
            } else {
                response.setMessage("Invoice detail not saved due to an unknown error.");
                response.setStatusCode(500);
            }
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred: " + e.getMessage());
        }
        return response;
    }

    public InvoiceDetailDTO getAllInvoiceDetails() {
        InvoiceDetailDTO response = new InvoiceDetailDTO();
        try {
            List<InvoiceDetail> invoiceDetails = invoiceDetailRepo.findAll();
            if (invoiceDetails.isEmpty()) {
                response.setStatusCode(404);
                response.setMessage("No invoice details found");
            } else {
                response.setStatusCode(200);
                response.setOurInvoicesDetailList(invoiceDetails);
                response.setMessage("Invoice details found successfully");
            }
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred: " + e.getMessage());
        }
        return response;
    }

    public InvoiceDetailDTO getInvoiceDetailById(Integer invoiceDetailId) {
        InvoiceDetailDTO response = new InvoiceDetailDTO();
        try {
            InvoiceDetail invoiceDetail = invoiceDetailRepo.findById(invoiceDetailId).orElseThrow(() -> new RuntimeException("Invoice Detail not found"));
            response.setStatusCode(200);
            response.setOurInvoiceDetail(invoiceDetail);
            response.setMessage("Invoice Detail found successfully");
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred: " + e.getMessage());
        }
        return response;
    }

    public InvoiceDetailDTO updateInvoiceDetail(Integer invoiceDetailId, InvoiceDetail invoiceDetail) {
        InvoiceDetailDTO response = new InvoiceDetailDTO();
        try {
            Optional<InvoiceDetail> existingInvoiceDetailOpt = invoiceDetailRepo.findById(invoiceDetailId);
            if (existingInvoiceDetailOpt.isPresent()) {
                InvoiceDetail existingInvoiceDetail = existingInvoiceDetailOpt.get();

                saveInvoiceDetail(existingInvoiceDetail, invoiceDetail.getTicket(), invoiceDetail.getInvoice());


                InvoiceDetail updatedInvoiceDetail = invoiceDetailRepo.save(existingInvoiceDetail);

                response.setStatusCode(200);
                response.setOurInvoiceDetail(updatedInvoiceDetail);
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

    private void saveInvoiceDetail(InvoiceDetail existingInvoiceDetail, OurTickets ticket, Invoice invoice) {
        existingInvoiceDetail.setTicket(ticket);
        existingInvoiceDetail.setInvoice(invoice);
    }

    public InvoiceDetailDTO deleteInvoiceDetail(Integer invoiceDetailId) {
        InvoiceDetailDTO response = new InvoiceDetailDTO();
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
}