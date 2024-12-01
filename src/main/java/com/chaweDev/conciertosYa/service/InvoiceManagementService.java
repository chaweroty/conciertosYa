package com.chaweDev.conciertosYa.service;

import com.chaweDev.conciertosYa.dto.InvoiceDTO;
import com.chaweDev.conciertosYa.dto.InvoiceDetailDTO;
import com.chaweDev.conciertosYa.dto.InvoiceRequestDTO;
import com.chaweDev.conciertosYa.dto.OurTicketsDTO;
import com.chaweDev.conciertosYa.entity.Invoice;
import com.chaweDev.conciertosYa.entity.OurSeats;
import com.chaweDev.conciertosYa.repository.InvoiceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InvoiceManagementService {

    @Autowired
    private InvoiceRepo invoiceRepo;

    @Autowired
    private InvoiceDetailManagementService invoiceDetailManagementService;

    @Autowired
    private TicketManagementService ticketService;

    @Autowired
    private SeatManagementService seatService;

    public InvoiceDTO addInvoice(InvoiceRequestDTO invoiceRequestDTO) {
        InvoiceDTO response = new InvoiceDTO();
        try {
            // Crear y guardar la factura principal
            Invoice savedInvoice = new Invoice();
            savedInvoice.setIssueDate(invoiceRequestDTO.getIssueDate());
            savedInvoice.setTotal(invoiceRequestDTO.getTotal());
            savedInvoice.setPaymentMethod(invoiceRequestDTO.getPaymentMethod());
            savedInvoice.setClient(invoiceRequestDTO.getClient());

            Invoice ourInvoiceResult = invoiceRepo.save(savedInvoice);

            if (ourInvoiceResult.getId() > 0) {
                response.setOurInvoice(ourInvoiceResult);
                response.setMessage("Invoice saved successfully.");
                response.setStatusCode(200);

                // Llamar a los métodos de InvoiceDetailManagementService para guardar los detalles de la factura
                for (OurSeats seat : invoiceRequestDTO.getOurSeatsList()) {
                    Double price = seatService.getSeatById(seat.getId()).getPrice();
                    Double discount = seatService.getSeatById(seat.getId()).getDiscount();
                    OurTicketsDTO ticket = new OurTicketsDTO();
                    ticket.setBuyingDate(invoiceRequestDTO.getBuyingDate());
                    ticket.setDiscount(discount);
                    ticket.setPrice(price);
                    ticket.setPriceWithDiscount(price - price*(discount/100));
                    ticket.setSeat(seat);
                    ticket.setClient(invoiceRequestDTO.getClient());
                    ticket.setEvent(invoiceRequestDTO.getEvent());

                    OurTicketsDTO ticketResponse = ticketService.addTicket(ticket);

                    InvoiceDetailDTO detailDTO = new InvoiceDetailDTO();

                    detailDTO.setInvoice(ourInvoiceResult); // Asociar el detalle a la factura creada
                    detailDTO.setTicket(ticketResponse.getOurTickets()); // Asociar el detalle a la factura creada
                    InvoiceDetailDTO detailResponse = invoiceDetailManagementService.addInvoiceDetail(detailDTO);
                    if (detailResponse.getStatusCode() != 200) {
                        throw new RuntimeException("Error saving invoice detail: " + detailResponse.getMessage());
                    }
                }
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