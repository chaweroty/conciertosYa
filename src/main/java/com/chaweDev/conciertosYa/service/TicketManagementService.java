package com.chaweDev.conciertosYa.service;

import com.chaweDev.conciertosYa.dto.DTO;
import com.chaweDev.conciertosYa.dto.OurSeatsDTO;
import com.chaweDev.conciertosYa.dto.OurTicketsDTO;
import com.chaweDev.conciertosYa.entity.OurEvents;
import com.chaweDev.conciertosYa.entity.OurSeats;
import com.chaweDev.conciertosYa.entity.OurTickets;
import com.chaweDev.conciertosYa.entity.OurUsers;
import com.chaweDev.conciertosYa.repository.TicketRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class TicketManagementService {

    @Autowired
    private TicketRepo ticketRepo;

    @Autowired
    private SeatManagementService seatService;

    /*
    Instanciación de OurTicketsDTO desde un objeto de tipo DTO:
    El metodo addTicket recibe un parámetro de tipo DTO, que podría ser cualquier objeto que implemente la interfaz DTO.
    La expresión instanceof OurTicketsDTO verifica si el objeto dto es una instancia de OurTicketsDTO,
    lo que permite que se pueda tratar específicamente como un objeto de tipo OurTicketsDTO. Si el objeto es de ese tipo,
    el código dentro del bloque if se ejecuta.

    Asignación a una variable del tipo OurTicketsDTO:
    El uso de instanceof no solo verifica el tipo del objeto,
    sino que también realiza un casting implícito del objeto dto a OurTicketsDTO.
    Esto significa que el objeto dto es tratado como un OurTicketsDTO dentro del bloque if.
    Esta técnica permite que se puedan acceder de manera directa a los métodos y propiedades específicos de OurTicketsDTO sin necesidad de hacer un cast manual,
    y es posible gracias al polimorfismo.

    Utilización de datos para crear y guardar un ticket:
    Una vez verificado y casteado el DTO como OurTicketsDTO, se procede a crear un objeto de tipo OurTickets
    y a establecer sus valores mediante los datos provenientes del DTO. Luego, el ticket se guarda en el repositorio mediante ticketRepo.save().
    Si el ticket se guarda exitosamente (es decir, si el id del ticket es mayor a 0), se actualizan los valores de la respuesta y se indica que el ticket fue guardado correctamente.
    En caso contrario, se devuelve un mensaje indicando un error en el proceso de guardado.
    */
    public OurTicketsDTO addTicket(DTO dto) {
        OurTicketsDTO response = new OurTicketsDTO();
        try {
            if (dto instanceof OurTicketsDTO ticket) {
                OurTickets savedTicket = new OurTickets();

                // Llamada a un metodo que asigna los valores del ticket
                SaveTicket(savedTicket, ticket.getBuyingDate(), ticket.getDiscount(), ticket.getPrice(), ticket.getPriceWithDiscount(), ticket.getSeat(), ticket.getClient(), ticket.getEvent());

                // Obtener información del asiento relacionado con el ticket
                OurSeats seatResult = seatService.getSeatById(ticket.getSeat().getId()).getOurSeats();
                OurSeatsDTO updatedSeat = new OurSeatsDTO();

                // Actualización de los detalles del asiento
                updatedSeat.setCode(seatResult.getCode());
                updatedSeat.setRow(seatResult.getRow());
                updatedSeat.setColumn(seatResult.getColumn());
                updatedSeat.setType(seatResult.getType());
                updatedSeat.setPrice(seatResult.getPrice());
                updatedSeat.setDiscount(seatResult.getDiscount());
                updatedSeat.setState("Sold");
                updatedSeat.setPlace(seatResult.getPlace().getId());

                // Actualización del estado del asiento en la base de datos
                seatService.updateSeat(seatResult.getId(), updatedSeat);

                // Guardado del ticket en el repositorio
                OurTickets ourTicketResult = ticketRepo.save(savedTicket);

                if (ourTicketResult.getId() > 0) {
                    response.setOurTickets(ourTicketResult);
                    response.setMessage("Ticket Saved Successfully");
                    response.setStatusCode(200);
                } else {
                    response.setMessage("Ticket not saved due to an unknown error.");
                    response.setStatusCode(500);
                }
            } else {
                response.setMessage("Invalid DTO type");
                response.setStatusCode(400);
            }

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred: " + e.getMessage());
        }
        return response;
    }

    public OurTicketsDTO getAllTickets() {
        OurTicketsDTO response = new OurTicketsDTO();
        try {
            List<OurTickets> tickets = ticketRepo.findAll();
            if (tickets.isEmpty()) {
                response.setStatusCode(404);
                response.setMessage("No tickets found");
            } else {
                response.setStatusCode(200);
                response.setOurTicketsList(tickets);
                response.setMessage("Tickets found successfully");
            }
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred: " + e.getMessage());
        }
        return response;
    }

    public OurTicketsDTO getTicketById(Integer ticketId) {
        OurTicketsDTO response = new OurTicketsDTO();
        try {
            OurTickets ticket = ticketRepo.findById(ticketId).orElseThrow(() -> new RuntimeException("Ticket not found"));
            response.setStatusCode(200);
            response.setOurTickets(ticket);
            response.setMessage("Ticket found successfully");
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred: " + e.getMessage());
        }
        return response;
    }

    public OurTicketsDTO updateTicket(Integer ticketId, OurTickets ticket) {
        OurTicketsDTO response = new OurTicketsDTO();
        try {
            Optional<OurTickets> existingTicketOpt = ticketRepo.findById(ticketId);
            if (existingTicketOpt.isPresent()) {
                OurTickets existingTicket = existingTicketOpt.get();

                SaveTicket(existingTicket, ticket.getBuyingDate(), ticket.getDiscount(), ticket.getPrice(), ticket.getPriceWithDiscount(), ticket.getSeat(), ticket.getClient(), ticket.getEvent());

                OurTickets updatedTicket = ticketRepo.save(existingTicket);
                response.setStatusCode(200);
                response.setOurTickets(updatedTicket);
                response.setMessage("Ticket updated successfully");
            } else {
                response.setStatusCode(404);
                response.setMessage("Ticket not found");
            }
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred: " + e.getMessage());
        }
        return response;
    }

    private void SaveTicket(OurTickets existingTicket, LocalDate buyingDate, Double discount, Double price, Double priceWithDiscount, OurSeats seat, OurUsers client, OurEvents event) {
        existingTicket.setBuyingDate(buyingDate);
        existingTicket.setDiscount(discount);
        existingTicket.setPrice(price);
        existingTicket.setPriceWithDiscount(priceWithDiscount);
        existingTicket.setSeat(seat);
        existingTicket.setClient(client);
        existingTicket.setEvent(event);
    }

    public OurTicketsDTO deleteTicket(Integer ticketId) {
        OurTicketsDTO response = new OurTicketsDTO();
        try {
            Optional<OurTickets> ticketOptional = ticketRepo.findById(ticketId);
            if (ticketOptional.isPresent()) {
                ticketRepo.deleteById(ticketId);
                response.setStatusCode(200);
                response.setMessage("Ticket deleted successfully");
            } else {
                response.setStatusCode(404);
                response.setMessage("Ticket not found");
            }
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred while deleting ticket: " + e.getMessage());
        }
        return response;
    }
}