package com.chaweDev.conciertosYa.service;

import com.chaweDev.conciertosYa.dto.*;
import com.chaweDev.conciertosYa.entity.Invoice;
import com.chaweDev.conciertosYa.entity.OurTickets;
import com.chaweDev.conciertosYa.entity.InvoiceDetail;
import com.chaweDev.conciertosYa.repository.InvoiceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// PRINCIPIO DE EXPERTO EN INFORMACIÓN (Information Expert):
// El servicio es el experto en la gestión de las facturas.
// Contiene la lógica para manipular los datos relacionados con la gestion de la factura,
// como guardarlos, obtenerlos, actualizarlos y eliminarlos, y tiene acceso
// al repositorio donde se encuentran los datos de las facturas.
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

    // PRINCIPIO DE CREADOR (Creator):
    // Este metodo crea una nueva factura utilizando los datos recibidos en el DTO.
    // El servicio se encarga de crear el objeto  a partir de los datos recibidos,
    // lo guarda en el repositorio y luego genera una respuesta adecuada.
    public InvoiceRequestDTO addInvoice(InvoiceRequestDTO invoiceRequestDTO) {
        InvoiceRequestDTO response = new InvoiceRequestDTO();
        try {

            System.out.println("Inicio del método addInvoice");
            System.out.println("Datos recibidos: " + invoiceRequestDTO);

            // Crear y guardar la factura
            Invoice savedInvoice = new Invoice();
            savedInvoice.setIssueDate(invoiceRequestDTO.getIssueDate());
            savedInvoice.setTotal(invoiceRequestDTO.getTotal());
            savedInvoice.setPaymentMethod(invoiceRequestDTO.getPaymentMethod());
            savedInvoice.setClient(invoiceRequestDTO.getClient());

            System.out.println("Guardando factura...");
            Invoice ourInvoiceResult = invoiceRepo.save(savedInvoice);

            if (ourInvoiceResult.getId() > 0) {
                System.out.println("Factura guardada con ID: " + ourInvoiceResult.getId());

                // Crear listas para almacenar los tickets y detalles de la factura
                List<OurTickets> ticketList = new ArrayList<>();
                List<InvoiceDetail> detailList = new ArrayList<>();

                // Guardar los detalles de la factura
                for (Integer seat : invoiceRequestDTO.getOurSeatsList()) {
                    System.out.println("Procesando asiento con ID: " + seat);

                    // Obtener información del asiento
                    OurSeatsDTO seatData = seatService.getSeatById(seat);
                    System.out.println("Datos del asiento: " + seatData);

                    Double price = seatData.getOurSeats().getPrice();
                    Double discount = seatData.getOurSeats().getDiscount();
                    System.out.println(seatData.getDiscount());

                    OurTicketsDTO ticket = new OurTicketsDTO();
                    if (discount != null) {
                        ticket.setBuyingDate(invoiceRequestDTO.getBuyingDate());
                        ticket.setDiscount(discount);
                        ticket.setPrice(price);
                        ticket.setPriceWithDiscount(price - (price * (discount / 100)));
                    } else {
                        ticket.setBuyingDate(invoiceRequestDTO.getBuyingDate());
                        ticket.setDiscount(discount);
                        ticket.setPrice(price);
                        ticket.setPriceWithDiscount(price);
                    }
                    ticket.setSeat(seatData.getOurSeats());
                    ticket.setClient(invoiceRequestDTO.getClient());
                    ticket.setEvent(invoiceRequestDTO.getEvent());

                    System.out.println("Guardando ticket...");
                    OurTicketsDTO ticketResponse = ticketService.addTicket(ticket);
                    System.out.println("Ticket guardado con ID: " + ticketResponse.getOurTickets().getId());

                    // Agregar el ticket a la lista de tickets
                    ticketList.add(ticketResponse.getOurTickets());

                    // Guardar detalle de factura
                    InvoiceDetailDTO detailDTO = new InvoiceDetailDTO();
                    detailDTO.setInvoice(ourInvoiceResult);
                    detailDTO.setTicket(ticketResponse.getOurTickets());

                    // Agregar el detalle a la lista de detalles
                    InvoiceDetailDTO savedDetail = invoiceDetailManagementService.addInvoiceDetail(detailDTO);
                    detailList.add(savedDetail.getOurInvoiceDetail());

                    System.out.println("Detalle de factura guardado para ticket ID: " + ticketResponse.getOurTickets().getId());
                }
                System.out.println("Hello1");
                response.setId(ourInvoiceResult.getId());
                response.setIssueDate(invoiceRequestDTO.getIssueDate());
                response.setTotal(invoiceRequestDTO.getTotal());
                response.setPaymentMethod(invoiceRequestDTO.getPaymentMethod());
                System.out.println("Hello4");
                response.setMessage("Invoice saved successfully.");
                System.out.println("Hello5");
                response.setStatusCode(200);
                System.out.println("Hello6");
            } else {
                System.out.println("Error: La factura no se pudo guardar.");
                response.setMessage("Invoice not saved due to an unknown error.");
                response.setStatusCode(500);
            }
            System.out.println("Hello7");
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred: " + e.getMessage());
        }
        System.out.println("Hello8");
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

    public InvoiceDTO getInvoicesByUserId(Integer userId) {
        InvoiceDTO response = new InvoiceDTO();
        try {
            List<Invoice> invoices = invoiceRepo.findAll();
            List<Invoice> userInvoices = new ArrayList<>();
            for (Invoice invoice : invoices) {
                if (invoice.getClient().getId().equals(userId)) {
                    userInvoices.add(invoice);
                }
            }
            if (invoices.isEmpty()) {
                response.setStatusCode(404);
                response.setMessage("No invoices found");
            } else {
                response.setStatusCode(200);
                response.setOurInvoicesList(userInvoices);
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