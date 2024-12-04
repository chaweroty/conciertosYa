package com.chaweDev.conciertosYa.service;

import com.chaweDev.conciertosYa.dto.*;
import com.chaweDev.conciertosYa.entity.Invoice;
import com.chaweDev.conciertosYa.entity.OurTickets;
import com.chaweDev.conciertosYa.entity.InvoiceDetail;
import com.chaweDev.conciertosYa.repository.InvoiceRepo;
import com.chaweDev.conciertosYa.service.visual.IInvoiceManagementService;
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
public class InvoiceManagementService implements IInvoiceManagementService {

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
    /*
    Instanciación de InvoiceRequestDTO desde un objeto de tipo DTO:
    El metodo addInvoice recibe un parámetro de tipo DTO, que podría ser cualquier objeto que implemente la interfaz DTO.
    La expresión instanceof InvoiceRequestDTO verifica si el objeto dto es una instancia de InvoiceRequestDTO,
    lo que permite que se pueda tratar específicamente como un objeto de tipo InvoiceRequestDTO. Si el objeto es de ese tipo,
    el código dentro del bloque if se ejecuta.

    Asignación a una variable del tipo InvoiceRequestDTO:
    El uso de instanceof no solo verifica el tipo del objeto,
    sino que también realiza un casting implícito del objeto dto a InvoiceRequestDTO.
    Esto significa que el objeto dto es tratado como un InvoiceRequestDTO dentro del bloque if.
    Esta técnica permite que se puedan acceder de manera directa a los métodos y propiedades específicos de InvoiceRequestDTO sin necesidad de hacer un cast manual,
    y es posible gracias al polimorfismo.

    Utilización de datos para crear y guardar una factura:
    Una vez verificado y casteado el DTO como InvoiceRequestDTO, se procede a crear un objeto de tipo Invoice
    y a establecer sus valores mediante los datos provenientes del DTO. Luego, la factura se guarda en el repositorio mediante invoiceRepo.save().
    Si la factura se guarda exitosamente (es decir, si el id de la factura es mayor a 0), se actualizan los valores de la respuesta y se indica que la factura fue guardada correctamente.
    En caso contrario, se devuelve un mensaje indicando un error en el proceso de guardado.
    */
    public InvoiceRequestDTO addInvoice(DTO dto) {
        InvoiceRequestDTO response = new InvoiceRequestDTO();
        try {
            if (dto instanceof InvoiceRequestDTO invoiceRequest) {
                // Crear y guardar la factura
                Invoice savedInvoice = new Invoice();
                savedInvoice.setIssueDate(invoiceRequest.getIssueDate());
                savedInvoice.setTotal(invoiceRequest.getTotal());
                savedInvoice.setPaymentMethod(invoiceRequest.getPaymentMethod());
                savedInvoice.setClient(invoiceRequest.getClient());

                // Guardar la factura en el repositorio
                Invoice ourInvoiceResult = invoiceRepo.save(savedInvoice);

                if (ourInvoiceResult.getId() > 0) {
                    // Crear listas para almacenar los tickets y detalles de la factura
                    List<OurTickets> ticketList = new ArrayList<>();
                    List<InvoiceDetail> detailList = new ArrayList<>();

                    // Guardar los detalles de la factura
                    for (Integer seat : invoiceRequest.getOurSeatsList()) {
                        // Obtener información del asiento
                        OurSeatsDTO seatData = seatService.getSeatById(seat);
                        Double price = seatData.getOurSeats().getPrice();
                        Double discount = seatData.getOurSeats().getDiscount();

                        OurTicketsDTO ticket = new OurTicketsDTO();
                        ticket.setBuyingDate(invoiceRequest.getBuyingDate());
                        ticket.setDiscount(discount);
                        ticket.setPrice(price);
                        ticket.setPriceWithDiscount(discount != null ? price - (price * (discount / 100)) : price);
                        ticket.setSeat(seatData.getOurSeats());
                        ticket.setClient(invoiceRequest.getClient());
                        ticket.setEvent(invoiceRequest.getEvent());

                        // Guardar el ticket
                        OurTicketsDTO ticketResponse = ticketService.addTicket(ticket);

                        // Agregar el ticket a la lista de tickets
                        ticketList.add(ticketResponse.getOurTickets());

                        // Guardar detalle de factura
                        InvoiceDetailDTO detailDTO = new InvoiceDetailDTO();
                        detailDTO.setInvoice(ourInvoiceResult);
                        detailDTO.setTicket(ticketResponse.getOurTickets());

                        // Agregar el detalle a la lista de detalles
                        InvoiceDetailDTO savedDetail = invoiceDetailManagementService.addInvoiceDetail(detailDTO);
                        detailList.add(savedDetail.getOurInvoiceDetail());
                    }

                    // Respuesta exitosa
                    response.setId(ourInvoiceResult.getId());
                    response.setIssueDate(invoiceRequest.getIssueDate());
                    response.setTotal(invoiceRequest.getTotal());
                    response.setPaymentMethod(invoiceRequest.getPaymentMethod());
                    response.setMessage("Invoice saved successfully.");
                    response.setStatusCode(200);
                } else {
                    // Si la factura no se pudo guardar
                    response.setMessage("Invoice not saved due to an unknown error.");
                    response.setStatusCode(500);
                }
            } else {
                // Si el tipo de DTO no es el esperado
                response.setMessage("Invalid DTO type");
                response.setStatusCode(400);
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