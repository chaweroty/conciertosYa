package com.chaweDev.conciertosYa.service;

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

    public OurTicketsDTO addTicket(OurTicketsDTO ticket) {
        OurTicketsDTO response = new OurTicketsDTO();
        try {
            OurTickets savedPlace = new OurTickets();

            SaveTicket(savedPlace, ticket.getBuyingDate(), ticket.getDiscount(), ticket.getPrice(), ticket.getPriceWithDiscount(), ticket.getSeat(), ticket.getClient(), ticket.getEvent());

            OurSeats seatResult = seatService.getSeatById(ticket.getSeat().getId()).getOurSeats();
            OurSeatsDTO updatedSeat = new OurSeatsDTO();

            updatedSeat.setCode(seatResult.getCode());
            updatedSeat.setRow(seatResult.getRow());
            updatedSeat.setColumn(seatResult.getColumn());
            updatedSeat.setType(seatResult.getType());
            updatedSeat.setState("Sold");
            updatedSeat.setPlace(seatResult.getPlace().getId());
            System.out.println(updatedSeat.getState());
            seatService.updateSeat(seatResult.getId(), updatedSeat);

            OurTickets ourTicketResult = ticketRepo.save(savedPlace);

            if (ourTicketResult.getId() > 0) {
                response.setOurTickets(ourTicketResult);
                response.setMessage("Ticket Saved Successfully");
                response.setStatusCode(200);
            } else {
                response.setMessage("Ticket not saved due to an unknown error.");
                response.setStatusCode(500);
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